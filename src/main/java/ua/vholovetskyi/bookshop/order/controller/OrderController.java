package ua.vholovetskyi.bookshop.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.vholovetskyi.onlinestore.order.controller.dto.*;
import ua.vholovetskyi.onlinestore.order.exception.ReportOrderException;
import ua.vholovetskyi.onlinestore.order.exception.UploadOrderException;
import ua.vholovetskyi.onlinestore.order.service.OrderService;
import ua.vholovetskyi.onlinestore.order.service.QueryOrderService;
import ua.vholovetskyi.onlinestore.order.service.ReportOrderService;
import ua.vholovetskyi.onlinestore.order.service.UploadOrderService;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private static final CSVFormat FORMAT = CSVFormat.Builder
            .create(CSVFormat.DEFAULT)
            .setHeader("Id", "Total product", "Status", "Order date")
            .build();
    private final OrderService orderService;
    private final UploadOrderService uploadOrderService;
    private final QueryOrderService queryOrderService;
    private final ReportOrderService reportService;

    @PostMapping("/_list")
    public OrderPagination findOrders(@RequestBody @Valid OrderPaginationDto paginationDto) {
        return queryOrderService.findOrders(paginationDto);
    }

    @GetMapping("/{id}")
    public OrderDetails findOrderById(@PathVariable Long id) {
        return queryOrderService.findOrderById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderSummary createOrder(@RequestBody @Valid OrderDto order) {
        return orderService.createOrder(order);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateOrder(@PathVariable Long id, @RequestBody @Valid OrderDto orderDto) {
        orderService.updateOrder(id, orderDto);
    }

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public UploadOrder uploadOrders(@RequestParam("file") MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            return uploadOrderService.uploadOrders(file.getOriginalFilename(), inputStream);
        } catch (IOException e) {
            throw new UploadOrderException(e);
        }
    }

    @PostMapping("/_report")
    public ResponseEntity<Resource> reportOrders(@RequestBody @Valid OrderFilteringDto filterRequest) {
        var orders = reportService.reportOrders(filterRequest);
        ByteArrayInputStream stream = transformToCsv(orders);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "reportOrder.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(stream));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    private ByteArrayInputStream transformToCsv(List<OrderList> orderList) {
        log.info("Start transform object to CSV file...");
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream();
             CSVPrinter printer = new CSVPrinter(new PrintWriter(stream), FORMAT)) {
            for (OrderList order : orderList) {
                printer.printRecord(Arrays.asList(
                        order.getId(),
                        order.getTotalProduct(),
                        order.getStatus(),
                        order.getOrderDate()
                ));
            }
            printer.flush();
            log.info("The end transform object to CSV file...");
            return new ByteArrayInputStream(stream.toByteArray());
        } catch (IOException e) {
            log.warn("Error transform object to CSV file. Error message: %s".formatted(e.getMessage()));
            throw new ReportOrderException("Error processing CSV file", e);
        }
    }
}
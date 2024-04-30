package ua.vholovetskyi.bookshop.order.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ua.vholovetskyi.bookshop.commons.exception.ErrorResponse;
import ua.vholovetskyi.bookshop.data.OrderBuilder;
import ua.vholovetskyi.bookshop.order.controller.dto.*;
import ua.vholovetskyi.bookshop.order.model.OrderStatus;
import ua.vholovetskyi.bookshop.order.service.OrderService;
import ua.vholovetskyi.bookshop.order.service.QueryOrderService;
import ua.vholovetskyi.bookshop.order.service.ReportOrderService;
import ua.vholovetskyi.bookshop.order.service.UploadOrderService;

import java.io.InputStream;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerApiTest extends OrderBuilder {

    @LocalServerPort
    private int port;
    @MockBean
    private OrderService orderService;
    @MockBean
    private QueryOrderService queryOrderService;
    @MockBean
    private ReportOrderService reportOrderService;
    @MockBean
    private UploadOrderService uploadOrderService;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldFindAllOrders() {
        //given
        var baseUrl = givenBaseUrl();
        var orderDto = givenOrderPaginationDto();
        var order = givenOrderPagination();

        //when
        when(queryOrderService.findOrders(any(OrderPaginationDto.class))).thenReturn(order);
        RequestEntity<?> request = RequestEntity.post(URI.create(baseUrl + "/_list")).body(orderDto);
        ResponseEntity<OrderPagination> result = restTemplate.exchange(request, OrderPagination.class);

        //then
        assertThat(result.getStatusCode().value()).isEqualTo(200);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getList()).size().isEqualTo(2);
        assertThat(result.getBody().getTotalPage()).isEqualTo(5);
        verify(queryOrderService, times(1)).findOrders(any(OrderPaginationDto.class));
    }

    @Test
    void shouldCreateOrder() {
        //given
        var baseUrl = givenBaseUrl();
        var orderDto = givenOrderDto();
        var order = givenOrderSummary();

        //when
        when(orderService.createOrder(any(OrderDto.class))).thenReturn(order);
        RequestEntity<?> request = RequestEntity.post(URI.create(baseUrl)).body(orderDto);
        ResponseEntity<OrderSummary> result = restTemplate.exchange(request, OrderSummary.class);

        //then
        assertThat(result.getStatusCode().value()).isEqualTo(201);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody().getTotalProduct()).isEqualTo(2);
        assertThat(result.getBody().getStatus()).isEqualTo(OrderStatus.NEW);
        verify(orderService, times(1)).createOrder(any(OrderDto.class));
    }

    @ParameterizedTest
    @MethodSource("wrongOrders")
    void shouldReturnBadRequest_whenCreateOrder_withWrongParams(OrderDto orderDto) {
        //given
        var baseUrl = givenBaseUrl();

        //when
        RequestEntity<?> request = RequestEntity.post(URI.create(baseUrl)).body(orderDto);
        ResponseEntity<ErrorResponse> result = restTemplate.exchange(request, ErrorResponse.class);

        //then
        assertThat(result.getStatusCode().value()).isEqualTo(400);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldReportOrder() {
        //given
        var baseUrl = givenBaseUrl() + "/_report";
        var orderDto = givenOrderFilteringByCustIdDto();
        var orderList = givenOrderList();

        //when
        when(reportOrderService.reportOrders(any(OrderFilteringDto.class))).thenReturn(orderList);
        RequestEntity<?> request = RequestEntity.post(URI.create(baseUrl)).body(orderDto);
        ResponseEntity<Resource> result = restTemplate.exchange(request, Resource.class);

        //then
        assertThat(result.getStatusCode().value()).isEqualTo(200);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(reportOrderService, times(1)).reportOrders(any(OrderFilteringDto.class));
    }

    @Test
    void shouldUploadOrder() {
        //given
        var baseUrl = givenBaseUrl() + "/upload";
        var order = givenUploadOrder();
        var headers = givenHeaders();
        var body = givenBody();

        //when
        when(uploadOrderService.uploadOrders(any(String.class), any(InputStream.class))).thenReturn(order);
        RequestEntity<?> request = new RequestEntity<>(body, headers, HttpMethod.POST, URI.create(baseUrl));
        ResponseEntity<UploadOrder> result = restTemplate.exchange(request, UploadOrder.class);

        //then
        assertThat(result.getStatusCode().value()).isEqualTo(200);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getImportedRecord()).isEqualTo(10);
        assertThat(result.getBody().getNonImportedRecord()).isEqualTo(2);
        verify(uploadOrderService, times(1)).uploadOrders(any(String.class), any(InputStream.class));
    }

    @Test
    void shouldUpdateOrder() {
        //given
        var baseUrl = givenBaseUrl() + "/1";
        var updateOrder = givenUpdateOrderDto();

        //when
        RequestEntity<OrderDto> request = RequestEntity.put(URI.create(baseUrl)).body(updateOrder);
        ResponseEntity<Void> result = restTemplate.exchange(request, Void.class);

        //then
        assertThat(result.getStatusCode().value()).isEqualTo(202);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }

    private String givenBaseUrl() {
        return "http://localhost:" + port + "/api/orders";
    }

    private MultiValueMap<String, String> givenHeaders() {
        var headers = new LinkedMultiValueMap<String, String>();
        headers.add("contentType", MediaType.MULTIPART_FORM_DATA_VALUE);
        headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    private MultiValueMap<String, Object> givenBody() {
        var jsonFile = new MockMultipartFile("test.json", "",
                "application/json", "{\"key1\": \"value1\"}".getBytes());
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", jsonFile.getResource());
        return body;
    }
}

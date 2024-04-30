package ua.vholovetskyi.bookshop.order.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import ua.vholovetskyi.onlinestore.data.OrderBuilder;
import ua.vholovetskyi.onlinestore.order.controller.dto.OrderDto;
import ua.vholovetskyi.onlinestore.order.controller.dto.OrderFilteringDto;
import ua.vholovetskyi.onlinestore.order.controller.dto.OrderPaginationDto;
import ua.vholovetskyi.onlinestore.order.service.OrderService;
import ua.vholovetskyi.onlinestore.order.service.QueryOrderService;
import ua.vholovetskyi.onlinestore.order.service.ReportOrderService;
import ua.vholovetskyi.onlinestore.order.service.UploadOrderService;

import java.io.InputStream;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerWebTest extends OrderBuilder {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    @MockBean
    private QueryOrderService queryOrderService;
    @MockBean
    private ReportOrderService reportOrderService;
    @MockBean
    private UploadOrderService uploadOrderService;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldFindOrderById() throws Exception {
        //given
        var order = givenOrderDetails();
        when(queryOrderService.findOrderById(any(Long.class))).thenReturn(order);

        //expect
        mockMvc.perform(get("/api/orders/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("NEW"))
                .andExpect(jsonPath("$.items", hasSize(2)));
    }

    @Test
    void shouldFindAllOrders() throws Exception {
        //given
        var orders = givenOrderPagination();
        var orderDto = givenOrderPaginationDto();
        when(queryOrderService.findOrders(any(OrderPaginationDto.class))).thenReturn(orders);

        //expect
        mockMvc.perform(post("/api/orders/_list")
                        .content(jsonString(orderDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list", hasSize(2)));
    }

    @Test
    void shouldUploadOrder() throws Exception {
        //given
        var order = givenUploadOrder();
        when(uploadOrderService.uploadOrders(any(String.class), any(InputStream.class))).thenReturn(order);
        var jsonFile = new MockMultipartFile("test.json", "",
                "application/json", "{\"key1\": \"value1\"}".getBytes());

        //expect
        mockMvc.perform(multipart(HttpMethod.POST, "/api/orders/upload")
                        .file("files", jsonFile.getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldCreateOrder() throws Exception {
        //given
        var orderDto = givenOrderDto();
        var order = givenOrderSummary();
        when(orderService.createOrder(any(OrderDto.class))).thenReturn(order);

        //expect
        mockMvc.perform(post("/api/orders")
                        .content(jsonString(orderDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.totalProduct").value(2))
                .andExpect(jsonPath("$.status").value("NEW"));
    }

    @Test
    void shouldUpdateOrder() throws Exception {
        //given
        var updateOrder = givenUpdateOrderDto();

        //expect
        mockMvc.perform(put("/api/orders/1")
                        .content(jsonString(updateOrder))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }
    @Test
    void shouldReportOrder() throws Exception {
        //given
        var order = givenOrderFilteringDto();
        when(reportOrderService.reportOrders(any(OrderFilteringDto.class))).thenReturn(List.of());

        //expect
        mockMvc.perform(post("/api/orders/_report")
                        .content(jsonString(order))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.parseMediaType("text/csv")))
                .andExpect(status().isOk());
    }

    private String jsonString(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }
}

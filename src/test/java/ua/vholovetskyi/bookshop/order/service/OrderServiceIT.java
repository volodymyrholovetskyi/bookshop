package ua.vholovetskyi.bookshop.order.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ua.vholovetskyi.onlinestore.customer.exception.CustomerNotFoundException;
import ua.vholovetskyi.onlinestore.customer.repository.CustomerRepository;
import ua.vholovetskyi.onlinestore.data.OrderBuilder;
import ua.vholovetskyi.onlinestore.order.controller.dto.OrderSummary;
import ua.vholovetskyi.onlinestore.order.exception.OrderNotFoundException;
import ua.vholovetskyi.onlinestore.order.model.OrderStatus;
import ua.vholovetskyi.onlinestore.order.repository.OrderRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
@Import(value = {OrderService.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrderServiceIT extends OrderBuilder {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderService orderService;

    @Test
    void shouldCreateOrder() {
        //given
        givenSaveCustomer();
        var orderDto = givenOrderDto();

        //when
        OrderSummary order = orderService.createOrder(orderDto);

        //then
        assertThat(order.getId()).isEqualTo(1L);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.NEW);
        assertThat(order.getTotalProduct()).isEqualTo(2);
    }

    @Test
    void shouldThrowsException_whenCreateOrder_ifCustomerNotFound() {
        //given
        var orderDto = givenOrderDto();

        //when
        var customerNotFound = assertThrows(CustomerNotFoundException.class,
                () -> orderService.createOrder(orderDto));

        //then
        assertThat(customerNotFound.getMessage()).isEqualTo(CUSTOMER_NOT_FOUND_MESSES);
    }

    @Test
    void shouldUpdateOrder() {
        //given
        giveSaveOrderWithCustomer();
        var orderDto = givenUpdateOrderDto();

        //when
        orderService.updateOrder(1L, orderDto);
        var updateStatus = orderRepository.findById(1L);

        //then
        assertThat(updateStatus.get().getStatus()).isEqualTo(OrderStatus.PAID);
        assertThat(updateStatus.get().getItems().size()).isEqualTo(1);
    }

    @Test
    void shouldThrowsException_whenOrderNotFound() {
        //given
        var orderDto = givenUpdateOrderDto();

        //when
        var orderNotFound = assertThrows(OrderNotFoundException.class,
                () -> orderService.updateOrder(1L, orderDto));

        //then
        assertThat(orderNotFound.getMessage()).isEqualTo(ORDER_NOT_FOUND_MESSES);
    }

    @Test
    void shouldThrowsException_whenUpdateStatusOrder_ifOrderNotFound() {
        //given
        var orderDto = givenUpdateOrderDto();

        //when
        var orderNotFound = assertThrows(OrderNotFoundException.class,
                () -> orderService.updateOrder(1L, orderDto));

        //then
        assertThat(orderNotFound.getMessage()).isEqualTo(ORDER_NOT_FOUND_MESSES);
    }

    @Test
    void shouldDeleteOrder() {
        //given
        giveSaveOrderWithCustomer();

        //when
        orderService.deleteOrder(1L);
        var deletedOrder = orderRepository.findById(1L);

        //then
        assertThat(deletedOrder.isEmpty()).isTrue();
    }

    @Test
    void shouldThrowException_whenDeleteOrder_ifOrderNotFound() {
        //given
        //when
        var orderNotFound = assertThrows(OrderNotFoundException.class,
                () -> orderService.deleteOrder(1L));

        //then
        assertThat(orderNotFound.getMessage()).isEqualTo(ORDER_NOT_FOUND_MESSES);
    }

    private void givenSaveCustomer() {
        customerRepository.save(givenCustomer());
    }

    private void giveSaveOrderWithCustomer() {
        givenSaveCustomer();
        orderRepository.save(givenOrder());
    }
}
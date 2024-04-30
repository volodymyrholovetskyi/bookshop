package ua.vholovetskyi.bookshop.order.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ua.vholovetskyi.onlinestore.customer.repository.CustomerRepository;
import ua.vholovetskyi.onlinestore.data.OrderBuilder;
import ua.vholovetskyi.onlinestore.order.exception.OrderNotFoundException;
import ua.vholovetskyi.onlinestore.order.model.OrderStatus;
import ua.vholovetskyi.onlinestore.order.repository.OrderRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest
@ActiveProfiles("test")
@Import(value = {QueryOrderService.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class QueryOrderServiceIT extends OrderBuilder {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private QueryOrderService orderService;


    @Test
    void shouldFindOrderById() {
        //given
        givenSaveOrder();

        //when
        var order = orderService.findOrderById(1L);

        //then
        assertThat(order.getStatus()).isEqualTo(OrderStatus.NEW);
        assertThat(order.getCustId()).isEqualTo(1);
        assertThat(order.getItems().size()).isEqualTo(2);
    }

    @Test
    void shouldThrowException_ifOrderNotFound() {
        //given
        givenSaveOrder();

        //when
        var orderNotFound = assertThrows(OrderNotFoundException.class, () -> orderService.findOrderById(2L));

        //then
        assertThat(orderNotFound.getMessage()).isEqualTo("Order with ID: 2 not found!");
    }
    @Test
    void shouldFindAllOrders_byCustomerId() {
        //given
        givenSaveOrders();
        var orderDto = givenOrderPaginationByCustIdDto();

        //when
        var orders = orderService.findOrders(orderDto);

        //then
        assertThat(orders.getList().size()).isEqualTo(5);
        assertThat(orders.getTotalPage()).isEqualTo(2);
    }

    @Test
    void shouldFindAllOrders_byCustomerIdAndStatus() {
        //given
        givenSaveOrders();
        var orderDto = givenOrderPaginationByCustIdAndStatusDto();

        //when
        var orders = orderService.findOrders(orderDto);

        //then
        assertThat(orders.getList().size()).isEqualTo(4);
        assertThat(orders.getTotalPage()).isEqualTo(1);
    }

    @Test
    void shouldFindAllOrders_byCustomerIdAndOrderDate() {
        //given
        givenSaveOrders();
        var orderDto = givenOrderPaginationByCustIdAndOrderDateDto();

        //when
        var orders = orderService.findOrders(orderDto);

        //then
        assertThat(orders.getList().size()).isEqualTo(2);
        assertThat(orders.getTotalPage()).isEqualTo(1);
    }

    @Test
    void shouldFindAllOrders_byCustomerIdAndStatusAndOrderDate() {
        //given
        givenSaveOrders();
        var orderDto = givenOrderPaginationByCustIdAndStatusAndOrderDateDto();

        //when
        var orders = orderService.findOrders(orderDto);

        //then
        assertThat(orders.getList().size()).isEqualTo(1);
        assertThat(orders.getTotalPage()).isEqualTo(1);
    }

    @Test
    void shouldReturnEmptyList_ifCustomerNotFound() {
        //given
        givenSaveOrders();
        var orderDto = givenOrderPaginationWithCustId3();

        //when
        var emptyList = orderService.findOrders(orderDto);

        //then
        assertThat(emptyList.getList().size()).isEqualTo(0);
    }

    private void givenSaveOrder() {
        customerRepository.save(givenCustomer());
        orderRepository.save(givenOrder());
    }

    private void givenSaveOrders() {
        customerRepository.save(givenCustomer());
        orderRepository.saveAll(givenOrders());
    }
}
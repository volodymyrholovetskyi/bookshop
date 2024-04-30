package ua.vholovetskyi.bookshop.order.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ua.vholovetskyi.bookshop.customer.repository.CustomerRepository;
import ua.vholovetskyi.bookshop.data.OrderBuilder;
import ua.vholovetskyi.bookshop.order.repository.OrderRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(value = {ReportOrderService.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReportOrderServiceIT extends OrderBuilder {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ReportOrderService orderService;

    @Test
    void shouldFindAllOrders_byCustomerId() {
        //given
        givenSaveOrders();
        var orderDto = givenOrderFilteringByCustIdDto();

        //when
        var orders = orderService.reportOrders(orderDto);

        //then
        assertThat(orders.size()).isEqualTo(10);
    }

    @Test
    void shouldFindAllOrders_byCustomerIdAndStatus() {
        //given
        givenSaveOrders();
        var orderDto = givenOrderFilteringByCustIdAndStatusDto();

        //when
        var orders = orderService.reportOrders(orderDto);

        //then
        assertThat(orders.size()).isEqualTo(4);
    }

    @Test
    void shouldFindAllOrders_byCustomerIdAndOrderDate() {
        //given
        givenSaveOrders();
        var orderDto = givenOrderFilteringByCustIdAndOrderDateDto();

        //when
        var orders = orderService.reportOrders(orderDto);

        //then
        assertThat(orders.size()).isEqualTo(2);
    }

    @Test
    void shouldFindAllOrders_byCustomerIdAndStatusAndOrderDate() {
        //given
        givenSaveOrders();
        var orderDto = givenOrderFilteringByCustIdAndStatusAndOrderDateDto();

        //when
        var orders = orderService.reportOrders(orderDto);

        //then
        assertThat(orders.size()).isEqualTo(1);
    }

    private void givenSaveOrders() {
        customerRepository.save(givenCustomer());
        orderRepository.saveAll(givenOrders());
    }
}
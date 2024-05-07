package ua.vholovetskyi.bookshop.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vholovetskyi.bookshop.commons.exception.impl.customer.CustomerNotFoundException;
import ua.vholovetskyi.bookshop.customer.repository.CustomerRepository;
import ua.vholovetskyi.bookshop.order.controller.dto.OrderDto;
import ua.vholovetskyi.bookshop.order.controller.dto.OrderSummary;
import ua.vholovetskyi.bookshop.commons.exception.impl.order.OrderNotFoundException;
import ua.vholovetskyi.bookshop.order.repository.OrderRepository;

import static ua.vholovetskyi.bookshop.order.mapper.OrderFactory.createNewOrder;
import static ua.vholovetskyi.bookshop.order.mapper.OrderFactory.createOrderSummary;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-24
 */
@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public OrderSummary createOrder(OrderDto orderDto) {
        validateCustomerExists(orderDto.getCustId());
        var savedOrder = orderRepository.save(createNewOrder(orderDto));
        return createOrderSummary(savedOrder);
    }

    public void updateOrder(Long id, OrderDto orderDto) {
        validateOrderExists(id);
        validateCustomerExists(orderDto.getCustId());
        orderRepository.save(createNewOrder(id, orderDto));
    }

    public void deleteOrder(Long id) {
        validateOrderExists(id);
        orderRepository.deleteById(id);
    }

    private void validateCustomerExists(Long id) {
        if (customerRepository.findById(id).isEmpty()) {
            throw new CustomerNotFoundException(id);
        }
    }

    private void validateOrderExists(Long id) {
        if (orderRepository.findById(id).isEmpty()) {
            throw new OrderNotFoundException(id);
        }
    }
}

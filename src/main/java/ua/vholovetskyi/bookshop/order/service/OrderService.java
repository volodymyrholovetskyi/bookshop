package ua.vholovetskyi.bookshop.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vholovetskyi.bookshop.customer.exception.CustomerNotFoundException;
import ua.vholovetskyi.bookshop.customer.repository.CustomerRepository;
import ua.vholovetskyi.bookshop.order.controller.dto.OrderDto;
import ua.vholovetskyi.bookshop.order.controller.dto.OrderSummary;
import ua.vholovetskyi.bookshop.order.exception.OrderNotFoundException;
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
        if (!validateCustomerExists(orderDto.getCustId())) {
            throw new CustomerNotFoundException(orderDto.getCustId());
        }
        var saveOrder = orderRepository.save(createNewOrder(orderDto));
        return createOrderSummary(saveOrder);
    }

    public void updateOrder(Long id, OrderDto orderDto) {
        if (!validateCustomerExists(orderDto.getCustId())) {
            throw new CustomerNotFoundException(orderDto.getCustId());
        }
        orderRepository.findById(id)
                .map(loadOrder -> loadOrder.updateFields(orderDto))
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public void deleteOrder(Long id) {
        if (!validateCustomerExists(id)) {
            throw new OrderNotFoundException(id);
        }
        orderRepository.deleteById(id);
    }

    private boolean validateCustomerExists(Long id) {
        return customerRepository.existsById(id);
    }
}

package ua.vholovetskyi.bookshop.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vholovetskyi.onlinestore.customer.exception.CustomerNotFoundException;
import ua.vholovetskyi.onlinestore.customer.repository.CustomerRepository;
import ua.vholovetskyi.onlinestore.order.controller.dto.OrderDto;
import ua.vholovetskyi.onlinestore.order.controller.dto.OrderSummary;
import ua.vholovetskyi.onlinestore.order.exception.OrderNotFoundException;
import ua.vholovetskyi.onlinestore.order.repository.OrderRepository;

import static ua.vholovetskyi.onlinestore.order.mapper.OrderFactory.createNewOrder;
import static ua.vholovetskyi.onlinestore.order.mapper.OrderFactory.createOrderSummary;

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
        orderRepository.findById(id)
                .map(loadOrder -> {
                    if (!validateCustomerExists(orderDto.getCustId())) {
                        throw new CustomerNotFoundException(orderDto.getCustId());
                    }
                    return loadOrder.updateFields(orderDto);
                })
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

package ua.vholovetskyi.bookshop.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.vholovetskyi.onlinestore.order.controller.dto.OrderDetails;
import ua.vholovetskyi.onlinestore.order.controller.dto.OrderPagination;
import ua.vholovetskyi.onlinestore.order.controller.dto.OrderPaginationDto;
import ua.vholovetskyi.onlinestore.order.exception.OrderNotFoundException;
import ua.vholovetskyi.onlinestore.order.mapper.OrderDtoMapper;
import ua.vholovetskyi.onlinestore.order.model.OrderEntity;
import ua.vholovetskyi.onlinestore.order.repository.OrderRepository;

import static ua.vholovetskyi.onlinestore.commons.helper.OrderFilterHelper.*;
import static ua.vholovetskyi.onlinestore.order.mapper.OrderDtoMapper.mapToOrderPagination;

@Service
@RequiredArgsConstructor
public class QueryOrderService {
    private final OrderRepository orderRepository;

    public OrderDetails findOrderById(Long id) {
        return orderRepository.findById(id)
                .map(OrderDtoMapper::mapToOrderDetailsDto)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public OrderPagination findOrders(OrderPaginationDto orderDto) {
        if (hasOnlyFilteringByCustId(orderDto.getFilter())) {
            return findAllByCustomerId(orderDto);
        }
        if (hasFilteringByCustIdAndStatus(orderDto.getFilter())) {
            return findAllByCustomerIdAndStatus(orderDto);
        }
        if (hasFilteringByCustIdAndOrderDate(orderDto.getFilter())) {
            return findAllByCustomerIdAndOrderDateIsBetween(orderDto);
        }
        return findAllByCustomerIdAndOrderDateAndStatus(orderDto);
    }

    private OrderPagination findAllByCustomerId(OrderPaginationDto orderDto) {
        var orders = orderRepository.findAllByCustomerId(orderDto.getFilter().getCustId(),
                getPageRequest(orderDto));
        return buildResultFilteringOrders(orders);
    }

    private OrderPagination findAllByCustomerIdAndStatus(OrderPaginationDto orderDto) {
        var orders = orderRepository.findAllByCustomerIdAndStatus(orderDto.getFilter().getCustId(),
                orderDto.getFilter().getStatus(),
                getPageRequest(orderDto));
        return buildResultFilteringOrders(orders);
    }

    private OrderPagination findAllByCustomerIdAndOrderDateIsBetween(OrderPaginationDto orderDto) {
        var orders = orderRepository.findAllByCustomerIdAndOrderDateIsBetween(orderDto.getFilter().getCustId(),
                orderDto.getFilter().getFrom(),
                orderDto.getFilter().getTo(),
                getPageRequest(orderDto));
        return buildResultFilteringOrders(orders);
    }

    private OrderPagination findAllByCustomerIdAndOrderDateAndStatus(OrderPaginationDto orderDto) {
        var orders = orderRepository.findAllByCustomerIdAndOrderDateIsBetweenAndStatus(orderDto.getFilter().getCustId(),
                orderDto.getFilter().getFrom(),
                orderDto.getFilter().getTo(),
                orderDto.getFilter().getStatus(),
                getPageRequest(orderDto));
        return buildResultFilteringOrders(orders);
    }

    private OrderPagination buildResultFilteringOrders(Page<OrderEntity> orders) {
        return mapToOrderPagination(orders.getContent(), orders.getTotalPages());
    }

    private PageRequest getPageRequest(OrderPaginationDto orderRequest) {
        return PageRequest.of(orderRequest.getPageNumber(), orderRequest.getSize());
    }
}

package ua.vholovetskyi.bookshop.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.vholovetskyi.bookshop.order.controller.dto.*;
import ua.vholovetskyi.bookshop.commons.exception.impl.order.OrderNotFoundException;
import ua.vholovetskyi.bookshop.order.mapper.OrderDtoMapper;
import ua.vholovetskyi.bookshop.order.model.OrderEntity;
import ua.vholovetskyi.bookshop.order.repository.OrderRepository;
import ua.vholovetskyi.bookshop.order.service.handler.OrderSearchService;
import java.util.List;

import static ua.vholovetskyi.bookshop.order.mapper.OrderDtoMapper.*;


/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-24
 */
@Service
@RequiredArgsConstructor
public class QueryOrderService {

    private static final Pageable EMPTY_PAGEABLE = null;
    private final OrderRepository orderRepository;
    private final OrderSearchService searchService;

    public OrderDetails findOrderById(Long id) {
        return orderRepository.findById(id)
                .map(OrderDtoMapper::mapToOrderDetailsDto)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public OrderSearchResponse findOrders(OrderSearchRequest searchRequest) {
        var pageOrder = getPageOrder(searchRequest.getSearch(), getPageRequest(searchRequest));
        return mapToOrderSearch(pageOrder.getContent(), pageOrder.getTotalPages());
    }

    public List<OrderList> findOrdersReport(SearchRequest searchRequest) {
        return mapToOrderListDto(getPageOrder(searchRequest, EMPTY_PAGEABLE).getContent());
    }

    private PageRequest getPageRequest(OrderSearchRequest orderRequest) {
        return PageRequest.of(orderRequest.getPageNumber(), orderRequest.getSize());
    }

    private Page<OrderEntity> getPageOrder(SearchRequest searchRequest, Pageable pageable) {
        return searchService.searchOrders(searchRequest, pageable);
    }
}

package ua.vholovetskyi.bookshop.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.vholovetskyi.bookshop.order.controller.dto.OrderFilteringDto;
import ua.vholovetskyi.bookshop.order.controller.dto.OrderList;
import ua.vholovetskyi.bookshop.order.model.OrderEntity;
import ua.vholovetskyi.bookshop.order.repository.OrderRepository;

import java.util.List;

import static ua.vholovetskyi.bookshop.commons.helper.OrderFilterHelper.*;
import static ua.vholovetskyi.bookshop.order.mapper.OrderDtoMapper.mapToOrderListDto;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-24
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReportOrderService {

    private static final Pageable EMPTY_PAGEABLE = null;
    private final OrderRepository orderRepository;

    public List<OrderList> reportOrders(OrderFilteringDto filterDto) {
        if (hasOnlyFilteringByCustId(filterDto)) {
             return mapToOrderListDto(findAllByCustomerId(filterDto));
        }
        if (hasFilteringByCustIdAndStatus(filterDto)) {
            return mapToOrderListDto(findAllByCustomerIdAndStatus(filterDto));
        }
        if (hasFilteringByCustIdAndOrderDate(filterDto)) {
            return mapToOrderListDto(findAllByCustomerIdAndOrderDateIsBetween(filterDto));
        }
        return mapToOrderListDto(findAllByCustomerIdAndOrderDateAndStatus(filterDto));
    }

    private List<OrderEntity> findAllByCustomerId(OrderFilteringDto filter) {
        return orderRepository.findAllByCustomerId(filter.getCustId(),
                EMPTY_PAGEABLE)
                .getContent();
    }

    private List<OrderEntity> findAllByCustomerIdAndStatus(OrderFilteringDto filter) {
        return orderRepository.findAllByCustomerIdAndStatus(filter.getCustId(),
                filter.getStatus(),
                EMPTY_PAGEABLE)
                .getContent();

    }

    private List<OrderEntity> findAllByCustomerIdAndOrderDateIsBetween(OrderFilteringDto filter) {
        return orderRepository.findAllByCustomerIdAndOrderDateIsBetween(filter.getCustId(),
                filter.getFrom(),
                filter.getTo(),
                EMPTY_PAGEABLE)
                .getContent();
    }

    private List<OrderEntity> findAllByCustomerIdAndOrderDateAndStatus(OrderFilteringDto filter) {
        return orderRepository.findAllByCustomerIdAndOrderDateIsBetweenAndStatus(filter.getCustId(),
                filter.getFrom(),
                filter.getTo(),
                filter.getStatus(),
                EMPTY_PAGEABLE)
                .getContent();
    }
}

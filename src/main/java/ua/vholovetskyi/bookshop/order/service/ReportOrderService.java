package ua.vholovetskyi.bookshop.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.vholovetskyi.onlinestore.order.controller.dto.OrderFilteringDto;
import ua.vholovetskyi.onlinestore.order.controller.dto.OrderList;
import ua.vholovetskyi.onlinestore.order.model.OrderEntity;
import ua.vholovetskyi.onlinestore.order.repository.OrderRepository;

import java.util.List;

import static ua.vholovetskyi.onlinestore.commons.helper.OrderFilterHelper.*;
import static ua.vholovetskyi.onlinestore.order.mapper.OrderDtoMapper.mapToOrderListDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportOrderService {
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
        return orderRepository.findAllByCustomerId(filter.getCustId());
    }

    private List<OrderEntity> findAllByCustomerIdAndStatus(OrderFilteringDto filter) {
        return orderRepository.findAllByCustomerIdAndStatus(filter.getCustId(),
                filter.getStatus());

    }

    private List<OrderEntity> findAllByCustomerIdAndOrderDateIsBetween(OrderFilteringDto filter) {
        return orderRepository.findAllByCustomerIdAndOrderDateIsBetween(filter.getCustId(),
                filter.getFrom(),
                filter.getTo());
    }

    private List<OrderEntity> findAllByCustomerIdAndOrderDateAndStatus(OrderFilteringDto filter) {
        return orderRepository.findAllByCustomerIdAndOrderDateIsBetweenAndStatus(filter.getCustId(),
                filter.getFrom(),
                filter.getTo(),
                filter.getStatus());
    }
}

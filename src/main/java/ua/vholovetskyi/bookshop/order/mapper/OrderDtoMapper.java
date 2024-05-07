package ua.vholovetskyi.bookshop.order.mapper;

import ua.vholovetskyi.bookshop.order.controller.dto.OrderDetails;
import ua.vholovetskyi.bookshop.order.controller.dto.OrderList;
import ua.vholovetskyi.bookshop.order.controller.dto.OrderSearchResponse;
import ua.vholovetskyi.bookshop.order.model.OrderEntity;

import java.util.List;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-24
 */
public class OrderDtoMapper {

    public static OrderSearchResponse mapToOrderSearch(List<OrderEntity> orders, int totalPage) {
        return OrderSearchResponse.builder()
                .list(mapToOrderListDto(orders))
                .totalPage(totalPage)
                .build();
    }

    public static List<OrderList> mapToOrderListDto(List<OrderEntity> orders) {
        return orders.stream()
                .map(OrderDtoMapper::mapToOrderListDto)
                .toList();
    }

    private static OrderList mapToOrderListDto(OrderEntity order) {
        return OrderList.builder()
                .id(order.getId())
                .totalProduct(order.getItems().size())
                .status(order.getStatus())
                .orderDate(order.getOrderDate())
                .build();
    }

    public static OrderDetails mapToOrderDetailsDto(OrderEntity order) {
        return OrderDetails.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .custId(order.getCustomerId())
                .items(order.getItems())
                .status(order.getStatus())
                .build();
    }
}

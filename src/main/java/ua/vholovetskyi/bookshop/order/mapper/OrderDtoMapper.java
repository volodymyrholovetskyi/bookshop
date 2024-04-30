package ua.vholovetskyi.bookshop.order.mapper;

import ua.vholovetskyi.onlinestore.order.controller.dto.OrderDetails;
import ua.vholovetskyi.onlinestore.order.controller.dto.OrderList;
import ua.vholovetskyi.onlinestore.order.controller.dto.OrderPagination;
import ua.vholovetskyi.onlinestore.order.model.OrderEntity;

import java.util.List;

public class OrderDtoMapper {

    public static OrderPagination mapToOrderPagination(List<OrderEntity> orders, int totalPage) {
        return OrderPagination.builder()
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

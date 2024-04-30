package ua.vholovetskyi.bookshop.order.mapper;

import ua.vholovetskyi.onlinestore.order.controller.dto.OrderDto;
import ua.vholovetskyi.onlinestore.order.controller.dto.OrderSummary;
import ua.vholovetskyi.onlinestore.order.model.OrderEntity;
import ua.vholovetskyi.onlinestore.order.model.OrderStatus;
import ua.vholovetskyi.onlinestore.order.validator.OrderJson;

import java.time.LocalDate;

public class OrderFactory {

    public static OrderEntity createNewOrder(OrderDto orderDto) {
        return OrderEntity.builder()
                .customerId(orderDto.getCustId())
                .items(orderDto.getItems())
                .status(orderDto.getOrderStatus())
                .build();
    }

    public static OrderEntity createNewOrder(Long id, OrderDto orderDto) {
        return OrderEntity.builder()
                .id(id)
                .customerId(orderDto.getCustId())
                .items(orderDto.getItems())
                .status(orderDto.getOrderStatus())
                .build();
    }

    public static OrderEntity createNewOrder(OrderJson orderJson) {
        return OrderEntity.builder()
                .customerId(orderJson.getCustId())
                .items(orderJson.getItems())
                .status(OrderStatus.parseString(orderJson.getStatus()).orElseThrow())
                .orderDate(LocalDate.parse(orderJson.getOrderDate()))
                .build();
    }

    public static OrderSummary createOrderSummary(OrderEntity newOrder) {
        return OrderSummary.builder()
                .id(newOrder.getId())
                .orderDate(newOrder.getOrderDate())
                .status(newOrder.getStatus())
                .totalProduct(newOrder.getItems().size())
                .build();
    }
}
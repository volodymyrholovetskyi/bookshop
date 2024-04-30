package ua.vholovetskyi.bookshop.order.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ua.vholovetskyi.onlinestore.order.model.OrderStatus;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderList {
    private Long id;
    private int totalProduct;
    private OrderStatus status;
    private LocalDate orderDate;
}

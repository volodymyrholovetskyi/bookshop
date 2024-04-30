package ua.vholovetskyi.bookshop.order.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ua.vholovetskyi.bookshop.order.model.OrderStatus;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-22
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {

    private Long id;
    private List<String> items;
    private OrderStatus status;
    private Long custId;
    private LocalDate orderDate;

}

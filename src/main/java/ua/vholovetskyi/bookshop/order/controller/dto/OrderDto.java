package ua.vholovetskyi.bookshop.order.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ua.vholovetskyi.bookshop.order.model.OrderStatus;

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
public class OrderDto {
    @Size(min = 1, message = "{order.item.min.size}")
    private List<@NotBlank(message = "{order.field.isBlank}") String> items;
    @NotNull(message = "{order.filed.required}")
    private Long custId;
    @NotNull(message = "{order.filed.required}")
    private OrderStatus orderStatus;
}

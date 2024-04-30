package ua.vholovetskyi.bookshop.order.controller.dto;

import jakarta.validation.constraints.NotNull;
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
public class OrderFilteringDto {

    @NotNull(message = "{order.field.required}")
    private Long custId;
    private OrderStatus status;
    private LocalDate from;
    private LocalDate to;
}

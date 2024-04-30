package ua.vholovetskyi.bookshop.order.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPaginationDto {

    @NotNull(message = "{order.field.required}")
    private OrderFilteringDto filter;
    @NotNull(message = "{order.field.required}")
    private Integer pageNumber;
    @NotNull(message = "{order.field.required}")
    @Min(value = 1L, message = "{order.min.size}")
    private Integer size;
}

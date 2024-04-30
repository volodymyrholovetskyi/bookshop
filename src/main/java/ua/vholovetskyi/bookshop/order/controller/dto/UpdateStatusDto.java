package ua.vholovetskyi.bookshop.order.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-22
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateStatusDto {
    @NotBlank(message = "{order.field.isBlank}")
    private String status;
}

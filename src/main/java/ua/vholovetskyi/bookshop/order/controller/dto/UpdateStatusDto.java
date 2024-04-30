package ua.vholovetskyi.bookshop.order.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateStatusDto {
    @NotBlank(message = "{order.field.isBlank}")
    private String status;
}

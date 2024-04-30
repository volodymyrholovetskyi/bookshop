package ua.vholovetskyi.bookshop.order.validator;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ua.vholovetskyi.onlinestore.commons.annotation.DateFormatValidation;
import ua.vholovetskyi.onlinestore.commons.annotation.StatusValidation;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderJson {

    @Size(min = 1)
    private List<@NotBlank String> items;
    @NotNull
    private Long custId;
    @StatusValidation
    private String status;
    @DateFormatValidation
    private String orderDate;
}

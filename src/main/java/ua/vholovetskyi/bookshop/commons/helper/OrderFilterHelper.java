package ua.vholovetskyi.bookshop.commons.helper;

import ua.vholovetskyi.onlinestore.order.controller.dto.OrderFilteringDto;

import java.util.Objects;
public final class OrderFilterHelper {

    private OrderFilterHelper() {
        throw new UnsupportedOperationException("Not allowed to create an instance!");
    }

    public static boolean hasOnlyFilteringByCustId(OrderFilteringDto filter) {
        return Objects.isNull(filter.getStatus())
                && Objects.isNull(filter.getFrom())
                && Objects.isNull(filter.getTo());
    }
    public static boolean hasFilteringByCustIdAndStatus(OrderFilteringDto filter) {
        return Objects.nonNull(filter.getStatus())
                && Objects.isNull(filter.getFrom())
                && Objects.isNull(filter.getTo());
    }

    public static boolean hasFilteringByCustIdAndOrderDate(OrderFilteringDto filter) {
        return Objects.isNull(filter.getStatus())
                && Objects.nonNull(filter.getFrom())
                && Objects.nonNull(filter.getTo());
    }
}

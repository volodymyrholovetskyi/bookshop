package ua.vholovetskyi.bookshop.data;

import org.junit.jupiter.params.provider.Arguments;
import ua.vholovetskyi.bookshop.customer.model.CustomerEntity;
import ua.vholovetskyi.bookshop.order.controller.dto.*;
import ua.vholovetskyi.bookshop.order.model.OrderEntity;
import ua.vholovetskyi.bookshop.order.model.OrderStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public abstract class OrderBuilder {

    protected static final String ORDER_NOT_FOUND_MESSES = "Order with ID: 1 not found!";
    protected static final String CUSTOMER_NOT_FOUND_MESSES = "Customer with ID: 1 not found!";

    protected OrderDto givenOrderDto() {
        return OrderDto.builder()
                .custId(1L)
                .items(List.of(
                        "Effective Java",
                        "Concurrency Java"
                ))
                .status(OrderStatus.NEW)
                .build();
    }

    protected OrderEntity givenOrder() {
        return OrderEntity.builder()
                .items(List.of(
                        "Effective Java",
                        "Concurrency Java"
                ))
                .customerId(1L)
                .status(OrderStatus.NEW)
                .build();
    }

    protected OrderDto givenUpdateOrderDto() {
        return OrderDto.builder()
                .custId(1L)
                .items(List.of(
                        "Java Database"
                ))
                .status(OrderStatus.PAID)
                .build();
    }

    protected OrderSummary givenOrderSummary() {
        return OrderSummary.builder()
                .id(1L)
                .orderDate(LocalDate.of(2024, 4, 25))
                .status(OrderStatus.NEW)
                .totalProduct(2)
                .build();
    }

    protected CustomerEntity givenCustomer() {
        return CustomerEntity.builder()
                .firstName("First")
                .lastName("Customer")
                .email("first@gamil.com")
                .city("New York")
                .street("London 1")
                .phone("0987656487")
                .zipCode("66-887")
                .build();
    }

    protected List<OrderList> givenOrderList() {
        return List.of(
                OrderList.builder()
                        .id(1L)
                        .orderDate(LocalDate.of(2024, 5, 5))
                        .totalProduct(2)
                        .status(OrderStatus.SHIPPED)
                        .build()
        );
    }

    protected OrderPaginationDto givenOrderPaginationDto() {
        return OrderPaginationDto.builder()
                .filter(givenOrderFilteringDto())
                .pageNumber(0)
                .size(4)
                .build();
    }

    protected OrderPaginationDto givenOrderPaginationByCustIdDto() {
        return OrderPaginationDto.builder()
                .filter(givenOrderFilteringByCustIdDto())
                .pageNumber(0)
                .size(5)
                .build();
    }

    protected OrderPaginationDto givenOrderPaginationWithCustId3() {
        return OrderPaginationDto.builder()
                .filter(givenOrderFilteringWithCustId3())
                .pageNumber(0)
                .size(5)
                .build();
    }

    protected OrderPaginationDto givenOrderPaginationByCustIdAndStatusDto() {
        return OrderPaginationDto.builder()
                .filter(givenOrderFilteringByCustIdAndStatusDto())
                .pageNumber(0)
                .size(5)
                .build();
    }

    protected OrderPaginationDto givenOrderPaginationByCustIdAndOrderDateDto() {
        return OrderPaginationDto.builder()
                .filter(givenOrderFilteringByCustIdAndOrderDateDto())
                .pageNumber(0)
                .size(5)
                .build();
    }

    protected OrderPaginationDto givenOrderPaginationByCustIdAndStatusAndOrderDateDto() {
        return OrderPaginationDto.builder()
                .filter(givenOrderFilteringByCustIdAndStatusAndOrderDateDto())
                .pageNumber(0)
                .size(5)
                .build();
    }

    protected OrderFilteringDto givenOrderFilteringDto() {
        return OrderFilteringDto.builder()
                .custId(1L)
                .status(OrderStatus.NEW)
                .from(LocalDate.of(2024, 4, 1))
                .to(LocalDate.of(2024, 5, 1))
                .build();
    }

    protected OrderFilteringDto givenOrderFilteringByCustIdDto() {
        return OrderFilteringDto.builder()
                .custId(1L)
                .build();
    }

    protected OrderFilteringDto givenOrderFilteringWithCustId3() {
        return OrderFilteringDto.builder()
                .custId(3L)
                .build();
    }

    protected OrderFilteringDto givenOrderFilteringByCustIdAndStatusDto() {
        return OrderFilteringDto.builder()
                .custId(1L)
                .status(OrderStatus.NEW)
                .build();
    }

    protected OrderFilteringDto givenOrderFilteringByCustIdAndOrderDateDto() {
        return OrderFilteringDto.builder()
                .custId(1L)
                .from(LocalDate.of(2024, 5, 10))
                .to(LocalDate.of(2024, 5, 15))
                .build();
    }

    protected OrderFilteringDto givenOrderFilteringByCustIdAndStatusAndOrderDateDto() {
        return OrderFilteringDto.builder()
                .custId(1L)
                .status(OrderStatus.PAID)
                .from(LocalDate.of(2024, 5, 5))
                .to(LocalDate.of(2024, 5, 20))
                .build();
    }

    protected OrderPagination givenOrderPagination() {
        return OrderPagination.builder()
                .list(List.of(
                        OrderList.builder()
                                .id(1L)
                                .totalProduct(2)
                                .status(OrderStatus.NEW)
                                .orderDate(LocalDate.of(2024, 4, 25))
                                .build(),
                        OrderList.builder()
                                .id(2L)
                                .totalProduct(4)
                                .status(OrderStatus.PAID)
                                .orderDate(LocalDate.of(2024, 4, 28))
                                .build()
                ))
                .totalPage(5)
                .build();
    }

    protected UploadOrder givenUploadOrder() {
        return UploadOrder.builder()
                .importedRecord(10)
                .nonImportedRecord(2)
                .build();
    }

    protected OrderDetails givenOrderDetails() {
        return OrderDetails.builder()
                .custId(1L)
                .items(List.of(
                        "Effective Java",
                        "Concurrency Java"
                ))
                .status(OrderStatus.NEW)
                .orderDate(LocalDate.of(2024, 4, 29))
                .build();
    }

    protected List<OrderEntity> givenOrders() {
        return List.of(
                OrderEntity.builder()
                        .items(List.of(
                                "Effective Java",
                                "Concurrency Java",
                                "Java 8 in Action"
                        ))
                        .orderDate(LocalDate.of(2024, 5, 9))
                        .customerId(1L)
                        .status(OrderStatus.NEW)
                        .build(),
                OrderEntity.builder()
                        .items(List.of(
                                "Effective Java",
                                "Concurrency Java",
                                "Java Database",
                                "Java 8 in Action"

                        ))
                        .orderDate(LocalDate.of(2024, 5, 9))
                        .customerId(1L)
                        .status(OrderStatus.NEW)
                        .build(),
                OrderEntity.builder()
                        .items(List.of(
                                "Effective Java",
                                "Concurrency Java",
                                "Java Database"
                        ))
                        .orderDate(LocalDate.of(2024, 5, 9))
                        .status(OrderStatus.SHIPPED)
                        .customerId(1L)
                        .build(),
                OrderEntity.builder()
                        .items(List.of(
                                "Effective Java",
                                "Concurrency Java"
                        ))
                        .orderDate(LocalDate.of(2024, 5, 8))
                        .status(OrderStatus.SHIPPED)
                        .customerId(1L)
                        .build(),
                OrderEntity.builder()
                        .items(List.of(
                                "Effective Java",
                                "Concurrency Java"
                        ))
                        .customerId(1L)
                        .orderDate(LocalDate.of(2024, 5, 2))
                        .status(OrderStatus.CANCELED)
                        .build(),
                OrderEntity.builder()
                        .items(List.of(
                                "Effective Java",
                                "Concurrency Java",
                                "Java Database",
                                "Java 8 in Action"
                        ))
                        .customerId(1L)
                        .orderDate(LocalDate.of(2024, 5, 1))
                        .status(OrderStatus.PAID)
                        .build(),
                OrderEntity.builder()
                        .items(List.of(
                                "Effective Java",
                                "Concurrency Java"
                        ))
                        .orderDate(LocalDate.of(2024, 5, 10))
                        .customerId(1L)
                        .status(OrderStatus.NEW)
                        .build(),
                OrderEntity.builder()
                        .items(List.of(
                                "Effective Java",
                                "Concurrency Java",
                                "Java Database",
                                "Java 8 in Action"
                        ))
                        .customerId(1L)
                        .orderDate(LocalDate.of(2024, 5, 10))
                        .status(OrderStatus.CANCELED)
                        .build(),
                OrderEntity.builder()
                        .items(List.of(
                                "Effective Java",
                                "Concurrency Java"
                        ))
                        .orderDate(LocalDate.of(2024, 5, 3))
                        .customerId(1L)
                        .status(OrderStatus.NEW)
                        .build(),
                OrderEntity.builder()
                        .items(List.of(
                                "Effective Java",
                                "Concurrency Java",
                                "Java Database",
                                "Java 8 in Action"
                        ))
                        .orderDate(LocalDate.of(2024, 5, 5))
                        .status(OrderStatus.PAID)
                        .customerId(1L)
                        .build()
        );
    }

    protected static Stream<Arguments> wrongOrders() {
        return Stream.of(
                Arguments.of(OrderDto.builder()
                        .items(List.of(" ", "  "))
                        .custId(1L)
                        .build()),
                Arguments.of(OrderDto.builder()
                        .items(null)
                        .custId(null)
                        .build()
                ),
                Arguments.of(OrderDto.builder()
                        .items(List.of("", "  "))
                        .build()
                ),
                Arguments.of(OrderDto.builder()
                        .items(List.of(""))
                        .build()
                ),
                Arguments.of(OrderDto.builder()
                        .build()));
    }
}

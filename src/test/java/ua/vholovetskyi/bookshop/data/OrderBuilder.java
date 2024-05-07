package ua.vholovetskyi.bookshop.data;

import org.junit.jupiter.params.provider.Arguments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ua.vholovetskyi.bookshop.customer.model.CustomerEntity;
import ua.vholovetskyi.bookshop.order.controller.dto.*;
import ua.vholovetskyi.bookshop.order.model.OrderEntity;
import ua.vholovetskyi.bookshop.order.model.OrderStatus;

import java.time.LocalDate;
import java.util.ArrayList;
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

    protected Page<OrderEntity> givenPageOrder() {
        return new PageImpl<OrderEntity>(
                givenOrders(),
                PageRequest.of(0, 4),
                4);
    }

    protected OrderEntity givenOrder() {
        var items = new ArrayList<String>();
        items.add("Effective Java");
        items.add("Concurrency Java");
        return OrderEntity.builder()
                .items(items)
                .customerId(1L)
                .status(OrderStatus.NEW)
                .build();
    }

    protected OrderDto givenUpdateOrderDto() {
        var items = new ArrayList<String>();
        items.add("Java Database");
        return OrderDto.builder()
                .custId(1L)
                .items(items)
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

    protected OrderSearchRequest givenOrderPaginationDto() {
        return OrderSearchRequest.builder()
                .search(givenSearchRequest())
                .pageNumber(0)
                .size(4)
                .build();
    }

    protected OrderSearchRequest givenOrderPaginationByCustIdDto() {
        return OrderSearchRequest.builder()
                .search(givenOrderSearchCustIdDto())
                .pageNumber(0)
                .size(5)
                .build();
    }

    protected OrderSearchRequest givenOrderPaginationWithCustId3() {
        return OrderSearchRequest.builder()
                .search(givenOrderFilteringWithCustId3())
                .pageNumber(0)
                .size(5)
                .build();
    }

    protected OrderSearchRequest givenOrderPaginationByCustIdAndStatusDto() {
        return OrderSearchRequest.builder()
                .search(givenOrderFilteringByCustIdAndStatusDto())
                .pageNumber(0)
                .size(5)
                .build();
    }

    protected OrderSearchRequest givenOrderPaginationByCustIdAndOrderDateDto() {
        return OrderSearchRequest.builder()
                .search(givenOrderFilteringByCustIdAndOrderDateDto())
                .pageNumber(0)
                .size(5)
                .build();
    }

    protected OrderSearchRequest givenOrderPaginationByCustIdAndStatusAndOrderDateDto() {
        return OrderSearchRequest.builder()
                .search(givenOrderFilteringByCustIdAndStatusAndOrderDateDto())
                .pageNumber(0)
                .size(5)
                .build();
    }

    protected SearchRequest givenSearchRequest() {
        return SearchRequest.builder()
                .custId(1L)
                .status(OrderStatus.NEW)
                .from(LocalDate.of(2024, 4, 1))
                .to(LocalDate.of(2024, 5, 1))
                .build();
    }

    protected SearchRequest givenOrderSearchCustIdDto() {
        return SearchRequest.builder()
                .custId(1L)
                .build();
    }

    protected SearchRequest givenOrderFilteringWithCustId3() {
        return SearchRequest.builder()
                .custId(3L)
                .build();
    }

    protected SearchRequest givenOrderFilteringByCustIdAndStatusDto() {
        return SearchRequest.builder()
                .custId(1L)
                .status(OrderStatus.NEW)
                .build();
    }

    protected SearchRequest givenOrderFilteringByCustIdAndOrderDateDto() {
        return SearchRequest.builder()
                .custId(1L)
                .from(LocalDate.of(2024, 5, 10))
                .to(LocalDate.of(2024, 5, 15))
                .build();
    }

    protected SearchRequest givenOrderFilteringByCustIdAndStatusAndOrderDateDto() {
        return SearchRequest.builder()
                .custId(1L)
                .status(OrderStatus.PAID)
                .from(LocalDate.of(2024, 5, 5))
                .to(LocalDate.of(2024, 5, 20))
                .build();
    }

    protected OrderSearchResponse givenOrderSearchResponse() {
        return OrderSearchResponse.builder()
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

package ua.vholovetskyi.bookshop.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.vholovetskyi.onlinestore.order.model.OrderEntity;
import ua.vholovetskyi.onlinestore.order.model.OrderStatus;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    Page<OrderEntity> findAllByCustomerId(Long customerId,
                                          Pageable pageable);

    Page<OrderEntity> findAllByCustomerIdAndStatus(
            Long customerId,
            OrderStatus status,
            Pageable pageable);

    Page<OrderEntity> findAllByCustomerIdAndOrderDateIsBetween(
            Long customerId,
            LocalDate from,
            LocalDate to,
            Pageable pageable);

    Page<OrderEntity> findAllByCustomerIdAndOrderDateIsBetweenAndStatus(
            Long customerId,
            LocalDate from,
            LocalDate to,
            OrderStatus status,
            Pageable pageable);

    List<OrderEntity> findAllByCustomerId(Long customerId);

    List<OrderEntity> findAllByCustomerIdAndStatus(
            Long customerId,
            OrderStatus status);

    List<OrderEntity> findAllByCustomerIdAndOrderDateIsBetween(
            Long customerId,
            LocalDate from,
            LocalDate to);

    List<OrderEntity> findAllByCustomerIdAndOrderDateIsBetweenAndStatus(
            Long customerId,
            LocalDate from,
            LocalDate to,
            OrderStatus status);

}

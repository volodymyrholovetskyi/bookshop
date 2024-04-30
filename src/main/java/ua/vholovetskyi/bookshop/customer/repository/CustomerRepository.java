package ua.vholovetskyi.bookshop.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.vholovetskyi.onlinestore.customer.model.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    boolean existsByEmail(String email);
}

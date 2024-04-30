package ua.vholovetskyi.bookshop.customer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vholovetskyi.bookshop.customer.exception.CustomerNotFoundException;
import ua.vholovetskyi.bookshop.customer.exception.EmailAlreadyExistsException;
import ua.vholovetskyi.bookshop.customer.model.CustomerEntity;
import ua.vholovetskyi.bookshop.customer.repository.CustomerRepository;

import java.util.List;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-20
 */
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepo;

    public List<CustomerEntity> findAll() {
        return customerRepo.findAll();
    }

    @Transactional
    public CustomerEntity createCustomer(CustomerEntity customer) {
        validateCustomerEmailExists(customer.getEmail());
        return customerRepo.save(customer);
    }

    @Transactional
    public CustomerEntity updateCustomer(CustomerEntity customer) {
        return customerRepo.findById(customer.getId())
                .map(loadCustomer -> {
                    validateCustomerEmailExists(loadCustomer, customer);
                    return customerRepo.save(loadCustomer.updateFields(customer));
                })
                .orElseThrow(() -> new CustomerNotFoundException(customer.getId()));
    }

    public void deleteById(Long id) {
        validateCustomerIdExists(id);
        customerRepo.deleteById(id);
    }

    private void validateCustomerEmailExists(String email) {
        if (customerRepo.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email);
        }
    }

    private void validateCustomerEmailExists(CustomerEntity loadCustomer, CustomerEntity customer) {
        if (customerRepo.existsByEmail(customer.getEmail()) && !loadCustomer.equalEmails(customer.getEmail())) {
            throw new EmailAlreadyExistsException(customer.getEmail());
        }
    }

    private void validateCustomerIdExists(Long id) {
        if (!customerRepo.existsById(id)) {
            throw new CustomerNotFoundException(id);
        }
    }
}

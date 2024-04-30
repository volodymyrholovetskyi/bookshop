package ua.vholovetskyi.bookshop.customer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.vholovetskyi.onlinestore.customer.dto.CustomerDto;
import ua.vholovetskyi.onlinestore.customer.model.CustomerEntity;
import ua.vholovetskyi.onlinestore.customer.service.CustomerService;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {

    public static final Long EMPTY_ID = null;
    private final CustomerService customerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerEntity> findAll() {
        return customerService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerEntity createCustomer(@RequestBody @Valid CustomerDto customerDto) {
        return customerService.createCustomer(toCustomerEntity(EMPTY_ID, customerDto));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CustomerEntity updateCustomer(@PathVariable Long id, @RequestBody @Valid CustomerDto customerDto) {
        return customerService.updateCustomer(toCustomerEntity(id, customerDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        customerService.deleteById(id);
    }

    private CustomerEntity toCustomerEntity(Long id, CustomerDto customerDto) {
        return CustomerEntity.builder()
                .id(id)
                .firstName(customerDto.getFirstName())
                .lastName(customerDto.getLastName())
                .email(customerDto.getEmail())
                .phone(customerDto.getPhone())
                .street(customerDto.getStreet())
                .city(customerDto.getCity())
                .zipCode(customerDto.getZipCode())
                .build();
    }
}

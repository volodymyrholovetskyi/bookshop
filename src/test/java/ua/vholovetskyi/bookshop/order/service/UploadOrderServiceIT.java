package ua.vholovetskyi.bookshop.order.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import ua.vholovetskyi.bookshop.customer.repository.CustomerRepository;
import ua.vholovetskyi.bookshop.data.CustomerBuilder;
import ua.vholovetskyi.bookshop.order.repository.OrderRepository;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

@SpringBootTest
@AutoConfigureTestDatabase
class UploadOrderServiceIT extends CustomerBuilder {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UploadOrderService orderService;

    @Test
    void uploadOrders() throws IOException {
        //given
        givenSaveCustomer();
        var resource = new ClassPathResource("test.json");

        //when
        var uploadOrder = orderService.uploadOrders(resource.getFilename(), resource.getInputStream());

        //then
        assertThat(uploadOrder.getImportedRecord()).isEqualTo(2);
        assertThat(uploadOrder.getNonImportedRecord()).isEqualTo(3);
    }

    private void givenSaveCustomer() {
        customerRepository.save(givenCustomer());
    }
}
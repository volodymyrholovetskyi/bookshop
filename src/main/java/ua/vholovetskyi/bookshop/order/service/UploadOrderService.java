package ua.vholovetskyi.bookshop.order.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vholovetskyi.onlinestore.customer.model.CustomerEntity;
import ua.vholovetskyi.onlinestore.customer.repository.CustomerRepository;
import ua.vholovetskyi.onlinestore.order.controller.dto.UploadOrder;
import ua.vholovetskyi.onlinestore.order.exception.UploadOrderException;
import ua.vholovetskyi.onlinestore.order.model.OrderEntity;
import ua.vholovetskyi.onlinestore.order.repository.OrderRepository;
import ua.vholovetskyi.onlinestore.order.validator.OrderJson;
import ua.vholovetskyi.onlinestore.order.validator.OrderJsonValidator;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ua.vholovetskyi.onlinestore.order.mapper.OrderFactory.createNewOrder;

@Service
@Slf4j
public class UploadOrderService {

    private final int batchSize;
    private final ObjectMapper objectMapper;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderJsonValidator validator;

    public UploadOrderService(@Value("${order.batch.size}") int batchSize, ObjectMapper objectMapper, OrderRepository orderRepository,
                              CustomerRepository customerRepository, OrderJsonValidator validator) {
        this.batchSize = batchSize;
        this.objectMapper = objectMapper;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.validator = validator;
    }

    @Transactional
    public UploadOrder uploadOrders(String fileName, InputStream inputStream) {
        return parseAndSaveOrders(fileName, inputStream);
    }

    private UploadOrder parseAndSaveOrders(String fileName, InputStream inputStream) {
        log.info("Start of INSERT uploaded %s file to the DB...".formatted(fileName));
        var batchItems = new ArrayList<OrderEntity>(batchSize);
        var response = new UploadOrder();
        try (JsonParser jsonParser = objectMapper.createParser(inputStream)) {
            if (jsonParser.nextToken() == JsonToken.START_ARRAY) {
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    var orderJson = objectMapper.readValue(jsonParser, OrderJson.class);
                    var isValid = isValidOrder(orderJson);
                    response.incrementCounter(isValid);
                    if (isValid) {
                        batchItems.add(createNewOrder(orderJson));
                        batchProcessing(batchItems);
                    }
                }
            }
            saveAllOrders(batchItems);
            log.info("End of INSERT to DB...");
        } catch (
                IOException e) {
            log.warn("Error in uploadOrders() method! Error message: %s".formatted(e.getMessage()));
            throw new UploadOrderException(e);
        }
        return response;
    }

    private boolean isValidOrder(OrderJson orderJson) {
        return validator.isValid(orderJson) && findById(orderJson.getCustId()).isPresent();
    }

    private void batchProcessing(List<OrderEntity> orders) {
        if (orders.size() == batchSize) {
            saveAllOrders(orders);
        }
    }

    private void saveAllOrders(List<OrderEntity> orders) {
        if (!orders.isEmpty()) {
            var savedOrders = orderRepository.saveAllAndFlush(orders);
            orders.clear();
            log.info("Batch: %d orders...".formatted(savedOrders.size()));
        }
    }

    private Optional<CustomerEntity> findById(Long id) {
        return customerRepository.findById(id);
    }
}

package ua.vholovetskyi.bookshop.order.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vholovetskyi.bookshop.customer.model.CustomerEntity;
import ua.vholovetskyi.bookshop.customer.repository.CustomerRepository;
import ua.vholovetskyi.bookshop.order.controller.dto.UploadOrder;
import ua.vholovetskyi.bookshop.order.exception.UploadOrderException;
import ua.vholovetskyi.bookshop.order.model.OrderEntity;
import ua.vholovetskyi.bookshop.order.validator.OrderJson;
import ua.vholovetskyi.bookshop.order.validator.OrderJsonValidator;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Optional;

import static ua.vholovetskyi.bookshop.order.mapper.OrderFactory.createNewOrder;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-24
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UploadOrderService {

    private final ObjectMapper objectMapper;
    private final CustomerRepository customerRepository;
    private final OrderJsonValidator validator;
    private final BatchOrderService batchOrderService;

    @Transactional
    public UploadOrder uploadOrders(String fileName, InputStream inputStream) {
        return parseOrders(fileName, inputStream);
    }

    private UploadOrder parseOrders(String fileName, InputStream inputStream) {
        log.info("Start parse uploaded %s file...".formatted(fileName));
        var orders = new ArrayList<OrderEntity>();
        var response = new UploadOrder();
        try (JsonParser jsonParser = objectMapper.createParser(inputStream)) {
            if (jsonParser.nextToken() == JsonToken.START_ARRAY) {
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    var orderJson = objectMapper.readValue(jsonParser, OrderJson.class);
                    var validOrder = isValidOrder(orderJson);
                    response.incrementCounter(validOrder);
                    if (validOrder) {
                        orders.add(createNewOrder(orderJson));
                        batchOrderService.batchProcessing(orders, true);
                    }
                }
            }
            batchOrderService.batchProcessing(orders, false);
            log.info("End parse uploaded %s file...".formatted(fileName));
        } catch (
                IOException e) {
            log.warn("Error in parseOrders() method! Error message: %s".formatted(e.getMessage()));
            throw new UploadOrderException(e);
        }
        return response;
    }

    private boolean isValidOrder(OrderJson orderJson) {
        return validator.isValid(orderJson) && findById(orderJson.getCustId()).isPresent();
    }

    private Optional<CustomerEntity> findById(Long id) {
        return customerRepository.findById(id);
    }
}

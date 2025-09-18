package com.ngo.customers.data.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngo.customers.data.domain.model.CustomerAddressData;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomerKafkaProducer {

    private static final String CUSTOMR_TOPIC = "customer-topic";
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendCustomer(CustomerAddressData customer) {
        try {
            String customerJson = objectMapper.writeValueAsString(customer);
            kafkaTemplate.send(CUSTOMR_TOPIC, customerJson);
        } catch (JsonProcessingException ex) {
            log.error("Error serializando customer", ex);
        }
    }
}

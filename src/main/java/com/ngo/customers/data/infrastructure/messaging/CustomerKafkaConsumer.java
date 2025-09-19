package com.ngo.customers.data.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngo.customers.data.domain.model.CustomerAddressData;
import com.ngo.customers.data.infrastructure.repository.MongoCustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomerKafkaConsumer {

    private final MongoCustomerRepository mongoCustomerRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "customer-topic", groupId = "customer-group")
    public void consumeCustomer(ConsumerRecord<String, String> record) {
        try {
            String message = record.value();
            CustomerAddressData customer = objectMapper.readValue(message, CustomerAddressData.class);
            log.info("Mensaje recibido del topic de customers para el customer: {}", customer);
            mongoCustomerRepository.saveCustomer(customer);
        } catch (Exception ex) {
            log.error("Error deserializando mensaje del topic", ex);
        }
    }
}


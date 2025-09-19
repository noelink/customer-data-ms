package com.ngo.customers.data.infrastructure.repository;

import com.ngo.customers.data.domain.model.CustomerAddressData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MongoCustomerRepository {

    private final MongoTemplate mongoTemplate;

    public void saveCustomer(CustomerAddressData customer) {
        mongoTemplate.insert(customer, "customers-address");
    }

    public List<CustomerAddressData> getAllCustomers() {
        return mongoTemplate.findAll(CustomerAddressData.class, "customers-address");
    }
}

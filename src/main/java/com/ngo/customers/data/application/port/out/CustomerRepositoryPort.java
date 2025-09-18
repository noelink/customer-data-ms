package com.ngo.customers.data.application.port.out;

import com.ngo.customers.data.domain.model.CustomerData;

import java.util.List;
import java.util.Optional;

public interface CustomerRepositoryPort {

    List<CustomerData> getAllCustomers(int page, int pageSize);

    Optional<CustomerData> fetchCustomer(String id);
}

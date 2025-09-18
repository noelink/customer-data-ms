package com.ngo.customers.data.application.port.out;

import com.ngo.customers.data.domain.model.CustomerData;

import java.util.List;

public interface CustomerRepositoryPort {

    List<CustomerData> getAllCustomers(int page, int pageSize);
}

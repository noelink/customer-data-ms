package com.ngo.customers.data.application.service;

import com.ngo.customers.data.application.port.in.CustomerUseCase;
import com.ngo.customers.data.application.port.out.CustomerRepositoryPort;
import com.ngo.customers.data.domain.model.CustomerAddressData;
import com.ngo.customers.data.domain.model.CustomerData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService implements CustomerUseCase {

    private final CustomerRepositoryPort customerRepository;

    @Override
    public List<CustomerData> getAllCustomers(int page, int size) {
        return customerRepository.getAllCustomers(page, size);
    }

    @Override
    public CustomerAddressData fetchCustomer(String id) {
        return customerRepository.fetchCustomer(id).get();
    }
}

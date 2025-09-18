package com.ngo.customers.data.application.service;

import com.ngo.customers.data.application.port.in.CustomerUseCase;
import com.ngo.customers.data.domain.model.CustomerData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService implements CustomerUseCase {

    @Override
    public List<CustomerData> getAllCustomers(int page, int size) {
        return List.of();
    }
}

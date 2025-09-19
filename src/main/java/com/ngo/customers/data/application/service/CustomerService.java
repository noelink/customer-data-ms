package com.ngo.customers.data.application.service;

import com.ngo.customers.data.application.port.in.CustomerUseCase;
import com.ngo.customers.data.application.port.out.CustomerRepositoryPort;
import com.ngo.customers.data.domain.model.CustomerAddressData;
import com.ngo.customers.data.domain.model.CustomerData;
import com.ngo.customers.data.infrastructure.repository.MongoCustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomerService implements CustomerUseCase {

    private final CustomerRepositoryPort customerRepository;
    private final MongoCustomerRepository mongoCustomerRepository;

    @Override
    public List<CustomerData> getAllCustomers(int page, int size) {
        return customerRepository.getAllCustomers(page, size);
    }

    @Override
    public CustomerAddressData fetchCustomer(String id) {
        return customerRepository.fetchCustomer(id).get();
    }

    @Override
    public List<CustomerAddressData> fetchCustomerFromMongo() {
        List<CustomerAddressData> customerAddressDataList = mongoCustomerRepository.getAllCustomers();
        log.info("recuperando customers de mongo con {} elementos listados:", customerAddressDataList.size());
        customerAddressDataList.forEach(log::info);
        return mongoCustomerRepository.getAllCustomers();
    }
}

package com.ngo.customers.data.application.port.in;

import com.ngo.customers.data.domain.model.CustomerAddressData;
import com.ngo.customers.data.domain.model.CustomerData;
import java.util.List;

public interface CustomerUseCase {

    List<CustomerData> getAllCustomers(int page, int size);

    CustomerAddressData fetchCustomer(String id);
}

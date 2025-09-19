package com.ngo.customers.data.adapter.controllers.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngo.customers.data.application.port.in.CustomerUseCase;
import com.ngo.customers.data.domain.model.CustomerAddressData;
import com.ngo.customers.data.domain.model.CustomerData;
import com.ngo.customers.data.infrastructure.messaging.CustomerKafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CustomerUseCase customerUseCase;

    @MockitoBean
    CustomerKafkaProducer producer;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testWhenRetrieveCustomerDataIsSuccessful() throws Exception {
        List<CustomerData> customers = Arrays.asList(
                CustomerData.builder().customerId("AAAAAAAADCAAAAAA").firstName("Noel").lastName("Gonzalez").emailAddress("noelgp@hotmail.com").build(),
                CustomerData.builder().customerId("AAAAAAAADCAAAABD").firstName("Ana").lastName("Perez").emailAddress("ana_perez@gmail.com").build()
        );
        when(customerUseCase.getAllCustomers(1, 10)).thenReturn(customers);
        mockMvc.perform(get("/api/customers")
                            .param("page","1")
                            .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value("AAAAAAAADCAAAAAA"))
                .andExpect(jsonPath("$[0].firstName").value("Noel"))
                .andExpect(jsonPath("$[1].customerId").value("AAAAAAAADCAAAABD"))
                .andExpect(jsonPath("$[1].firstName").value("Ana"));
        verify(customerUseCase, times(1)).getAllCustomers(1, 10);
    }

    @Test
    public void testWhenFetchCustomerIsSuccessful() throws Exception {
        CustomerAddressData customer = CustomerAddressData.builder().customerId("AAAAAAAADCAAAAAA").firstName("Noel").lastName("Gonzalez").email("noelgp@hotmail.com").build();
        String customerId = "AAAAAAAADCAAAAAA";
        when(customerUseCase.fetchCustomer(customerId)).thenReturn(customer);

        mockMvc.perform(get("/api/customers/fetch/{id}", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value("AAAAAAAADCAAAAAA"))
                .andExpect(jsonPath("$.firstName").value("Noel"))
                .andExpect(content().json(objectMapper.writeValueAsString(customer)));

        verify(producer, times(1)).sendCustomer(customer);
    }

}

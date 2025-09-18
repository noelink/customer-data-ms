package com.ngo.customers.data.adapter.inbound.controllers;

import com.ngo.customers.data.domain.model.CustomerData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class CustomerController {

    @GetMapping("/customers")
    public List<CustomerData> retrieveCustomerData(){
        return null;
    }

}

package com.ngo.customers.data.adapter.controllers.in;

import com.ngo.customers.data.application.port.in.CustomerUseCase;
import com.ngo.customers.data.domain.model.CustomerData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerUseCase customerUseCase;

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerData>> retrieveCustomerData(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size){
        List<CustomerData> customerList = customerUseCase.getAllCustomers(page, size);
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    @GetMapping("/customers/fetch/{id}")
    public ResponseEntity<CustomerData> fetchCustomer(@PathVariable String id){
        CustomerData customer = customerUseCase.fetchCustomer(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

}

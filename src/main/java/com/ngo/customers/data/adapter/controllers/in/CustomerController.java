package com.ngo.customers.data.adapter.controllers.in;

import com.ngo.customers.data.application.port.in.CustomerUseCase;
import com.ngo.customers.data.domain.model.CustomerData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerUseCase customerUseCase;

    @GetMapping("/customers")
    public List<CustomerData> retrieveCustomerData(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size){
        return null;
    }

}

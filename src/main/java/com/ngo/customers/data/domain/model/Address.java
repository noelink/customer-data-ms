package com.ngo.customers.data.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Address {

    private String street;
    private String number;
    private String city;
    private String state;
    private String country;


}

package com.ngo.customers.data.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
public class CustomerAddressData {

    private String customerId;
    private String firstName;
    private String lastName;
    private Integer birthDay;
    private Integer birthMonth;
    private Integer birthYear;
    private String email;
    private AddressData address;
    private LocalDateTime receivedAt;
}

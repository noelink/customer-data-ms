package com.ngo.customers.data.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class Customer {
    private Long customerId;
    private String firstName;
    private String lastName;
    private Integer birthDay;
    private Integer birthMonth;
    private Integer birthYear;
    private String email;
    private Address address;
    private LocalDateTime receivedAt;
}

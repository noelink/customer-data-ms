package com.ngo.customers.data.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CustomerData {

    private Long customerSk;
    private String customerId;
    private Long currentCDemoSk;
    private Long currentHDemoSk;
    private Long currentAddrSk;
    private Long firstShipToDateSk;
    private Long firstSalesDateSk;
    private String salutation;
    private String firstName;
    private String lastName;
    private String preferredCustomFlag;
    private Integer birthDay;
    private Integer birthMonth;
    private Integer birthYear;
    private String birthCountry;
    private String login;
    private String emailAddress;
    private String lastReviewDate;
}

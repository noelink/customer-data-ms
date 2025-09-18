package com.ngo.customers.data.infrastructure.repository;

import com.ngo.customers.data.application.port.out.CustomerRepositoryPort;
import com.ngo.customers.data.domain.model.AddressData;
import com.ngo.customers.data.domain.model.CustomerAddressData;
import com.ngo.customers.data.domain.model.CustomerData;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SnowflakeCustomerRepository implements CustomerRepositoryPort {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<CustomerData> rowMapperCustomers = (rs, rowNum) -> CustomerData.builder()
            .customerSk(rs.getLong("C_CUSTOMER_SK"))
            .customerId(rs.getString("C_CUSTOMER_ID"))
            .currentCDemoSk(rs.getLong("C_CURRENT_HDEMO_SK"))
            .currentHDemoSk(rs.getLong("C_CURRENT_HDEMO_SK"))
            .currentAddrSk(rs.getLong("C_CURRENT_ADDR_SK"))
            .firstShipToDateSk(rs.getLong("C_FIRST_SHIPTO_DATE_SK"))
            .firstSalesDateSk(rs.getLong("C_FIRST_SALES_DATE_SK"))
            .salutation(rs.getString("C_SALUTATION"))
            .firstName(rs.getString("C_FIRST_NAME"))
            .lastName(rs.getString("C_LAST_NAME"))
            .preferredCustomFlag(rs.getString("C_PREFERRED_CUST_FLAG"))
            .birthDay(rs.getInt("C_BIRTH_DAY"))
            .birthMonth(rs.getInt("C_BIRTH_MONTH"))
            .birthYear(rs.getInt("C_BIRTH_YEAR"))
            .birthCountry(rs.getString("C_BIRTH_COUNTRY"))
            .login(rs.getString("C_LOGIN"))
            .emailAddress(rs.getString("C_EMAIL_ADDRESS"))
            .lastReviewDate(rs.getString("C_LAST_REVIEW_DATE"))
            .build();

    private final RowMapper<CustomerAddressData> rowMapperCustomerAddress = (rs, rowNum) -> CustomerAddressData.builder()
            .customerId(rs.getString("C_CUSTOMER_ID"))
            .firstName(rs.getString("C_FIRST_NAME"))
            .lastName(rs.getString("C_LAST_NAME"))
            .birthDay(rs.getInt("C_BIRTH_DAY"))
            .birthMonth(rs.getInt("C_BIRTH_MONTH"))
            .birthYear(rs.getInt("C_BIRTH_YEAR"))
            .email(rs.getString("C_EMAIL_ADDRESS"))
            .receivedAt(LocalDateTime.now())
            .address(AddressData.builder()
                    .street(rs.getString("CA_STREET_NAME"))
                    .number(rs.getString("CA_STREET_NUMBER"))
                    .city(rs.getString("CA_CITY"))
                    .country(rs.getString("CA_COUNTY"))
                    .state(rs.getString("CA_STATE"))
                    .build())
            .build();

    @Override
    public List<CustomerData> getAllCustomers(int page, int pageSize) {
        //revisar si esta bien este offset de mi consultaa snowflake
        int offset = (page - 1) * pageSize;
        String sql = "SELECT * FROM CUSTOMER ORDER BY C_CUSTOMER_SK LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, rowMapperCustomers, pageSize, offset);
    }

    @Override
    public Optional<CustomerAddressData> fetchCustomer(String id) {
        String sql = "SELECT c.*, a.* " + "FROM CUSTOMER c " +
                "LEFT JOIN CUSTOMER_ADDRESS a ON c.C_CURRENT_ADDR_SK = a.CA_ADDRESS_SK " +
                "WHERE c.C_CUSTOMER_ID = ?";
        try {
            CustomerAddressData customer = jdbcTemplate.queryForObject(sql, rowMapperCustomerAddress, id);
            return Optional.ofNullable(customer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}

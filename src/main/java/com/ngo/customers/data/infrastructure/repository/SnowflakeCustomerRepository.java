package com.ngo.customers.data.infrastructure.repository;

import com.ngo.customers.data.application.port.out.CustomerRepositoryPort;
import com.ngo.customers.data.domain.model.CustomerData;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SnowflakeCustomerRepository implements CustomerRepositoryPort {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<CustomerData> rowMapper = (rs, rowNum) -> CustomerData.builder()
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

    @Override
    public List<CustomerData> getAllCustomers(int page, int pageSize) {
        //revisar si esta bien este offset de mi consultaa snowflake
        int offset = (page - 1) * pageSize;
        String sql = "SELECT * FROM CUSTOMER ORDER BY C_CUSTOMER_SK LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, rowMapper, pageSize, offset);
    }

    @Override
    public Optional<CustomerData> fetchCustomer(String id) {
        String sql = "SELECT * FROM CUSTOMER WHERE C_CUSTOMER_ID = ?";
        try {
            CustomerData customer = jdbcTemplate.queryForObject(sql, rowMapper, id);
            return Optional.ofNullable(customer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}

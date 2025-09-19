package com.ngo.customers.data.infrastructure.config;

import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {
    private final String mongoUrlCustomersDb = "mongodb://admin:admin123@mongo:27017/customer-db?authSource=admin";

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(MongoClients.create(mongoUrlCustomersDb), "customer-db");
    }
}

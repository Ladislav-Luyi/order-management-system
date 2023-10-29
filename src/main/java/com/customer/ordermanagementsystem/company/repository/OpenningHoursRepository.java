package com.customer.ordermanagementsystem.company.repository;

import com.customer.ordermanagementsystem.company.domain.OpenningHours;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface OpenningHoursRepository extends MongoRepository<OpenningHours, String> {
}

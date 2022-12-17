package com.customer.ordermanagementsystem.repository;

import com.customer.ordermanagementsystem.pojos.company.OpenningHours;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface OpenningHoursRepository extends MongoRepository<OpenningHours,String> {
}

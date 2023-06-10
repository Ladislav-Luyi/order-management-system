package com.customer.ordermanagementsystem.repository;

import com.customer.ordermanagementsystem.domain.company.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository  extends MongoRepository<Company,String> {

    @Query("{name:'?0'}")
    Optional<Company> findById(String name);
}

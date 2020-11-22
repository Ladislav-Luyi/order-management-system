package com.customer.ordermanagementsystem.repository;

import com.customer.ordermanagementsystem.pojos.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository  extends CrudRepository<Company,Long> {
}

package com.customer.ordermanagementsystem.company.domain;

import lombok.Data;

@Data
public class CompanyDTO {
    private String status;
    private String statusMessage = "";
}

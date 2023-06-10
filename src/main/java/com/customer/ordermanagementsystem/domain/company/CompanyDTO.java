package com.customer.ordermanagementsystem.domain.company;

import lombok.Data;

@Data
public class CompanyDTO {
    private String status;
    private String statusMessage = "";
}

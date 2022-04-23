package com.customer.ordermanagementsystem.controllers;

import com.customer.ordermanagementsystem.pojos.company.CompanyDTO;
import com.customer.ordermanagementsystem.services.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class CompanyController {
    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }


    @GetMapping("/podnik")
    public String getOpen(Model model){
        model.addAttribute("status", companyService.getOpenAndCloseStoreMessage());
        model.addAttribute("company", new CompanyDTO());

        return "company";
    }

    @PostMapping("/podnik")
    public String setOpen(CompanyDTO companyDTO){

        companyService.openAndCloseStoreWithMessage(companyDTO.getStatus(),
                companyDTO.getStatusMessage());

        return "redirect:/podnik";
    }

}

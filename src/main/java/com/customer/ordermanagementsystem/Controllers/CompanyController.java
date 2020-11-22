package com.customer.ordermanagementsystem.Controllers;

import com.customer.ordermanagementsystem.services.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CompanyController {
    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }


    @GetMapping("/otvorene")
    private String openAndCloseStore(@RequestParam String status){
        log.info("openAndCloseStore method called, Status: " + status);
        companyService.openAndCloseStore(status);

        return companyService.getOpenAndCloseStoreMessage();
    }

    @GetMapping("/otvorene-sprava")
    private String openAndCloseStore(@RequestParam String status, @RequestParam String sprava ){
        log.info("openAndCloseStore method called, Status: " + status + " Sprava: " + sprava);
        companyService.openAndCloseStoreWithMessage(status,sprava);

        return companyService.getOpenAndCloseStoreMessage();
    }

}

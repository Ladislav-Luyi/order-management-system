package com.customer.ordermanagementsystem.Controllers;

import com.customer.ordermanagementsystem.pojos.company.CompanyDTO;
import com.customer.ordermanagementsystem.services.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

        return "open";
    }

    @PostMapping("/podnik")
    public String setOpen(CompanyDTO companyDTO){

        companyService.openAndCloseStoreWithMessage(companyDTO.getStatus(),
                companyDTO.getStatusMessage());

        return "redirect:/podnik";
    }

}

package com.customer.ordermanagementsystem.Controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@Slf4j
@SessionAttributes({"dateDTO"})
@RequestMapping({"/ukaz-menu"})
public class ShowMenuController {

    @RequestMapping()
    public String showMenu(Model model){

        return "show_menu";
    }
}

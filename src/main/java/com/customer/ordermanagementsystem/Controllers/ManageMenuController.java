package com.customer.ordermanagementsystem.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class ManageMenuController {

    @GetMapping("/uprav-menu")
    public String adjustMenu(Model model,
    @RequestParam(value = "selectedDate", required = false) String selectedDate){

        if (selectedDate == null)
            selectedDate = "žiadny";

        model.addAttribute("specificDate", selectedDate);


        return "adjust_menu";
    }



    @PostMapping("/uprav-menu")
    public String adjustMenu(){


        return "redirect:/uprav-menu";
    }

    @GetMapping("/ukaz-menu")
    public String showMenu(Model model){

        return "show_menu";
    }


}

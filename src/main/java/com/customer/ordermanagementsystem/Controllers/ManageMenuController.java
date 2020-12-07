package com.customer.ordermanagementsystem.Controllers;

import com.customer.ordermanagementsystem.pojos.item.menu_item.MenuEditDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@Slf4j
public class ManageMenuController {

    @GetMapping("/uprav-menu")
    public String adjustMenu(Model model){
        model.addAttribute("menuEditDTO", new MenuEditDTO());

        return "adjust_menu";
    }

    @PostMapping("/uprav-menu")
    public String adjustMenu(MenuEditDTO menuEditDTO){
        log.info("Processing menu " + menuEditDTO);

        return "redirect:/uprav-menu";
    }

    @GetMapping("/ukaz-menu")
    public String showMenu(Model model){

        return "show_menu";
    }


}

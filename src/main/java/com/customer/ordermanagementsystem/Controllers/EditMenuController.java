package com.customer.ordermanagementsystem.Controllers;

import com.customer.ordermanagementsystem.pojos.item.Item;
import com.customer.ordermanagementsystem.pojos.item.menu_item.DateDTO;
import com.customer.ordermanagementsystem.pojos.item.menu_item.MenuEditDTO;
import com.customer.ordermanagementsystem.services.AdjustMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@Slf4j
@SessionAttributes({"dateDTO"})
@RequestMapping({"/uprav-menu"})
public class EditMenuController {

    private final AdjustMenuService adjustMenuService;
    private DateDTO dateDTO;

    @Autowired
    public EditMenuController(AdjustMenuService adjustMenuService, DateDTO dateDTO) {
        this.adjustMenuService = adjustMenuService;
        this.dateDTO = dateDTO;
    }

    @RequestMapping()
    public String adjustMenu(Model model){

        model.addAttribute("dateDTO", dateDTO);
        model.addAttribute("menuEditDTO", new MenuEditDTO());

        return "adjust_menu";
    }

    @RequestMapping(params={"loadDate"})
    public String loadDate(Model model, DateDTO dateDTO, MenuEditDTO menuEditDTO ){

        if (dateDTO.getDate() != null) {
            List<Item> items = adjustMenuService.getMenuEditDTOAccordingDate(dateDTO.getDate());
            adjustMenuService.loadMenuEditDTO(menuEditDTO, items);
            adjustMenuService.setTargetDate(dateDTO.getDate());
        }

        model.addAttribute("dateDTO", dateDTO);
        model.addAttribute("menuEditDTO", menuEditDTO);

        return "adjust_menu";
    }


    @PostMapping
    public String adjustMenu(MenuEditDTO menuEditDTO, DateDTO dateDTO){

        log.info("Processing menu " + menuEditDTO);

        menuEditDTO.setSpecificDate(dateDTO.getDate());
        adjustMenuService.transformToItemAndSave(menuEditDTO);

        return "redirect:/uprav-menu";
    }

}

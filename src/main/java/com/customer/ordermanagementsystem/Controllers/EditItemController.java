package com.customer.ordermanagementsystem.Controllers;

import com.customer.ordermanagementsystem.orders.Order;
import com.customer.ordermanagementsystem.orders.OrderPlaceHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Slf4j
@Controller
@SessionAttributes({"orderInfo","order"})

@RequestMapping("/editItem")
public class EditItemController {

    @Autowired
    private OrderPlaceHolder orderPlaceHolder;

    @RequestMapping()
    public String editItem(Model model, Order order, @RequestParam int index){
        System.out.println("Edit item with index: " + index);

        //trying to access data
        System.out.println( orderPlaceHolder.getOrderList().get(index) );



        return "edit";

    }

//    @PostMapping(params={"addElement"})
//    public String addItem(Model model, Order order){
//
//
//
//
//        return "edit";
//
//    }

    @PostMapping()
    public String returnToBasket(Model model, Order order){




        return "redirect:/basket";

    }
}

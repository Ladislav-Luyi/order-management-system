package com.customer.ordermanagementsystem.Controllers;

import com.customer.ordermanagementsystem.orders.Item;
import com.customer.ordermanagementsystem.orders.Order;
import com.customer.ordermanagementsystem.orders.OrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.customer.ordermanagementsystem.orders.Item.Type;

import javax.validation.Valid;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Slf4j
@Controller
@SessionAttributes({"order","orderInfo"})
@RequestMapping("/basket")
public class AddItemsController {


    @GetMapping
    public String showOrderForm(Model model){
        List<Item> items =  Arrays.asList(
                new Item( 1l,"Capri", Item.Type.PIZZA),
                new Item( 2l,"Alte", Item.Type.PIZZA),
                new Item(3l,"Cesnacka", Type.POLIEVKA)
        );


        Item.Type[] types = Type.values();
        for (Type type : types)
        {
            List<Item> tmpList = new ArrayList<>();
            for(Item tmpItem : items)
            {
                if (tmpItem.getType() == type) {
                    tmpList.add(tmpItem);
                }
            }

            if (tmpList.size() == 0)
                continue;

            log.info("continue for " + type.toString());
            model.addAttribute(type.toString(), tmpList);


        }

        model.addAttribute("order", new Order() );

        model.addAttribute("orderInfo", new OrderInfo());

        return "order";

    }

    @PostMapping
    public String processOrder(Order order,@Valid OrderInfo orderInfo, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println("BINDING RESULT ERROR");
            return "order";
        }

        log.info("Processing order: " + order);
        log.info("Processing order: " + orderInfo);

        return "redirect:/order/orderFinished";
    }





}







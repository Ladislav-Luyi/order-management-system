package com.alte.ordermanagementsystem.Controllers;

import com.alte.ordermanagementsystem.orders.Item;
import com.alte.ordermanagementsystem.orders.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alte.ordermanagementsystem.orders.Item.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/order")
public class AddItemsController {



    @GetMapping
    public String showOrderForm(Model model){
//        List<Item> items = new ArrayList<>();
        List<Item> items =  Arrays.asList(
                new Item( 1l,"Capri", Item.Type.PIZZA),
                new Item( 2l,"Alte", Item.Type.PIZZA)
        );


        Item.Type[] types = Type.values();
        for (Type type : types)
        {
            List<Item> tmpList = new ArrayList<>();
            for(Item tmpItem : items)
            {
                if (tmpItem.getType() == type)
                    tmpList.add(tmpItem);
            }

            if (tmpList.size() == 0)
                continue;

            log.info("continue for " + type.toString());
            model.addAttribute(type.toString(), tmpList);
        }

        model.addAttribute("order", new Order() );

        return "order";

    }





}

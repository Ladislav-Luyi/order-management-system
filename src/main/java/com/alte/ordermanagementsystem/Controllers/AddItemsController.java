package com.alte.ordermanagementsystem.Controllers;

import com.alte.ordermanagementsystem.orders.Item;
import com.alte.ordermanagementsystem.orders.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/order")
public class AddItemsController {

    private List<Item> filterByType(List<Item> ingredients, Item.Type type) {

        return ingredients.stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    @GetMapping
    public String showOrderForm(Model model){
        List<Item> orders = new ArrayList<>();
        orders.add(new Item( 1l,"Capri", Item.Type.PIZZA));
        orders.add(new Item( 2l,"Alte", Item.Type.PIZZA));

        Item.Type[] types = Item.Type.values();
        for (Item.Type type : types){
            model.addAttribute(type.toString().toLowerCase(), filterByType(orders, type));
        }

//        for(Order.Type type : types){
//            for(Order order : orders)
//                if (order.getType().toString().toLowerCase().equals(type.toString().toLowerCase()))
//                    model.addAttribute(type.toString().toLowerCase(), order);
//        }

        model.addAttribute("itemList", orders );
        model.addAttribute("order", new Order() );

        return "order";

    }



}

package com.customer.ordermanagementsystem.Controllers;

import com.customer.ordermanagementsystem.orders.*;
import com.customer.ordermanagementsystem.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Slf4j
@Controller
@SessionAttributes({"orderInfo","order"})
@RequestMapping("/basket")
public class AddItemsController {

    @Autowired
    private OrderPlaceHolder orderPlaceHolder;

    @Autowired
    private ItemRepository itemRepository;


    @RequestMapping()
    public String showOrderForm(Model model, Order order){



        List<Item> items = itemRepository.findAll();


        Type[] types = Type.values();
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

        model.addAttribute("orderedItem", orderPlaceHolder.getOrderList());

        return "order";

    }


    @RequestMapping(params={"addElement"})
    public String addElement(Order  order, Model model) {

        log.info("before order: " + orderPlaceHolder);

        if (order.getOrderList() != null)
            orderPlaceHolder.getOrderList().add(order.getOrderList().get(0));

        System.out.println(orderPlaceHolder.getOrderList().toString());

        List<Item> items = itemRepository.findAll();

        Type[] types = Type.values();
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
        //test

        model.addAttribute("orderedItem", orderPlaceHolder.getOrderList());

        // tu by si mal pridat do modelu dalsi polozky

        log.info("Processing order: " + orderPlaceHolder);
        log.info("calling add element!");


//        return "redirect:/basket";
        return "order";
    }


    @RequestMapping(params={"removeElement"})
    public String removeElement(Order  order, Model model) {
        log.info("before removeElement: " + orderPlaceHolder);


        if (order.getOrderList().size() > 0)
////            removed = orderPlaceHolder.getOrderList().remove(order.getOrderList().get(0));
            orderPlaceHolder.getOrderList().remove(order.getIndexToRemove());




        List<Item> items = itemRepository.findAll();

        Type[] types = Type.values();
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
        //test

        model.addAttribute("orderedItem", orderPlaceHolder.getOrderList());

        // tu by si mal pridat do modelu dalsi polozky

        log.info("after removeElement: " + orderPlaceHolder);
        log.info("calling remove element!");





//        return "redirect:/basket";
        return "order";
    }

    @PostMapping
    public String processOrder(Order order, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println("BINDING RESULT ERROR");
            return "order";
        }

        order.setOrderList( orderPlaceHolder.getOrderList() );


        log.info("Processing order: " + order);


        return "redirect:/order/orderInfo";
    }




}







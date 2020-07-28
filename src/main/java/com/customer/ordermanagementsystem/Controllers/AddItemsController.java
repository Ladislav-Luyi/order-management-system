package com.customer.ordermanagementsystem.Controllers;

import com.customer.ordermanagementsystem.orders.*;
import com.customer.ordermanagementsystem.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;


@Slf4j
@Controller
@SessionAttributes({"orderInfo","orderPlaceHolder"})
@RequestMapping("/basket")
public class AddItemsController {

    @Autowired
    private Order order;

    @Autowired
    private ItemRepository itemRepository;


    @RequestMapping()
    public String showOrderForm(Model model, OrderPlaceHolder orderPlaceHolder){



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

        model.addAttribute("orderPlaceHolder", new OrderPlaceHolder() );

        model.addAttribute("orderedItem", order.getOrderList());

        return "order";

    }


    @RequestMapping(params={"addElement"})
    public String addElement(OrderPlaceHolder orderPlaceHolder, Model model) {

        log.info("before order: " + order);

        order.getOrderList().add(orderPlaceHolder.getItem());

//        if (order.getOrderList() != null)
//            order.getOrderList().add(order.getOrderList().get(0));
//
        System.out.println(order.getOrderList().toString());

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

        model.addAttribute("orderedItem", order.getOrderList());

        // tu by si mal pridat do modelu dalsi polozky

        log.info("Processing order: " + order);
        log.info("calling add element!");



        return "order";
    }


    @RequestMapping(params={"removeElement"})
    public String removeElement(OrderPlaceHolder orderPlaceHolder,  Model model) {
        log.info("before removeElement: " + this.order);


        if (order.getOrderList().size() > 0)
////            removed = orderPlaceHolder.getOrderList().remove(order.getOrderList().get(0));
            this.order.getOrderList().remove(orderPlaceHolder.getIndexToRemove());




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

        model.addAttribute("orderedItem", this.order.getOrderList());

        // tu by si mal pridat do modelu dalsi polozky

        log.info("after removeElement: " + this.order);
        log.info("calling remove element!");





        return "order";
    }

    @PostMapping
    public String processOrder(Order order, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println("BINDING RESULT ERROR");
            return "order";
        }

        order.setOrderList( this.order.getOrderList() );


        log.info("Processing order: " + order);


        return "redirect:/order/orderInfo";
    }




}







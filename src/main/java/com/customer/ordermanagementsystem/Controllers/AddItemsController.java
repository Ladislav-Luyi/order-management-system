package com.customer.ordermanagementsystem.Controllers;

import com.customer.ordermanagementsystem.orders.*;
import com.customer.ordermanagementsystem.services.ItemServiceForSpringModel;
import com.customer.ordermanagementsystem.services.OrderServiceForSpringModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@SessionAttributes({"orderInfo","order"})
@RequestMapping("/basket")
public class AddItemsController {

    private final Order order;
    private final ItemServiceForSpringModel itemServiceForSpringModel;
    private final OrderServiceForSpringModel orderServiceForSpringModel;

    @Autowired
    public AddItemsController(Order order, ItemServiceForSpringModel itemServiceForSpringModel, OrderServiceForSpringModel orderServiceForSpringModel) {
        this.order = order;
        this.itemServiceForSpringModel = itemServiceForSpringModel;
        this.orderServiceForSpringModel = orderServiceForSpringModel;
    }

    @RequestMapping()
    public String showOrderForm(Model model){


        itemServiceForSpringModel.addAllItemsToModel(model);

        orderServiceForSpringModel.addOrderedItemsToModel(model, "orderedItems");

        orderServiceForSpringModel.addTotalPrice(model, "totalPrice");

        model.addAttribute("orderPlaceHolder", new OrderPlaceHolder() );

        return "order";
    }


    @RequestMapping(params={"addElement"})
    public String addElement(OrderPlaceHolder orderPlaceHolder, Model model) {

        log.info("before order: " + order);

        order.getOrderList().add(orderPlaceHolder.getItem());

        System.out.println(order.getOrderList().toString());

        itemServiceForSpringModel.addAllItemsToModel(model);

        orderServiceForSpringModel.addOrderedItemsToModel(model, "orderedItems");

        orderServiceForSpringModel.addTotalPrice(model, "totalPrice");

        log.info("Processing order: " + order);
        log.info("calling add element!");

        return "order";
    }


    @RequestMapping(params={"removeElement"})
    public String removeElement(OrderPlaceHolder orderPlaceHolder,  Model model) {
        log.info("before removeElement: " + this.order);


        if (order.getOrderList().size() > 0)
            this.order.getOrderList().remove(orderPlaceHolder.getIndexToRemove());


        itemServiceForSpringModel.addAllItemsToModel(model);

        orderServiceForSpringModel.addOrderedItemsToModel(model, "orderedItems");

        orderServiceForSpringModel.addTotalPrice(model, "totalPrice");


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







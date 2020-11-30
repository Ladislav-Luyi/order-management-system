package com.customer.ordermanagementsystem.Controllers;

import com.customer.ordermanagementsystem.pojos.order.Order;
import com.customer.ordermanagementsystem.pojos.order.OrderDTO;
import com.customer.ordermanagementsystem.services.CompanyService;
import com.customer.ordermanagementsystem.services.DiscountService;
import com.customer.ordermanagementsystem.services.ItemService;
import com.customer.ordermanagementsystem.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@SessionAttributes({"orderInfo","order","orderedItems","totalPrice","discount"})
@RequestMapping("/basket")
public class AddItemsController {

    private final Order order;
    private final ItemService itemService;
    private final OrderService orderService;
    private final DiscountService discountService;
    private final CompanyService companyService;

    @Autowired
    public AddItemsController(Order order, ItemService itemService, OrderService orderService, DiscountService discountService, CompanyService companyService) {
        this.order = order;
        this.itemService = itemService;
        this.orderService = orderService;
        this.discountService = discountService;
        this.companyService = companyService;
    }

    @RequestMapping()
    public String showOrderForm(Model model){

        if ( ! companyService.isStoreOpen() ) {
            companyService.addItemToModel(model,"closedMessage");
            return "closed";
        }

        itemService.addAllItemsToModel(model);

        orderService.addOrderedItemsToModel(model, "orderedItems");

        orderService.refreshPrice();

        discountService.refreshDiscounts();

        discountService.addDiscountToModel(model, "discount");

        orderService.addTotalPrice(model, "totalPrice");

        model.addAttribute("orderDTO", new OrderDTO() );

        return "order";
    }


    @RequestMapping(params={"addElement"})
    public String addElement(OrderDTO orderDTO, Model model) {
        log.info("before order: " + order);

        order.getOrderList().add(orderDTO.getItem());

        System.out.println(order.getOrderList().toString());

        itemService.addAllItemsToModel(model);

        orderService.addOrderedItemsToModel(model, "orderedItems");

        orderService.refreshPrice();

        discountService.refreshDiscounts();

        discountService.addDiscountToModel(model, "discount");

        orderService.addTotalPrice(model, "totalPrice");



        log.info("Processing order: " + order);
        log.info("calling add element!");

        return "order";
    }


    @RequestMapping(params={"removeElement"})
    public String removeElement(OrderDTO orderDTO, Model model) {
        log.info("before removeElement: " + this.order);


        if (order.getOrderList().size() > 0)
            this.order.getOrderList().remove(orderDTO.getIndexToRemove());


        itemService.addAllItemsToModel(model);

        orderService.addOrderedItemsToModel(model, "orderedItems");

        orderService.refreshPrice();

        discountService.refreshDiscounts();

        discountService.addDiscountToModel(model, "discount");

        orderService.addTotalPrice(model, "totalPrice");



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







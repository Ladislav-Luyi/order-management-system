package com.customer.ordermanagementsystem.controllers;


import com.customer.ordermanagementsystem.pojos.order.OrderDTO;
import com.customer.ordermanagementsystem.services.CompanyService;
import com.customer.ordermanagementsystem.services.DiscountService;
import com.customer.ordermanagementsystem.services.ItemService;
import com.customer.ordermanagementsystem.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@SessionAttributes({"orderInfo","order","orderedItems","totalPrice","discount"})
@RequestMapping({"","/","/kosik"})
public class AddItemsController {

    private final ItemService itemService;
    private final OrderService orderService;
    private final DiscountService discountService;
    private final CompanyService companyService;

    @Autowired
    public AddItemsController(ItemService itemService, OrderService orderService, DiscountService discountService, CompanyService companyService) {
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

        addItemOrderDiscountToModel(model);

        model.addAttribute("orderDTO", new OrderDTO() );

        return "order";
    }



    @RequestMapping(params={"addElement"})
    public String addElement(OrderDTO orderDTO, Model model) {
        orderService.addItemToList(orderDTO.getItem());

        log.debug("addElement: " + orderService.getOrderInstance().getOrderList().toString());

        addItemOrderDiscountToModel(model);

        return "order";
    }


    @RequestMapping(params={"removeElement"})
    public String removeElement(OrderDTO orderDTO, Model model) {
        orderService.removeItemFromList(orderDTO.getIndexToRemove());

        addItemOrderDiscountToModel(model);

        return "order";
    }

    @PostMapping
    public String processOrder() {
        if (!orderService.isHigherThanMinimalValue())
            return "redirect:/kosik";

        log.info("Processing order: " + orderService.getOrderInstance());

        return "redirect:/objednavka/formular";
    }

    private void addItemOrderDiscountToModel(Model model) {
        itemService.addAllItemsToModel(model);

        orderService.addOrderedItemsToModel(model, "orderedItems");

        orderService.addMinimalOrderValueToModel(model, "minimalOrderValue");

        orderService.refreshPrice();

        discountService.refreshDiscounts();

        discountService.addDiscountToModel(model, "discount");

        orderService.addTotalPrice(model, "totalPrice");
    }

}







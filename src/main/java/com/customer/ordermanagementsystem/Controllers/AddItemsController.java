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
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@SessionAttributes({"orderInfo","order","orderedItems","totalPrice","discount"})
@RequestMapping("/basket")
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

        orderService.addItemToList(orderDTO.getItem());

        log.info("addElement: " + orderService.getOrderInstance().getOrderList().toString());

        itemService.addAllItemsToModel(model);

        orderService.addOrderedItemsToModel(model, "orderedItems");

        orderService.refreshPrice();

        discountService.refreshDiscounts();

        discountService.addDiscountToModel(model, "discount");

        orderService.addTotalPrice(model, "totalPrice");

        return "order";
    }


    @RequestMapping(params={"removeElement"})
    public String removeElement(OrderDTO orderDTO, Model model) {
        orderService.removeItemFromList(orderDTO.getIndexToRemove());

        itemService.addAllItemsToModel(model);

        orderService.addOrderedItemsToModel(model, "orderedItems");

        orderService.refreshPrice();

        discountService.refreshDiscounts();

        discountService.addDiscountToModel(model, "discount");

        orderService.addTotalPrice(model, "totalPrice");

        return "order";
    }

    @PostMapping
    public String processOrder() {

        log.info("Processing order: " + orderService.getOrderInstance());

        return "redirect:/order/orderInfo";
    }

}







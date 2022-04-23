package com.customer.ordermanagementsystem.controllers;

import com.customer.ordermanagementsystem.pojos.order.OrderDTO;
import com.customer.ordermanagementsystem.pojos.item.Type;
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
@SessionAttributes({"orderInfo","order", "orderDTO"})

@RequestMapping("/upravaPolozky")
public class EditItemController {


    private final ItemService itemService;
    private final OrderService orderService;
    private final DiscountService discountService;

    @Autowired
    public EditItemController(ItemService itemService, OrderService orderService, DiscountService discountService) {
        this.itemService = itemService;
        this.orderService = orderService;
        this.discountService = discountService;
    }

    @RequestMapping()
    public String editItem(Model model,  @RequestParam int index){
        addItemOrderDiscountToModel(model, index);

        model.addAttribute("orderDTO", new OrderDTO() );

        return "edit";

    }

    @RequestMapping(params={"addInnerElement"})
    public String addItem(Model model, OrderDTO orderDTO, @RequestParam int index){
        orderService.addItemToIndexInList(index, orderDTO.getItem());

        addItemOrderDiscountToModel(model, index);

        model.addAttribute("orderDTO", new OrderDTO() );

        return "edit";

    }


    @RequestMapping(params={"removeElement"})
    public String removeItem(OrderDTO orderDTO, Model model, @RequestParam int index) {
        orderService.removeIndexFromInnerList(index, orderDTO.getIndexToRemove());

        addItemOrderDiscountToModel(model, index);

        model.addAttribute("orderDTO", new OrderDTO() );

        return "edit";
    }


    @PostMapping()
    public String returnToBasket(){

        return "redirect:/kosik";
    }

    private void addItemOrderDiscountToModel(Model model, @RequestParam int index) {
        itemService.addSingleItemToModel(model, Type.DOPLNOK);

        orderService.addSingleOrderedItemToModel(model, index, "orderedItem");

        orderService.addOrderedItemsToModel(model, "orderedItems");

        orderService.refreshPrice();

        discountService.refreshDiscounts();

        discountService.addDiscountToModel(model, "discount");

        orderService.addTotalPrice(model, "totalPrice");
    }
}

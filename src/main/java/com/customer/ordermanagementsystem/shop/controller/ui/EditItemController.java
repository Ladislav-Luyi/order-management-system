package com.customer.ordermanagementsystem.shop.controller.ui;

import com.customer.ordermanagementsystem.shop.domain.item.Item;
import com.customer.ordermanagementsystem.shop.domain.item.Type;
import com.customer.ordermanagementsystem.shop.domain.order.OrderDTO;
import com.customer.ordermanagementsystem.shop.service.ItemService;
import com.customer.ordermanagementsystem.shop.service.ModelService;
import com.customer.ordermanagementsystem.shop.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Slf4j
@Controller
@RequestMapping("/upravaPolozky")
public class EditItemController {


    private final ItemService itemService;
    private final OrderService orderService;
    private final ModelService modelService;

    public EditItemController(ItemService itemService, OrderService orderService, ModelService modelService) {
        this.itemService = itemService;
        this.orderService = orderService;
        this.modelService = modelService;
    }

    @RequestMapping()
    public String editItem(Model model, @RequestParam int index) {
        addElements(model, index);
        model.addAttribute("orderDTO", new OrderDTO());
        return "edit";

    }

    @RequestMapping(params = {"addInnerElement"})
    public String addItem(Model model, OrderDTO orderDTO, @RequestParam int index) {
        orderService.addItemToIndexInList(index, orderDTO.getItem());
        addElements(model, index);
        model.addAttribute("orderDTO", new OrderDTO());
        return "edit";

    }


    @RequestMapping(params = {"removeElement"})
    public String removeItem(OrderDTO orderDTO, Model model, @RequestParam int index) {
        orderService.removeIndexFromInnerList(index, orderDTO.getIndexToRemove());
        addElements(model, index);
        model.addAttribute("orderDTO", new OrderDTO());
        return "edit";
    }


    @PostMapping()
    public String returnToBasket() {
        return "redirect:/kosik";
    }

    void addElements(Model model, @RequestParam int index) {
        addToModel(model, Type.DOPLNOK, itemService.getItemsOfType(Type.DOPLNOK));
        modelService.addToModel(model, "orderedItem", orderService.getOrders().get(index));
        modelService.addToModel(model, "orderedItems", orderService.getOrders());
        modelService.addToModel(model, "totalPrice", orderService.getTotalPrice());
    }

    private void addToModel(Model model, Type type, List<Item> items) {
        model.addAttribute(type.toString(), items);
    }
}

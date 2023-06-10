package com.customer.ordermanagementsystem.controllers.ui;

import com.customer.ordermanagementsystem.domain.item.Item;
import com.customer.ordermanagementsystem.domain.order.OrderDTO;
import com.customer.ordermanagementsystem.domain.item.Type;
import com.customer.ordermanagementsystem.services.DiscountService;
import com.customer.ordermanagementsystem.services.ItemService;
import com.customer.ordermanagementsystem.services.ModelService;
import com.customer.ordermanagementsystem.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Controller
@SessionAttributes({"customerInfo", "order", "orderDTO"})
@RequestMapping("/upravaPolozky")
public class EditItemController {


    private final ItemService itemService;
    private final OrderService orderService;
    private final DiscountService discountService;
    private final ModelService modelService;

    @Autowired
    public EditItemController(ItemService itemService, OrderService orderService, DiscountService discountService, ModelService modelService) {
        this.itemService = itemService;
        this.orderService = orderService;
        this.discountService = discountService;
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

    private void addElements(Model model, @RequestParam int index) {
        addToModel(model, Type.DOPLNOK, itemService.getItemsOfType(Type.DOPLNOK));
        modelService.addToModel(model, "orderedItem",  orderService.getOrders().get(index));
        modelService.addToModel(model, "orderedItems", orderService.getOrders());
        orderService.refreshPrice();
        discountService.refreshDiscounts();
        discountService.addDiscountToModel(model, "discount");
        modelService.addToModel(model, "totalPrice", orderService.getTotalPrice());
    }

    private void addToModel(Model model, Type type, List<Item> items) {
        model.addAttribute(type.toString(), items);
    }
}

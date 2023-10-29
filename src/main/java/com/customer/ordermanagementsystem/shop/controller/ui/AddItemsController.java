package com.customer.ordermanagementsystem.shop.controller.ui;


import com.customer.ordermanagementsystem.company.service.CompanyService;
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
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Slf4j
@Controller
@RequestMapping({"", "/", "/kosik"})
@SessionScope
public class AddItemsController {

    private final ItemService itemService;
    private final OrderService orderService;
    private final CompanyService companyService;
    private final ModelService modelService;

    public AddItemsController(ItemService itemService, OrderService orderService, CompanyService companyService, ModelService modelService) {
        this.itemService = itemService;
        this.orderService = orderService;
        this.companyService = companyService;
        this.modelService = modelService;
    }


    private static Predicate<Map.Entry<Type, List<Item>>> isKeyDefinedAsType() {
        return typeListEntry -> Type.isTypeDefined(typeListEntry.getKey().toString());
    }

    private Predicate<Map.Entry<Type, List<Item>>> isValueArrayNotEmpty() {
        return typeListEntry -> typeListEntry.getValue().size() != 0;
    }

    public void addAllItemsToModel(Model model, List<Item> items) {
        items.stream()
                .collect(Collectors.groupingBy(Item::getType))
                .entrySet().stream()
                .filter(isValueArrayNotEmpty())
                .filter(isKeyDefinedAsType())
                .forEach(typeListEntry -> modelService.addToModel(model, typeListEntry.getKey().toString(), typeListEntry.getValue()));
    }

    @RequestMapping()
    public String showOrderForm(Model model) {
//        if (!companyService.isStoreOpen()) {
//            addAllItemsToModel(model, itemService.getItems());
//            modelService.addToModel(model, "closedMessage", companyService.getOpenAndCloseStoreMessage());
//            return "closed";
//        }
        addElements(model);
        model.addAttribute("orderDTO", new OrderDTO());
        return "order";
    }


    @RequestMapping(params = {"addElement"})
    public String addElement(OrderDTO orderDTO, Model model) {
        orderService.addItemToList(orderDTO.getItem());
        addElements(model);
        return "order";
    }


    @RequestMapping(params = {"removeElement"})
    public String removeElement(OrderDTO orderDTO, Model model) {
        orderService.removeItemFromList(orderDTO.getIndexToRemove());
        addElements(model);
        return "order";
    }

    @PostMapping
    public String processOrder() {
        if (!orderService.isHigherThanMinimalValue())
            return "redirect:/kosik";
        return "redirect:/objednavka/formular";
    }

    private void addElements(Model model) {
        addAllItemsToModel(model, itemService.getItems());
        modelService.addToModel(model, "orderedItems", orderService.getOrders());
        modelService.addToModel(model, "minimalOrderValue", orderService.getMinimalOrderValue().toString());
        modelService.addToModel(model, "totalPrice", orderService.getTotalPrice());
    }

}







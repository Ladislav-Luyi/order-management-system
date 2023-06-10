package com.customer.ordermanagementsystem.controllers.ui;


import com.customer.ordermanagementsystem.domain.item.Item;
import com.customer.ordermanagementsystem.domain.item.Type;
import com.customer.ordermanagementsystem.domain.order.OrderDTO;
import com.customer.ordermanagementsystem.services.CompanyService;
import com.customer.ordermanagementsystem.services.DiscountService;
import com.customer.ordermanagementsystem.services.ItemService;
import com.customer.ordermanagementsystem.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Slf4j
@Controller
@SessionAttributes({"customerInfo", "order", "orderedItems", "totalPrice", "discount"})
@RequestMapping({"", "/", "/kosik"})
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
    private void addToModel(Model model, Type type, List<Item> items) {
        model.addAttribute(type.toString(), items);
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
                .forEach(typeListEntry -> addToModel(model, typeListEntry.getKey(), typeListEntry.getValue()));
    }

//    public void addSingleTypeItemsToModel(Model model, List<Item> items) {
//        items
//                .filter(e -> e.getType().equals(type))
//                .collect(Collectors.toList());
//        addToModel(model, type, items);
//    }

    @RequestMapping()
    public String showOrderForm(Model model) {
        if (!companyService.isStoreOpen()) {
            addAllItemsToModel(model, itemService.getItems());
            companyService.addItemToModel(model, "closedMessage");
            return "closed";
        }


        addElements(model);

        model.addAttribute("orderDTO", new OrderDTO());

        return "order";
    }


    @RequestMapping(params = {"addElement"})
    public String addElement(OrderDTO orderDTO, Model model) {
        orderService.addItemToList(orderDTO.getItem());

        log.debug("addElement: " + orderService.getOrderInstance().getOrderList().toString());

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

        log.info("Processing order: " + orderService.getOrderInstance());

        return "redirect:/objednavka/formular";
    }

    private void addElements(Model model) {
        addAllItemsToModel(model, itemService.getItems());

        orderService.addOrderedItemsToModel(model, "orderedItems");

        orderService.addMinimalOrderValueToModel(model, "minimalOrderValue");

        orderService.refreshPrice();

        discountService.refreshDiscounts();

        discountService.addDiscountToModel(model, "discount");

        orderService.addTotalPrice(model, "totalPrice");
    }

}







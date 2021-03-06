package com.customer.ordermanagementsystem.Controllers;

import com.customer.ordermanagementsystem.pojos.order.Order;
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
        itemService.addSingleItemToModel(model, Type.DOPLNOK);

        orderService.addSingleOrderedItemToModel(model, index, "orderedItem");

        orderService.addOrderedItemsToModel(model, "orderedItems");

        orderService.refreshPrice();

        discountService.refreshDiscounts();

        discountService.addDiscountToModel(model, "discount");

        orderService.addTotalPrice(model, "totalPrice");

        model.addAttribute("orderDTO", new OrderDTO() );

        return "edit";

    }

    @RequestMapping(params={"addInnerElement"})
    public String addItem(Model model, OrderDTO orderDTO, @RequestParam int index){
        orderService.addItemToIndexInList(index, orderDTO.getItem());

        itemService.addSingleItemToModel(model, Type.DOPLNOK);

        orderService.addOrderedItemsToModel(model, "orderedItems");

        model.addAttribute("orderDTO", new OrderDTO() );

        orderService.addSingleOrderedItemToModel(model, index, "orderedItem");

        orderService.refreshPrice();

        discountService.refreshDiscounts();

        discountService.addDiscountToModel(model, "discount");

        orderService.addTotalPrice(model, "totalPrice");

        return "edit";

    }


    @RequestMapping(params={"removeElement"})
    public String removeItem(OrderDTO orderDTO, Model model, @RequestParam int index) {
        orderService.removeIndexFromInnerList(index, orderDTO.getIndexToRemove());

        itemService.addSingleItemToModel(model, Type.DOPLNOK);

        orderService.addOrderedItemsToModel(model, "orderedItems");

        orderService.addSingleOrderedItemToModel(model, index, "orderedItem");

        orderService.addTotalPrice(model, "totalPrice");

        model.addAttribute("orderDTO", new OrderDTO() );

        return "edit";
    }


    @PostMapping()
    public String returnToBasket(){

        return "redirect:/kosik";
    }
}

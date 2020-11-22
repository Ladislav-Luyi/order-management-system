package com.customer.ordermanagementsystem.Controllers;

import com.customer.ordermanagementsystem.pojos.Order;
import com.customer.ordermanagementsystem.pojos.OrderDTO;
import com.customer.ordermanagementsystem.pojos.Type;
import com.customer.ordermanagementsystem.services.DiscountService;
import com.customer.ordermanagementsystem.services.ItemServiceForSpringModel;
import com.customer.ordermanagementsystem.services.OrderServiceForSpringModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@SessionAttributes({"orderInfo","order", "orderDTO"})

@RequestMapping("/editItem")
public class EditItemController {

    private final Order order;
    private final ItemServiceForSpringModel itemServiceForSpringModel;
    private final OrderServiceForSpringModel orderServiceForSpringModel;
    private final DiscountService discountService;

    @Autowired
    public EditItemController(Order order, ItemServiceForSpringModel itemServiceForSpringModel, OrderServiceForSpringModel orderServiceForSpringModel, DiscountService discountService) {
        this.order = order;
        this.itemServiceForSpringModel = itemServiceForSpringModel;
        this.orderServiceForSpringModel = orderServiceForSpringModel;
        this.discountService = discountService;
    }

    @RequestMapping()
    public String editItem(Model model,  @RequestParam int index){
        System.out.println("Edit item with index: " + index);

        System.out.println( order.getOrderList().get(index) );

        itemServiceForSpringModel.addSingleItemToModel(model, Type.DOPLNOK);

        orderServiceForSpringModel.addSingleOrderedItemToModel(model, index, "orderedItem");

        orderServiceForSpringModel.addOrderedItemsToModel(model, "orderedItems");

        orderServiceForSpringModel.refreshPrice();

        discountService.refreshDiscounts();

        discountService.addDiscountToModel(model, "discount");

        orderServiceForSpringModel.addTotalPrice(model, "totalPrice");

        model.addAttribute("orderDTO", new OrderDTO() );

        return "edit";

    }

    @RequestMapping(params={"addInnerElement"})
    public String addItem(Model model, OrderDTO orderDTO, @RequestParam int index){
        System.out.println(orderDTO);
        order.getOrderList().get(index).getItemList().add(orderDTO.getItem());

        itemServiceForSpringModel.addSingleItemToModel(model, Type.DOPLNOK);

        orderServiceForSpringModel.addOrderedItemsToModel(model, "orderedItems");

        model.addAttribute("orderDTO", new OrderDTO() );

        orderServiceForSpringModel.addSingleOrderedItemToModel(model, index, "orderedItem");

        orderServiceForSpringModel.refreshPrice();

        discountService.refreshDiscounts();

        discountService.addDiscountToModel(model, "discount");

        orderServiceForSpringModel.addTotalPrice(model, "totalPrice");

        return "edit";

    }


    @RequestMapping(params={"removeElement"})
    public String removeItem(OrderDTO orderDTO, Model model, @RequestParam int index) {

        if (order.getOrderList().get(index).getItemList().size() > 0)
            order.getOrderList().get(index).getItemList().remove(orderDTO.getIndexToRemove());

        itemServiceForSpringModel.addSingleItemToModel(model, Type.DOPLNOK);

        orderServiceForSpringModel.addOrderedItemsToModel(model, "orderedItems");

        orderServiceForSpringModel.addSingleOrderedItemToModel(model, index, "orderedItem");

        orderServiceForSpringModel.addTotalPrice(model, "totalPrice");

        model.addAttribute("orderDTO", new OrderDTO() );

        return "edit";
    }


    @PostMapping()
    public String returnToBasket(Model model, Order order){

        return "redirect:/basket";
    }
}

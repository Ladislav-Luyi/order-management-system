package com.customer.ordermanagementsystem.Controllers;

import com.customer.ordermanagementsystem.orders.Item;
import com.customer.ordermanagementsystem.orders.Order;
import com.customer.ordermanagementsystem.orders.OrderPlaceHolder;
import com.customer.ordermanagementsystem.orders.Type;
import com.customer.ordermanagementsystem.repository.ItemRepository;
import com.customer.ordermanagementsystem.services.ItemServiceForSpringModel;
import com.customer.ordermanagementsystem.services.OrderServiceForSpringModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Controller
@SessionAttributes({"orderInfo","order", "orderPlaceHolder"})

@RequestMapping("/editItem")
public class EditItemController {

    private final Order order;
    private final ItemServiceForSpringModel itemServiceForSpringModel;
    private final OrderServiceForSpringModel orderServiceForSpringModel;

    @Autowired
    public EditItemController(Order order, ItemServiceForSpringModel itemServiceForSpringModel, OrderServiceForSpringModel orderServiceForSpringModel) {
        this.order = order;
        this.itemServiceForSpringModel = itemServiceForSpringModel;
        this.orderServiceForSpringModel = orderServiceForSpringModel;
    }

    @RequestMapping()
    public String editItem(Model model,  @RequestParam int index){
        System.out.println("Edit item with index: " + index);

        //trying to access data
        System.out.println( order.getOrderList().get(index) );

        itemServiceForSpringModel.addSingleItemToModel(model, Type.DOPLNKY);

        model.addAttribute("orderedItem", order.getOrderList());

        model.addAttribute("orderPlaceHolder", new OrderPlaceHolder() );

        return "edit";

    }

    @RequestMapping(params={"addInnerElement"})
    public String addItem(Model model, OrderPlaceHolder orderPlaceHolder, @RequestParam int index){
        System.out.println(orderPlaceHolder);
        order.getOrderList().get(index).getItemList().add(orderPlaceHolder.getItem());

        itemServiceForSpringModel.addSingleItemToModel(model, Type.DOPLNKY);

        model.addAttribute("orderPlaceHolder", new OrderPlaceHolder() );

        model.addAttribute("orderedItem", order.getOrderList());

        return "edit";

    }


    @RequestMapping(params={"removeElement"})
    public String removeItem(OrderPlaceHolder orderPlaceHolder,  Model model, @RequestParam int index) {

        if (order.getOrderList().get(index).getItemList().size() > 0)
            order.getOrderList().get(index).getItemList().remove(orderPlaceHolder.getIndexToRemove());

        itemServiceForSpringModel.addSingleItemToModel(model, Type.DOPLNKY);

        model.addAttribute("orderPlaceHolder", new OrderPlaceHolder() );

        model.addAttribute("orderedItem", order.getOrderList());

        return "edit";
    }


    @PostMapping()
    public String returnToBasket(Model model, Order order){




        return "redirect:/basket";

    }
}

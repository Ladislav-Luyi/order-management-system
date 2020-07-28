package com.customer.ordermanagementsystem.Controllers;

import com.customer.ordermanagementsystem.orders.Item;
import com.customer.ordermanagementsystem.orders.Order;
import com.customer.ordermanagementsystem.orders.OrderPlaceHolder;
import com.customer.ordermanagementsystem.orders.Type;
import com.customer.ordermanagementsystem.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Controller
//@SessionAttributes({"orderInfo","order","innerOrder"})

@RequestMapping("/editItem")
public class EditItemController {

    @Autowired
    private OrderPlaceHolder orderPlaceHolder;

    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping()
    public String editItem(Model model, Order innerOrder, @RequestParam int index){
        System.out.println("Edit item with index: " + index);

        //trying to access data
        System.out.println( orderPlaceHolder.getOrderList().get(index) );



        List<Item> items = itemRepository.findAll();


        Type[] types = Type.values();
        for (Type type : types)
        {
            List<Item> tmpList = new ArrayList<>();
            for(Item tmpItem : items)
            {
                if (tmpItem.getType() == type) {
                    tmpList.add(tmpItem);
                }
            }

            if (tmpList.size() == 0)
                continue;

            log.info("continue for " + type.toString());
            model.addAttribute(type.toString(), tmpList);


        }


//        model.addAttribute("innerOrder", new Order() );


        return "edit";

    }

    @RequestMapping(params={"addInnerElement"})
    public String addItem(Model model, Order innerOrder, @RequestParam int index){
        System.out.println(innerOrder);
        orderPlaceHolder.getOrderList().get(index).getItemList().add(innerOrder.getOrderList().get(0));

        System.out.println(orderPlaceHolder);
        List<Item> items = itemRepository.findAll();


        Type[] types = Type.values();
        for (Type type : types)
        {
            List<Item> tmpList = new ArrayList<>();
            for(Item tmpItem : items)
            {
                if (tmpItem.getType() == type) {
                    tmpList.add(tmpItem);
                }
            }

            if (tmpList.size() == 0)
                continue;

            log.info("continue for " + type.toString());

            model.addAttribute(type.toString(), tmpList);


        }

//        model.addAttribute("innerOrder", new Order() );


        return "edit";

    }

    @PostMapping()
    public String returnToBasket(Model model, Order order){




        return "redirect:/basket";

    }
}

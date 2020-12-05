package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.pojos.item.Item;
import com.customer.ordermanagementsystem.pojos.order.Order;


import com.customer.ordermanagementsystem.pojos.item.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class DiscountServiceImpl implements DiscountService {

    private final OrderService orderService;
    private final Order order;

    private String message = "";
    private BigDecimal discount = new BigDecimal(0);


    @Autowired
    public DiscountServiceImpl(OrderService orderService) {
        this.orderService = orderService;
        this.order = orderService.getOrderInstance();
    }


    @Override
    public void addDiscountToModel(Model model, String nameOfAttributeForMapping) {

        String discountMessage = message;

        model.addAttribute(nameOfAttributeForMapping, discountMessage);

    }

    public void refreshDiscounts() {
        this.discount = new BigDecimal(0);
        this.message = "";
        processDiscountsForPizzas();

    }

    private void processDiscountsForPizzas() {

        List<Item> pizzaItems = new ArrayList<>();

        for(Item i : order.getOrderList() ){
            if (i.getType() == Type.PIZZA_BIG || i.getType() == Type.PIZZA_NORMAL)
                pizzaItems.add(i);
        }

        log.debug("Items considered for pizza discount: " + pizzaItems);

        List<Item> listItemsWithDiscount = new ArrayList<>();
        List<Item> listItemsWithOutDiscount = new ArrayList<>();

        if (isDiscountForPizzas(pizzaItems)){
            int discountForXItems = howManyTimesDiscountForPizzas(pizzaItems);
            Collections.sort( pizzaItems, Comparator.comparing(o -> o.getPrice()));

            int counter = 0;
            for(Item item : pizzaItems){

               if(counter < discountForXItems){
                   listItemsWithDiscount.add(item);
               }else{
                   listItemsWithOutDiscount.add(item);
               }

               counter++;
            }


        }

        this.discount = setTotalDiscountForPizzas(listItemsWithDiscount, discount);
        order.setTotalDiscount(discount);

        this.message = generateMessageDiscounts(listItemsWithDiscount, message);

        log.info("Pizza items with discount: " + listItemsWithDiscount.toString() );
        log.info("Pizza items without discount: " + listItemsWithOutDiscount.toString() );

    }

    private BigDecimal setTotalDiscountForPizzas(List<Item> listItemsWithDiscount, BigDecimal discount) {

        for (Item i : listItemsWithDiscount) {
            discount = discount.add(i.getPrice()) ;
        }

        return discount;
    }

    private String generateMessageDiscounts(List<Item> listItemsWithDiscount,String originalMessage) {

        StringBuilder sb = new StringBuilder(originalMessage);

        if (listItemsWithDiscount.size() > 0) {
            sb.append( order.getTotalPrice() );
            sb.append( " € - ");
            sb.append( order.getTotalDiscount() );
            sb.append( " € = ");
            sb.append( order.getTotalPriceDiscount());
            sb.append( " € ");
            sb.append( " / ");

            int loopCounter = 0;
            for (Item i : listItemsWithDiscount) {

                if (loopCounter > 0)
                    sb.append(", ");

                sb.append(i.getName());

                loopCounter++;

            }
            sb.append( " / ");
        }

        return addPrefixIfNotEmpty( sb.toString() );
    }


    private String addPrefixIfNotEmpty(String originalMessage) {
        if (originalMessage.length() > 0)
            return originalMessage = "Zľava: " + originalMessage;
        else
            return originalMessage;
    }

    private int howManyTimesDiscountForPizzas(List<Item> pizzaItems) {
        return pizzaItems.size() / 4;
    }

    private boolean isDiscountForPizzas(List<Item> pizzaItems) {
        return (pizzaItems.size() / 4) > 0;
    }
}

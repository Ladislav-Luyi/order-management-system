package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.pojos.item.Item;
import com.customer.ordermanagementsystem.pojos.order.Order;
import com.customer.ordermanagementsystem.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.ArrayList;


@Component
public class OrderServiceImpl implements OrderService {

    private final Order order;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(Order order, OrderRepository orderRepository) {
        this.order = order;
        this.orderRepository = orderRepository;
    }

    @Override
    public void addOrderedItemsToModel(Model model,String nameOfAttributeForMapping) {
        model.addAttribute(nameOfAttributeForMapping, order.getOrderList());
    }

    @Override
    public void addSingleOrderedItemToModel(Model model, int i,String nameOfAttributeForMapping) {
        model.addAttribute(nameOfAttributeForMapping, order.getOrderList().get(i));
    }

    @Override
    public void addTotalPrice(Model model, String nameOfAttributeForMapping) {
        BigDecimal totalPriceWithDiscount = order.getTotalPriceDiscount();

        model.addAttribute(nameOfAttributeForMapping, totalPriceWithDiscount);
    }

    @Override
    public void addItemToList(Item item) {
        order.getOrderList().add(item);
    }

    @Override
    public void refreshPrice(){
        BigDecimal price = new BigDecimal(0);

        for(Item item1 : order.getOrderList()) {
            price = price.add(item1.getPrice()) ;

            for (Item item2 : item1.getItemList()){
                price = price.add(item2.getPrice()) ;
            }
        }

        order.setTotalPrice(price);

    }

    @Override
    public Order getOrderInstance() {
        return order;
    }

    @Override
    public void saveOrder() {

        Order orderToSave = new Order();
        orderToSave.setOrderText( order.toString() );
        orderToSave.setOrderInfo( order.getOrderInfo() );
        orderToSave.setTotalPrice( order.getTotalPrice() );
        orderToSave.setTotalDiscount( order.getTotalDiscount() );
        orderToSave.setTotalPriceDiscount( order.getTotalPriceDiscount() );

        orderRepository.save(orderToSave);
    }
}

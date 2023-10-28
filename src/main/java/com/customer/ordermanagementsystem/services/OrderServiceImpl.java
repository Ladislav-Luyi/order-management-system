package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.domain.item.Item;
import com.customer.ordermanagementsystem.domain.order.CustomerInfo;
import com.customer.ordermanagementsystem.domain.order.Order;
import com.customer.ordermanagementsystem.domain.order.OrderDefaults;
import com.customer.ordermanagementsystem.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


@Slf4j
@Service
@SessionScope
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final Order order = new Order();
    private final OrderRepository orderRepository;
    private final DiscountService discountService;
    private final OrderDefaults orderDefaults;


    @Override
    public void addItemToList(Item item) {
        order.getShoppingCart().add(item);
    }

    @Override
    public void removeItemFromList(int index) {
        if (order.getShoppingCart().size() > 0)
            order.getShoppingCart().remove(index);
    }

    @Override
    public void addItemToIndexInList(int index, Item item) {
        if (order.getShoppingCart().size() > 0) {
            log.debug("Adding subItem " + item);
            order.getShoppingCart().get(index).getItemList().add(item);
        }
    }

    @Override
    public void removeIndexFromInnerList(int indexOuter, int indexInner) {
        if (order.getShoppingCart().size() > 0) {
            log.debug("Removing subItem " + indexInner);
            order.getShoppingCart().get(indexOuter).getItemList().remove(indexInner);
        }
    }

    @Override
    public void setCustomerInfo(CustomerInfo customerInfo) {
        order.setCustomerInfo(customerInfo);
    }


    private BigDecimal getPriceWithoutDiscount() {
        Optional<BigDecimal> priceWithoutDiscount = order.getShoppingCart().stream()
                // each item can have list of items, e.g. ingredients
                .flatMap(item -> Stream.concat(Stream.of(item),
                        item.getItemList().stream()))
                .map(Item::getPrice)
                .reduce(BigDecimal::add);
        return priceWithoutDiscount.orElse(new BigDecimal(0));
    }


    @Override
    @Transactional
    public void saveOrder() {
        order.setOrderText( new OrderDescriptionComposer().composeOrderText(order) );
        orderRepository.save(order);
    }

    @Override
    // TODO what is reason for this?
    public boolean isHigherThanMinimalValue() {
        return order.getTotalPriceDiscount().compareTo(orderDefaults.getMinimalValueForOrder()) != -1;
    }

    @Override
    public List<Item> getOrders() {
        return order.getShoppingCart();
    }

    @Override
    public BigDecimal getMinimalOrderValue() {
        return orderDefaults.getMinimalValueForOrder();
    }

    @Override
    public BigDecimal getTotalPrice() {
        refreshPrice();
        return order.getPriceDetails().getTotalPrice();
    }

    public void refreshPrice() {
        BigDecimal price = getPriceWithoutDiscount();
        BigDecimal totalDiscount = discountService.getDiscountValue(order.getShoppingCart());
        order.getPriceDetails().setTotalPriceDiscount(totalDiscount);
        order.getPriceDetails().setTotalPrice(price.subtract(totalDiscount));
    }
}

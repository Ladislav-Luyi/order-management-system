package com.customer.ordermanagementsystem.shop.domain.order.price;

import com.customer.ordermanagementsystem.shop.domain.item.Item;
import com.customer.ordermanagementsystem.shop.domain.order.price.discount.Discount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PriceDetails {

    Discount discount = new Discount();

    private BigDecimal totalPrice = new BigDecimal(0);
    private BigDecimal totalDiscount = new BigDecimal(0);
    private BigDecimal priceAfterDiscount = new BigDecimal(0);

    public void calculateTotalPrice(List<Item> shoppingCart) {
        totalPrice = getTotalPrice(shoppingCart);
        totalDiscount = discount.getDiscountValue(shoppingCart);
        priceAfterDiscount = totalPrice.subtract(totalDiscount);
    }

    private BigDecimal getTotalPrice(List<Item> shoppingCart) {
        Optional<BigDecimal> priceWithoutDiscount = shoppingCart.stream()
                // each item can have list of items, e.g. ingredients
                .flatMap(item -> Stream.concat(Stream.of(item),
                        item.getItemList().stream()))
                .map(Item::getPrice)
                .reduce(BigDecimal::add);
        return priceWithoutDiscount.orElse(new BigDecimal(0));
    }
}
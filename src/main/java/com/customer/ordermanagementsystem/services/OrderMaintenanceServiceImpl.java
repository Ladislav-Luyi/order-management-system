package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.pojos.order.Order;
import com.customer.ordermanagementsystem.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

@Service
@Slf4j
public class OrderMaintenanceServiceImpl implements OrderMaintenanceService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderMaintenanceServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    //second, minute, hour, day of month, month, day(s) of week
    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public void removeOlderThanWeekOrder() {

        List<Order> items = (List<Order>) orderRepository.findAll();

        Predicate<Order> isOrderOlderThan7days = o -> deltaDates(o.getPlacedAt(),new Date()) > 7;
        Predicate<Order> isNotIdWhichSupposedToBeSkipped = o -> ! o.getId().equals(11111l);

        items.stream()
                .filter(isOrderOlderThan7days)
                .filter(isNotIdWhichSupposedToBeSkipped)
                .forEach(orderRepository::delete);
    }

    private long deltaDates(Date date1, Date date2){

        long diff = ChronoUnit.DAYS.between(date1.toInstant(), date2.toInstant());

        return diff;
    }

}

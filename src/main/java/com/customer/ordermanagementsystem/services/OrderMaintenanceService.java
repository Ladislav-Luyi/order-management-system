package com.customer.ordermanagementsystem.services;

import org.springframework.stereotype.Service;

@Service
public interface OrderMaintenanceService {
    void removeOlderThanWeekOrder();
}

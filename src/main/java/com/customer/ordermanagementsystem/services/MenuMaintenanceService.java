package com.customer.ordermanagementsystem.services;

import org.springframework.stereotype.Service;

@Service
public interface MenuMaintenanceService {
    void removeOlderThanTodayMenu();
}

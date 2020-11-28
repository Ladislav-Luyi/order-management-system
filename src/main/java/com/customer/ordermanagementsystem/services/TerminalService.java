package com.customer.ordermanagementsystem.services;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public interface TerminalService {
    File refreshAndGetFile();

}

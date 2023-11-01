package com.customer.ordermanagementsystem.shop.controller.ui;

import com.customer.ordermanagementsystem.shop.service.ModelService;
import com.customer.ordermanagementsystem.shop.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerInfoController.class)
class CustomerInfoControllerIntTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrderService orderService;

    @MockBean
    ModelService modelService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void whenOrderWithMinimalValue_thenShowCustomerInfoForm() throws Exception {
        when(orderService.getMinimalOrderValue()).thenReturn(new BigDecimal("5"));
        when(orderService.isHigherThanMinimalValue()).thenReturn(true);
        this.mockMvc
                .perform(get("/objednavka/formular"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("customerInfo"))
                .andExpect(view().name("customerInfo"));
    }

    @Test
    void whenNoOrderWithMinimalValue_thenRedirectBack() throws Exception {
        when(orderService.getMinimalOrderValue()).thenReturn(new BigDecimal("5"));
        when(orderService.isHigherThanMinimalValue()).thenReturn(false);
        this.mockMvc
                .perform(get("/objednavka/formular"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/kosik"));
    }

    @Test
    void processCustomerInfoFormWithError_thenDoNotRedirect() throws Exception {
        this.mockMvc
                .perform(post("/objednavka/formular"))
                .andExpect(status().isOk())
                .andExpect(view().name("customerInfo"));
    }

    @Test
    void legalConditions() throws Exception {
        when(orderService.getMinimalOrderValue()).thenReturn(new BigDecimal("5"));
        when(orderService.isHigherThanMinimalValue()).thenReturn(true);
        this.mockMvc
                .perform(get("/objednavka/podmienky"))
                .andExpect(view().name("conditions"));
    }
}
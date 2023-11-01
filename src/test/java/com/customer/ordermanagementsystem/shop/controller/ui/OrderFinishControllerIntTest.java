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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderFinishController.class)
class OrderFinishControllerIntTest {

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
    void whenNotMinimalValueOrder_thenRedirectBack() throws Exception {
        when(orderService.isHigherThanMinimalValue()).thenReturn(false);
        this.mockMvc
                .perform(get("/objednavka/dokoncena"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/kosik"));
    }

    @Test
    void whenMinimalValueOrder_thenFinnish() throws Exception {
        when(orderService.isHigherThanMinimalValue()).thenReturn(true);
        this.mockMvc
                .perform(get("/objednavka/dokoncena"))
                .andExpect(status().isOk())
                .andExpect(view().name("ordered"));
    }
}
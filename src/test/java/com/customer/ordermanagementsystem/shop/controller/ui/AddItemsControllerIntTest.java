package com.customer.ordermanagementsystem.shop.controller.ui;

import com.customer.ordermanagementsystem.company.service.CompanyService;
import com.customer.ordermanagementsystem.shop.domain.item.Item;
import com.customer.ordermanagementsystem.shop.service.ItemService;
import com.customer.ordermanagementsystem.shop.service.ModelService;
import com.customer.ordermanagementsystem.shop.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AddItemsController.class)
class AddItemsControllerIntTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ItemService itemService;

    @MockBean
    OrderService orderService;

    @MockBean
    CompanyService companyService;

    @MockBean
    ModelService modelService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @ParameterizedTest
    @ValueSource(strings = {"", "/kosik", "/"})
    void showOrderForm() throws Exception {
        when(orderService.getMinimalOrderValue()).thenReturn(new BigDecimal("5"));
        this.mockMvc
                .perform(get("/kosik"))
                .andExpect(status().isOk())
                .andExpect(view().name("order"))
                .andExpect(model().attributeExists("orderDTO"));
    }

    @Test
    void addElement() throws Exception {
        when(orderService.getMinimalOrderValue()).thenReturn(new BigDecimal("5"));
        doNothing().when(orderService).addItemToList(new Item());
        this.mockMvc
                .perform(get("/kosik")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("addElement",""))
                .andExpect(status().isOk())
                .andExpect(view().name("order"));
    }

    @Test
    void removeElement() throws Exception {
        when(orderService.getMinimalOrderValue()).thenReturn(new BigDecimal("5"));
        doNothing().when(orderService).removeItemFromList(0);
        this.mockMvc
                .perform(get("/kosik")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("removeElement",""))
                .andExpect(status().isOk())
                .andExpect(view().name("order"));
    }

    @Test
    void whenProcessOrderWithNoMinimalOrder_thenRedirectToSamePage() throws Exception {
        this.mockMvc
                .perform(post("/kosik"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/kosik"));;
    }

    @Test
    void whenProcessOrderWithMinimalOrder_thenRedirectForward() throws Exception {
        when(orderService.isHigherThanMinimalValue()).thenReturn(true);
        this.mockMvc
                .perform(post("/kosik"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/objednavka/formular"));;
    }
}
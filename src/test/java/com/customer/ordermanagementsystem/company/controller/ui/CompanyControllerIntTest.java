package com.customer.ordermanagementsystem.company.controller.ui;

import com.customer.ordermanagementsystem.company.service.CompanyService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CompanyController.class)
class CompanyControllerIntTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    CompanyService companyService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getOpen() throws Exception {
        when(companyService.getOpenAndCloseStoreMessage()).thenReturn("open");
        this.mockMvc
                .perform(get("/podnik"))
                .andExpect(status().isOk())
                .andExpect(view().name("company"))
                .andExpect(model().attributeExists("status"))
                .andExpect(model().attributeExists("company"));
    }

    @Test
    void setOpen() throws Exception {
        this.mockMvc
                .perform(post("/podnik"))
                .andExpect(status().is3xxRedirection());
    }
}
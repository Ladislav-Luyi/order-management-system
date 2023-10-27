package com.customer.ordermanagementsystem.controllers.ui;


import com.customer.ordermanagementsystem.domain.order.CustomerInfo;
import com.customer.ordermanagementsystem.services.ModelService;
import com.customer.ordermanagementsystem.services.OrderService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
@RequestMapping("/objednavka")
public class CustomerInfoController {

    private final OrderService orderService;
    private final ModelService modelService;

    public CustomerInfoController(OrderService orderService, ModelService modelService) {
        this.orderService = orderService;
        this.modelService = modelService;
    }

    @GetMapping("/formular")
    public String showCustomerInfoForm(Model model) {
        if (!orderService.isHigherThanMinimalValue())
            return "redirect:/kosik";

        model.addAttribute("customerInfo", new CustomerInfo());

        addOrderToModel(model);

        return "customerInfo";
    }

    @PostMapping("/formular")
    public String processCustomerInfoForm(Model model, @Valid CustomerInfo customerInfo, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.debug("BINDING RESULT ERROR");
            addOrderToModel(model);
            return "customerInfo";
        }

        log.info("Processing order processCustomerInfoForm: " + orderService.getOrderInstance());

        log.info("Processing customerInfo in customerInfo: " + customerInfo);

        orderService.setCustomerInfo(customerInfo);

        return "redirect:/objednavka/dokoncena";
    }

    @GetMapping("/podmienky")
    public String legalConditions() {

        return "conditions";
    }

    private void addOrderToModel(Model model) {
        modelService.addToModel(model, "orderedItems", orderService.getOrders());
        orderService.refreshPrice();
        modelService.addToModel(model, "totalPrice", orderService.getTotalPrice());
    }
}

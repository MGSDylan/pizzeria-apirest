package com.platzi.pizza.web.controller;

import com.platzi.pizza.persistence.entity.CustomerEntity;
import com.platzi.pizza.persistence.entity.OrdenEntity;
import com.platzi.pizza.servicios.CustomerService;
import com.platzi.pizza.servicios.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    private final OrderService orderService;

    @Autowired
    public CustomerController(CustomerService customerService, OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @GetMapping("{phone}")
    public ResponseEntity<CustomerEntity> findByphone(@PathVariable String phone){
        return ResponseEntity.ok(customerService.findByPhone(phone));
    }

    @GetMapping("customer/{id}")
    public ResponseEntity<List<OrdenEntity>> getOrdersById(@PathVariable String id){
        return new  ResponseEntity<>(orderService.getOrdersById(id), HttpStatus.FOUND);
    }

}

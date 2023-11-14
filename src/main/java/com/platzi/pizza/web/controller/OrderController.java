package com.platzi.pizza.web.controller;

import com.platzi.pizza.persistence.entity.OrdenEntity;
import com.platzi.pizza.persistence.projection.OrderSummary;
import com.platzi.pizza.servicios.OrderService;
import com.platzi.pizza.servicios.dto.RandomOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrdenEntity>> getAll(){
        return new ResponseEntity<>(orderService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/date")
    public ResponseEntity<List<OrdenEntity>> getByDateAfter(){
        return new ResponseEntity<>(orderService.getByDateToday(),HttpStatus.OK);
    }

    @GetMapping("/outside")
    public ResponseEntity<List<OrdenEntity>> getOutsideOrders(){
        return ResponseEntity.ok(orderService.getOutsideOrders());
    }

    @GetMapping("customer/{id}")
    public ResponseEntity<List<OrdenEntity>> getOrdersById(@PathVariable String id){
        return new  ResponseEntity<>(orderService.getOrdersById(id),HttpStatus.FOUND);
    }

    @GetMapping("/summary/{idOrder}")
    public ResponseEntity<OrderSummary> getDetalleOrder(@PathVariable int idOrder){
        return ResponseEntity.ok(orderService.getSummary(idOrder));
    }

    @PostMapping("/random")
    public ResponseEntity<Boolean> saveProcedure(@RequestBody RandomOrderDto dto){
        return new ResponseEntity<>(orderService.saveProcedure(dto),HttpStatus.OK);
    }

}

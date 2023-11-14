package com.platzi.pizza.servicios;

import com.platzi.pizza.persistence.entity.OrdenEntity;
import com.platzi.pizza.persistence.projection.OrderSummary;
import com.platzi.pizza.persistence.repository.OrderRepository;
import com.platzi.pizza.servicios.dto.RandomOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private static final  String CARRYOUT="D";
    private static final  String DELIVERY="C";
    private static final  String ON_SITE="S";

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrdenEntity> getAll(){
        List<OrdenEntity> orders=orderRepository.findAll();
        orders.forEach(ordenEntity -> System.out.println(ordenEntity.getCustomer().getName()));
        return orders;
    }

    public List<OrdenEntity> getByDateToday(){
        LocalDateTime local= LocalDate.now().atTime(0,0);
        return orderRepository.findByDateAfter(local);
    }

    public List<OrdenEntity> getOutsideOrders(){
        List<String> methos= Arrays.asList(DELIVERY,CARRYOUT);
        return orderRepository.findAllByMethodIn(methos);
    }

    @Secured("ROLE_ADMIN")
    public List<OrdenEntity> getOrdersById(String idCustomer){
        return orderRepository.getOrders(idCustomer);
    }

    public OrderSummary getSummary(int orderId){
        return orderRepository.findSummary(orderId);
    }

    @Transactional
    public boolean saveProcedure(RandomOrderDto dto){
        return orderRepository.saveRandomOrder(
                dto.getIdCustomer(),
                dto.getMethod());
    }

}

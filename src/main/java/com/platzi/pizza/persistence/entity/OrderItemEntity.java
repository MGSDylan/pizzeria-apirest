package com.platzi.pizza.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_item")
@IdClass(OrderItemId.class)
@Getter
@Setter
@NoArgsConstructor
public class OrderItemEntity {

    @Id
    @Column(nullable = false)
    private Integer idOrder;

    @Id
    @Column(nullable = false)
    private Integer idItem;

    @Column(nullable = false)
    private Integer idPizza;

    @Column(nullable = false,columnDefinition = "DECIMAL(2,1)")
    private Double quantity;

    @Column(nullable = false,columnDefinition = "DECIMAL(5,2)")
    private Double price;

    @OneToOne
    @JoinColumn(name = "idPizza",referencedColumnName = "id_pizza",insertable = false,updatable = false)
    private PizzaEntity pizza;

    @ManyToOne
    @JoinColumn(name = "idOrder",referencedColumnName = "id_order",insertable = false,updatable = false)
    @JsonIgnore
    private OrdenEntity order;


}
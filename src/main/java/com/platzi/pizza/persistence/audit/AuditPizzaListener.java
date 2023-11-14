package com.platzi.pizza.persistence.audit;

import com.platzi.pizza.persistence.entity.PizzaEntity;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreRemove;
import org.springframework.util.SerializationUtils;

public class AuditPizzaListener {

    private PizzaEntity currentValue;

    @PostLoad
    public void postLoad(PizzaEntity pizza){
        System.out.println("POST LOAD");
        currentValue= SerializationUtils.clone(pizza);
    }

    @PostPersist
    @PostUpdate
    public void onPostPersist(PizzaEntity pizza){
        System.out.println("POST PERSISTE OR UPDATE");
        System.out.println("HOLD VALUE "+currentValue);
        System.out.println("new VALUE "+pizza.toString());
    }


    @PreRemove
    public void onPreDelete(PizzaEntity pizza){
        System.out.println(pizza.toString());
    }



}

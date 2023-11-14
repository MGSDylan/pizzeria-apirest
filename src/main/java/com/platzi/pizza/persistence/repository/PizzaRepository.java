package com.platzi.pizza.persistence.repository;

import com.platzi.pizza.persistence.entity.PizzaEntity;
import com.platzi.pizza.servicios.dto.UpdatePizzaPriceDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PizzaRepository extends ListCrudRepository<PizzaEntity,Integer> {

    PizzaEntity findFirstByAvailableIsFalse();

    List<PizzaEntity> findAllByAvailableOrderByPrice(boolean bolean);

    Optional<PizzaEntity> findFirstByAvailableTrueAndNameIgnoreCase(String name);

    List<PizzaEntity> findAllByAvailableTrueAndDescriptionContainingIgnoreCase(String descrip);

    List<PizzaEntity> findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(String descrip);

    int countByVeganIsTrue();

    List<PizzaEntity> findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(double price);

    @Query(value = "UPDATE pizza "+
            "SET price =:newPrice "+
            "WHERE id_pizza =:idPizza"
            ,nativeQuery = true)
    @Modifying
    void updatePrice(@Param("idPizza")int idPizza,@Param("newPrice") double newPrice);

    @Query(value = "UPDATE pizza "+
            "SET price = :#{#newPizzaPrice.newPrice} "+
            "WHERE id_pizza = :#{#newPizzaPrice.pizzaId}"
            ,nativeQuery = true)
    @Modifying
    void updatePrice2(@Param("newPizzaPrice")UpdatePizzaPriceDto newPizzaPrice);


}

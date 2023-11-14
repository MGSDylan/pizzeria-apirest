package com.platzi.pizza.servicios;

import com.platzi.pizza.persistence.entity.PizzaEntity;
import com.platzi.pizza.persistence.repository.PizzaPagSortRepository;
import com.platzi.pizza.persistence.repository.PizzaRepository;
import com.platzi.pizza.servicios.dto.UpdatePizzaPriceDto;
import com.platzi.pizza.servicios.exception.EmailApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PizzaService {

    private final PizzaRepository pizzaRepository;

    private final PizzaPagSortRepository pizzaPagSortRepository;

    @Autowired
    public PizzaService(PizzaRepository pizzaRepository, PizzaPagSortRepository pizzaPagSortRepository) {
        this.pizzaRepository = pizzaRepository;
        this.pizzaPagSortRepository = pizzaPagSortRepository;
    }

    public Page<PizzaEntity> getAll(int page, int elements){
        Pageable pageRequest= PageRequest.of(page,elements);
        return pizzaPagSortRepository.findAll(pageRequest);
    }

    public Page<PizzaEntity> getAllNotAvailable(int page,int element,String sortBy,String sortDireccion){
        Sort sort=Sort.by(Sort.Direction.fromString(sortDireccion),sortBy);
        Pageable pageRequest=PageRequest.of(page,element, sort);
        return pizzaPagSortRepository.findByAvailableTrue(pageRequest);
    }

    public PizzaEntity getPizza(int pizzaId){
        return pizzaRepository.findById(pizzaId).orElse(null);
    }

    public PizzaEntity save(PizzaEntity pizza){
        return pizzaRepository.save(pizza);
    }

    public boolean exists(int idPizza){
        return pizzaRepository.existsById(idPizza);
    }

    public void delete(Integer idPizza){
        pizzaRepository.deleteById(idPizza);
    }

    public List<PizzaEntity> getAvailable(boolean bolean){
        System.out.println(pizzaRepository.countByVeganIsTrue());
        return pizzaRepository.findAllByAvailableOrderByPrice(bolean);
    }

    public PizzaEntity getByName(String name){
        return pizzaRepository.findFirstByAvailableTrueAndNameIgnoreCase(name)
                .orElseThrow(()->new RuntimeException("la pizza no existe"));
    }

    public List<PizzaEntity> getDescription(String descrip){
        return pizzaRepository.findAllByAvailableTrueAndDescriptionContainingIgnoreCase(descrip);
    }

    public List<PizzaEntity> getDescriptionWithNot(String descrip){
        return pizzaRepository.findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(descrip);
    }

    public List<PizzaEntity> getCheapper(double price){
        return pizzaRepository.findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(price);
    }

    @Transactional
    public void updatePrice(int idPizza,double newPrice){
        pizzaRepository.updatePrice(idPizza,newPrice);
    }

    @Transactional(noRollbackFor = EmailApiException.class)
    public void updatePrice2(UpdatePizzaPriceDto dto){
         pizzaRepository.updatePrice2(dto);
         sendEmail();
    }


    private  void sendEmail(){
        throw new EmailApiException();
    }


}
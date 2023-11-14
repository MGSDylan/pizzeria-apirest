package com.platzi.pizza.web.controller;

import com.platzi.pizza.persistence.entity.PizzaEntity;
import com.platzi.pizza.servicios.PizzaService;
import com.platzi.pizza.servicios.dto.UpdatePizzaPriceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pizzas")
public class PizzaController {

    private final PizzaService pizzaService;

    @Autowired
    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping
    public ResponseEntity<Page<PizzaEntity>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int element){
        return new ResponseEntity<>(pizzaService.getAll(page, element), HttpStatus.OK);
    }

    @GetMapping("/available")
    public ResponseEntity<Page<PizzaEntity>> getAlNotAvailable(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int element,
            @RequestParam(defaultValue = "price") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDireccion
    ){
        return new ResponseEntity<>(pizzaService.getAllNotAvailable(page,element,sortBy,sortDireccion), HttpStatus.OK);
    }

    @GetMapping("/{idPizza}")
    public ResponseEntity<PizzaEntity> getPizza(@PathVariable int idPizza){
        PizzaEntity pizza = pizzaService.getPizza(idPizza);
        HttpStatus status = (pizza != null) ? HttpStatus.FOUND : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(pizza, status);
    }

    @PostMapping
    public ResponseEntity<PizzaEntity> save(@RequestBody PizzaEntity pizza){
        if (pizza.getIdPizza()==null || !pizzaService.exists(pizza.getIdPizza())){
            return new ResponseEntity<>(pizzaService.save(pizza),HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().build();

    }

    @PutMapping
    public ResponseEntity<PizzaEntity> update(@RequestBody PizzaEntity pizza){
        if (pizza.getIdPizza()!=null && pizzaService.exists(pizza.getIdPizza())){
            return new ResponseEntity<>(pizzaService.save(pizza),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/{idPizza}")
    public ResponseEntity delete(@PathVariable Integer idPizza){
        if (pizzaService.exists(idPizza)){
            pizzaService.delete(idPizza);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("available/{bolean}")
    public ResponseEntity<List<PizzaEntity>> getAvailable(@PathVariable boolean bolean){
        return new ResponseEntity<>(pizzaService.getAvailable(bolean),HttpStatus.OK);
    }

    @GetMapping("byname/{name}")
    public ResponseEntity<PizzaEntity> findByName(@PathVariable String name){
        return new ResponseEntity<>(pizzaService.getByName(name),HttpStatus.FOUND);
    }

    @GetMapping("/descrip/{descrip}")
    public ResponseEntity<List<PizzaEntity>> getDescription(@PathVariable String descrip){
        return new ResponseEntity<>(pizzaService.getDescription(descrip),HttpStatus.OK);
    }

    @GetMapping("/with-not/{descrip}")
    public ResponseEntity<List<PizzaEntity>> getDescriptionWithNot(@PathVariable String descrip){
        return new ResponseEntity<>(pizzaService.getDescriptionWithNot(descrip),HttpStatus.OK);
    }


    @GetMapping("/cheapper/{price}")
    public ResponseEntity<List<PizzaEntity>> getCheapper(@PathVariable double price){
        return ResponseEntity.ok(pizzaService.getCheapper(price));
    }

    @PutMapping("/update/{idPizza}/{newPrice}")
    public ResponseEntity<Void> updatePrice(@PathVariable int idPizza,@PathVariable double newPrice){
        pizzaService.updatePrice(idPizza,newPrice);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update2")
    public ResponseEntity<Void> updatePrice2(@RequestBody UpdatePizzaPriceDto dto){
        if (pizzaService.exists(dto.getPizzaId())){
            pizzaService.updatePrice2(dto);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}

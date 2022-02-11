package pl.wsiz.foodservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wsiz.foodservice.dto.DishDto;
import pl.wsiz.foodservice.model.Dish;
import pl.wsiz.foodservice.service.DishService;

import java.util.List;

import static pl.wsiz.foodservice.dto.Converter.dishListToDishDtoList;

@RestController
@RequestMapping(value = "/api")
public class DishController {
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/dishes")
    public ResponseEntity<List<DishDto>> getAllDishes(){
        List<Dish> dishDetails = dishService.findAll();
        return new ResponseEntity<>(dishListToDishDtoList(dishDetails), HttpStatus.OK);
    }
}

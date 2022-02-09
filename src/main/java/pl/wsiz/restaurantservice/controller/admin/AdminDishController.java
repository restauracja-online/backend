package pl.wsiz.foodservice.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsiz.foodservice.controller.model.DishRequest;
import pl.wsiz.foodservice.dto.DishDto;
import pl.wsiz.foodservice.model.Dish;
import pl.wsiz.foodservice.service.DishService;

import javax.validation.Valid;

import java.util.List;

import static pl.wsiz.foodservice.dto.Converter.*;

@RestController
@RequestMapping(value = "/api/admin")
public class AdminDishController {

    private final DishService dishService;

    @Autowired
    AdminDishController(final DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/dishes")
    public ResponseEntity<List<DishDto>> getDishes() {
        List<Dish> dishes = this.dishService.getDishes();
        return new ResponseEntity<>(dishListToDishDtoList(dishes), HttpStatus.OK);
    }

    @GetMapping("/dish")
    public ResponseEntity<DishDto> getDish(@RequestParam Long dishId) {
        Dish dish = this.dishService.getDishById(dishId);
        return new ResponseEntity<DishDto>(dishToDishDto(dish), HttpStatus.OK);
    }

    @PostMapping("/dish")
    public ResponseEntity<DishDto> saveDish(@RequestBody @Valid DishRequest dishRequest) {
        Dish dish = this.dishService.createDish(dishRequestToDishModel(dishRequest));
        return new ResponseEntity<DishDto>(dishToDishDto(dish),HttpStatus.OK);
    }

    @PutMapping("/dish")
    public ResponseEntity<DishDto> editDish(@RequestBody @Valid DishRequest dishRequest) {
        Dish dish = this.dishService.editDish(dishRequestToDishModel(dishRequest));
        return new ResponseEntity<DishDto>(dishToDishDto(dish),HttpStatus.OK);
    }

    @DeleteMapping("/dish")
    public ResponseEntity<?> removeDish(@RequestParam Long dishId) {
        this.dishService.deleteDish(dishId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

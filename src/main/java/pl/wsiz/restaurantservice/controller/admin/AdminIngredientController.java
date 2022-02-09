package pl.wsiz.foodservice.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsiz.foodservice.controller.model.IngredientRequest;
import pl.wsiz.foodservice.dto.Converter;
import pl.wsiz.foodservice.dto.IngredientDto;
import pl.wsiz.foodservice.model.Ingredient;
import pl.wsiz.foodservice.service.IngredientService;

import javax.validation.Valid;

import java.util.List;

import static pl.wsiz.foodservice.dto.Converter.ingredientListToIngredientDtoList;
import static pl.wsiz.foodservice.dto.Converter.ingredientRequestToIngredientModel;

@RestController
@RequestMapping(value = "/api/admin")
public class AdminIngredientController {

    private final IngredientService ingredientService;

    @Autowired
    public AdminIngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/ingredients")
    public ResponseEntity<List<IngredientDto>> getIngredients() {
        List<Ingredient> ingredients = this.ingredientService.getIngredient();
        return new ResponseEntity<>(ingredientListToIngredientDtoList(ingredients), HttpStatus.OK);
    }

    @GetMapping("/ingredient")
    public ResponseEntity<IngredientDto> getIngredients(@RequestParam Long ingredientId) {
        Ingredient ingredient = this.ingredientService.getIngredientById(ingredientId);
        return new ResponseEntity<>(Converter.ingredientToIngredientDto(ingredient), HttpStatus.OK);
    }

    @PostMapping("/ingredients")
    public ResponseEntity<IngredientDto> saveIngredients(@RequestBody @Valid IngredientRequest ingredientRequest) {
        Ingredient ingredient = this.ingredientService.createIngredient(ingredientRequestToIngredientModel(ingredientRequest));
        return new ResponseEntity<>(Converter.ingredientToIngredientDto(ingredient), HttpStatus.OK);
    }

    @PutMapping("/ingredients")
    public ResponseEntity<IngredientDto> editIngredients(@RequestBody @Valid IngredientRequest ingredientRequest) {
        Ingredient ingredient = this.ingredientService.editIngredient(ingredientRequestToIngredientModel(ingredientRequest));
        return new ResponseEntity<>(Converter.ingredientToIngredientDto(ingredient), HttpStatus.OK);
    }

    @DeleteMapping("/ingredient")
    public ResponseEntity<?> removeIngredient(@RequestParam Long ingredientId) {
        this.ingredientService.deleteIngredient(ingredientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

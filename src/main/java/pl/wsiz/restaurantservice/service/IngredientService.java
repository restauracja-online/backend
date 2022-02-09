package pl.wsiz.foodservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wsiz.foodservice.exception.IngredientException;
import pl.wsiz.foodservice.model.Ingredient;
import pl.wsiz.foodservice.repository.IngredientRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Ingredient createIngredient(final Ingredient ingredient) {
        validateIfIngredientExist(ingredient.getName());

        return this.ingredientRepository.save(ingredient);
    }

    public Ingredient editIngredient(final Ingredient ingredient) {
        Long ingredientId = Optional.ofNullable(ingredient.getId()).orElse(null);
        String ingredientName = Optional.ofNullable(ingredient.getName()).orElse("");
        double ingredientPrice = Optional.ofNullable(ingredient.getPrice()).orElse(0.00);

        if (ingredientName.isEmpty() || ingredientPrice == 0.00 || ingredientId == null) {
            throw new IngredientException("Ingredient payload error");
        }

        Optional<Ingredient> ingredientByName = this.ingredientRepository.findById(ingredientId);

        if (!ingredientByName.isPresent()) {
            throw new IngredientException(String.format("Ingredient with name %s don't exist", ingredientByName));
        }

        Ingredient ingredientToSave = ingredientByName.get();

        ingredientToSave.setName(ingredientName);
        ingredientToSave.setPrice(ingredientPrice);

        return this.ingredientRepository.save(ingredientToSave);
    }

    public List<Ingredient> getIngredient() {
        return this.ingredientRepository.findAll();
    }

    public Ingredient getIngredientById(final Long ingredientId) {
        Optional<Ingredient> ingredient = this.ingredientRepository.findById(ingredientId);

        if (ingredient.isPresent()) {
            return ingredient.get();
        }

        throw new IngredientException(String.format("Ingredient with id %s don't exist", ingredientId));
    }

    public void deleteIngredient(final Long ingredientId) {
        if (ingredientId == null) {
            throw new IngredientException(String.format("Can't remove ingredient with %s id", ingredientId));
        }

        Optional<Ingredient> ingredient = this.ingredientRepository.findById(ingredientId);

        this.ingredientRepository.delete(ingredient.get());
    }

    private void validateIfIngredientExist(final String name) {
        Optional<Ingredient> ingredient = this.ingredientRepository.findByName(name);

        if (ingredient.isPresent()) {
            throw new IngredientException(String.format("Ingredient with following name %s exist", name));
        }
    }

}

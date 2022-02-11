package pl.wsiz.foodservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wsiz.foodservice.model.Ingredient;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Optional<Ingredient> findByName(String name);
}

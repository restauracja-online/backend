package pl.wsiz.foodservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wsiz.foodservice.model.Dish;

import java.util.Collection;
import java.util.Optional;

public interface DishRepository extends JpaRepository<Dish, Long> {

    Optional<Dish> findByName(String name);

    Iterable<Dish> findByIdIn(Collection<Long> ids);
}

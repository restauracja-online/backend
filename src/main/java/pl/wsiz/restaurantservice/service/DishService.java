package pl.wsiz.foodservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wsiz.foodservice.exception.DishException;
import pl.wsiz.foodservice.model.Dish;
import pl.wsiz.foodservice.repository.DishRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DishService {
    private final DishRepository dishRepository;

    @Autowired
    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public List<Dish> findAll(){
        return dishRepository.findAll();
    }

    public List<Dish> getDishes() {
        return this.dishRepository.findAll();
    }

    public Dish createDish(Dish dish) {
        validateIfDishtExist(dish.getName());

        return this.dishRepository.save(dish);
    }

    public void deleteDish(final Long dishId) {
        Optional<Dish> dish = this.dishRepository.findById(dishId);
        this.dishRepository.delete(dish.get());
    }

    public Dish getDishById(final Long dishId) {
        Optional<Dish> dish = this.dishRepository.findById(dishId);

        if (dish.isPresent()) {
            return dish.get();
        }

        throw new DishException("Can't find dish");
    }

    public Dish editDish(final Dish dish) {
        Long dishId = Optional.ofNullable(dish.getId()).orElse(null);
        String dishName = Optional.ofNullable(dish.getName()).orElse("");
        String dishDescription = Optional.ofNullable(dish.getDescription()).orElse("");
        double dishPrice = Optional.ofNullable(dish.getPrice()).orElse(0.00);

        if (dishId == null || dishName.isEmpty() || dishDescription.isEmpty() || dishPrice == 0.00) {
            throw new DishException("Cant save dish");
        }

        Optional<Dish> dishById = this.dishRepository.findById(dishId);

        if (!dishById.isPresent()) {
            throw new DishException("Can't find dish");
        }

        Dish dishToSave = dishById.get();

        dishToSave.setName(dishName);
        dishToSave.setDescription(dishDescription);
        dishToSave.setPrice(dishPrice);
        dishToSave.setImgURL(dish.getImgURL());
        dishToSave.setIngredients(dish.getIngredients());

        return this.dishRepository.save(dishToSave);
    }

    private void validateIfDishtExist(final String name) {
        Optional<Dish> dish = this.dishRepository.findByName(name);

        if (dish.isPresent()) {
            throw new DishException(String.format("Dish with name %s exist", name));
        }
    }

}

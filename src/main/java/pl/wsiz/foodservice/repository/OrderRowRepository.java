package pl.wsiz.foodservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wsiz.foodservice.model.Dish;
import pl.wsiz.foodservice.model.OrderRow;

import java.util.Optional;

public interface OrderRowRepository extends JpaRepository<OrderRow, Long> {

    Optional<OrderRow> findByCartId(Long cartId);
    Optional<OrderRow> findByDishId(Long dishId);

}

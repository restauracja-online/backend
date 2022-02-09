package pl.wsiz.foodservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wsiz.foodservice.model.Cart;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
}

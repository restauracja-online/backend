package pl.wsiz.foodservice.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "order_row")
public class OrderRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderRow orderRow = (OrderRow) o;
        return quantity == orderRow.quantity && Objects.equals(id, orderRow.id) && Objects.equals(cart, orderRow.cart) && Objects.equals(dish, orderRow.dish);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, cart, dish);
    }
}

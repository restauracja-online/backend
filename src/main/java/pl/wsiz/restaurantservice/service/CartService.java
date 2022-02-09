package pl.wsiz.foodservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wsiz.foodservice.controller.model.OrderRowRequest;
import pl.wsiz.foodservice.exception.CartException;
import pl.wsiz.foodservice.exception.DishException;
import pl.wsiz.foodservice.exception.UserException;
import pl.wsiz.foodservice.model.Cart;
import pl.wsiz.foodservice.model.Dish;
import pl.wsiz.foodservice.model.OrderRow;
import pl.wsiz.foodservice.model.User;
import pl.wsiz.foodservice.repository.CartRepository;
import pl.wsiz.foodservice.repository.DishRepository;
import pl.wsiz.foodservice.repository.OrderRowRepository;
import pl.wsiz.foodservice.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private CartRepository cartRepository;

    private UserRepository userRepository;

    private DishRepository dishRepository;

    private OrderRowRepository orderRowRepository;

    @Autowired
    CartService(CartRepository cartRepository,
                UserRepository userRepository,
                DishRepository dishRepository,
                OrderRowRepository orderRowRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.dishRepository = dishRepository;
        this.orderRowRepository = orderRowRepository;
    }

    public Cart getCart(final String userName) {
        Optional<User> userOptional = this.userRepository.findByEmail(userName);

        if (!userOptional.isPresent()) {
            throw new UserException("Can't get user");
        }

        User user = userOptional.get();

        final Cart cart = Optional.ofNullable(user.getCart()).orElse(new Cart());

        return cart;
    }

    public Cart saveCart(final String userName, final OrderRowRequest cartRequest) {

        if (cartRequest == null) {
            throw new CartException("Can't save cart");
        }

        Optional<User> userGet = this.userRepository.findByEmail(userName);

        if (!userGet.isPresent()) {
            throw new UserException("Can't find user");
        }

        User user = userGet.get();

        Cart cartUser = Optional.ofNullable(user.getCart()).orElse(null);

        if (cartUser == null) {
            Cart newCart = new Cart();
            List<OrderRow> orderRows = fillByOrderRowNew(cartRequest, newCart);
            newCart.setOrderRows(orderRows);
            user.setCart(newCart);
            newCart.setUser(user);

            return this.cartRepository.save(newCart);
        }

        List<OrderRow> orderRows = fillListByOrderRow(cartRequest, cartUser);
        cartUser.setOrderRows(orderRows);

        for (OrderRow orderRow : orderRows) {
            this.orderRowRepository.save(orderRow);
        }

        return this.cartRepository.save(cartUser);
    }

    public void deleteCart(final String userName, final Long orderRowId) {
        Optional<User> userGet = this.userRepository.findByEmail(userName);

        if (!userGet.isPresent()) {
            throw new UserException(String.format("Can't get user with name: %s", userName));
        }

        User user = userGet.get();

        Cart cartUser = Optional.ofNullable(user.getCart()).orElse(null);

        if (cartUser != null) {
            Optional<OrderRow> orderRow = this.orderRowRepository.findById(orderRowId);

            if (orderRow.isPresent()) {
                this.orderRowRepository.deleteById(orderRow.get().getId());
            }
        }
    }

    private List<OrderRow> fillByOrderRowNew(final OrderRowRequest cartRequest, final Cart cart) {
        List<OrderRow> orderRows = new ArrayList<>();

        OrderRow orderRowToSave = new OrderRow();
        orderRowToSave.setCart(cart);
        orderRowToSave.setDish(getDish(cartRequest.getDishId()));
        orderRowToSave.setQuantity(cartRequest.getDishQuantity());
        orderRows.add(orderRowToSave);

        return orderRows;
    }

    private List<OrderRow> fillListByOrderRow(final OrderRowRequest cartRequest, final Cart cart) {
        List<OrderRow> orderRows = new ArrayList<>();
        for (OrderRow orderRow : cart.getOrderRows()) {
            if (orderRow.getDish().getId().equals(cartRequest.getDishId())) {
                orderRow.setQuantity(cartRequest.getDishQuantity());
                orderRows.add(orderRow);
            }
        }

        OrderRow orderNewToSave = new OrderRow();
        orderNewToSave.setCart(cart);
        orderNewToSave.setDish(getDish(cartRequest.getDishId()));
        orderNewToSave.setQuantity(cartRequest.getDishQuantity());
        orderRows.add(orderNewToSave);

        return orderRows;
    }

    private Dish getDish(final Long dishId) {
        Optional<Dish> dish = this.dishRepository.findById(dishId);

        if (dish.isPresent()) {
            return dish.get();
        }

        throw new DishException("Can't find dish");
    }
}

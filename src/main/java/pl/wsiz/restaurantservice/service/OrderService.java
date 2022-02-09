package pl.wsiz.foodservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wsiz.foodservice.controller.model.OrderRowRequest;
import pl.wsiz.foodservice.exception.CartException;
import pl.wsiz.foodservice.exception.UserException;
import pl.wsiz.foodservice.model.*;
import pl.wsiz.foodservice.repository.DishRepository;
import pl.wsiz.foodservice.repository.OrderRepository;
import pl.wsiz.foodservice.repository.OrderRowRepository;
import pl.wsiz.foodservice.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private UserRepository userRepository;

    private OrderRowRepository orderRowRepository;

    private OrderRepository orderRepository;

    private DishRepository dishRepository;

    @Autowired
    public OrderService(UserRepository userRepository,
                        OrderRowRepository orderRowRepository,
                        OrderRepository orderRepository,
                        DishRepository dishRepository
    ) {
        this.userRepository = userRepository;
        this.orderRowRepository = orderRowRepository;
        this.orderRepository = orderRepository;
        this.dishRepository = dishRepository;
    }

    public List<Order> getOrders(final String userName) {
        Optional<User> user = this.userRepository.findByEmail(userName);

        if (!user.isPresent()) {
            throw new UserException(String.format("Cant find user with name %s", userName));
        }

        Optional<List<Order>> orders = this.orderRepository.findAllByUserId(user.get().getId());

        if (orders.isPresent()) {
            return orders.get();
        }

        return new ArrayList<>();
    }

    public Order saveOrder(final String userName, final List<OrderRowRequest> orderRowRequests) {

        if (orderRowRequests == null || orderRowRequests.isEmpty()) {
            throw new CartException("Can't save order");
        }

        Optional<User> userGet = this.userRepository.findByEmail(userName);

        if (!userGet.isPresent()) {
            throw new UserException("Can't find user");
        }

        Order order = new Order();

        order.setUser(userGet.get());

        Double totalPrice = calculateTotalPrice(orderRowRequests);

        order.setPrice(totalPrice);

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderRowRequest orderRow : orderRowRequests) {
            Optional<Dish> dish = this.dishRepository.findById(orderRow.getDishId());

            if (dish.isPresent()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setPrice(dish.get().getPrice());
                orderItem.setName(dish.get().getName());
                orderItem.setOrder(order);
                orderItems.add(orderItem);

                this.orderRowRepository.deleteById(orderRow.getOrderRowId());
            }
        }

        order.setOrderItems(orderItems);

        return this.orderRepository.save(order);
    }

    private Double calculateTotalPrice(final List<OrderRowRequest> orderRowRequests) {
        double totalPrice = 0;

        for (OrderRowRequest orderRowRequest : orderRowRequests) {
            Optional<Dish> dish = this.dishRepository.findById(orderRowRequest.getDishId());

            if (dish.isPresent()) {
                totalPrice += dish.get().getPrice();
            }
        }

        return totalPrice;
    }

}

package pl.wsiz.foodservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsiz.foodservice.controller.model.OrderRowRequest;
import pl.wsiz.foodservice.dto.OrderDto;
import pl.wsiz.foodservice.model.Order;
import pl.wsiz.foodservice.service.OrderService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static pl.wsiz.foodservice.dto.Converter.orderToOrderDto;

@RestController
@RequestMapping(value = "/api/user")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public ResponseEntity<?> saveOrder(Principal principal, @RequestBody @Valid List<OrderRowRequest> orderRequest) {
        this.orderService.saveOrder(principal.getName(), orderRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/order")
    public ResponseEntity<List<OrderDto>> getOrders(Principal principal) {
        List<Order> order = this.orderService.getOrders(principal.getName());
        return new ResponseEntity<List<OrderDto>>(orderToOrderDto(order), HttpStatus.OK);
    }

}

package pl.wsiz.foodservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsiz.foodservice.controller.model.OrderRowRequest;
import pl.wsiz.foodservice.dto.CartDto;
import pl.wsiz.foodservice.dto.Converter;
import pl.wsiz.foodservice.model.Cart;
import pl.wsiz.foodservice.service.CartService;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping(value = "/api/user")
public class CartController {

    private CartService cartService;

    @Autowired
    CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/cart")
    public ResponseEntity<?> saveCart(Principal principal, @RequestBody @Valid OrderRowRequest cartRequest) {
        Cart cart = this.cartService.saveCart(principal.getName(), cartRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<CartDto> getCart(Principal principal) {
        Cart cart = this.cartService.getCart(principal.getName());
        return new ResponseEntity<>(Converter.cartToCartDto(cart), HttpStatus.OK);
    }

    @DeleteMapping("/cart")
    public ResponseEntity<?> deleteCart(Principal principal, @RequestParam final Long orderRowId) {
        this.cartService.deleteCart(principal.getName(), orderRowId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

package pl.wsiz.foodservice.controller.model;

import lombok.Data;

import java.util.List;

@Data
public class CartRequest {
    private OrderRowRequest orderRowRequest;
}

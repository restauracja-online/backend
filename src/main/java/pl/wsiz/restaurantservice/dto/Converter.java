package pl.wsiz.foodservice.dto;

import pl.wsiz.foodservice.controller.model.DishRequest;
import pl.wsiz.foodservice.controller.model.IngredientRequest;
import pl.wsiz.foodservice.model.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Converter {

    // Util class is not allowed to be initialized
    private Converter() {
    }

    public static User signUpToUser(Credentials credentials) {
        User user = new User();
        user.setEmail(credentials.getEmail());
        user.setPassword(credentials.getPassword());

        return user;
    }

    public static UserDetails userToUserDetails(User user) {
        return UserDetails.builder()
                .email(user.getEmail())
                .status(user.getStatus())
                .role(user.getRole())
                .addresses(addressesToAddressDetailsList(user.getAddresses()))
                .build();
    }

    public static AddressDetails toAddressDetails(Address address) {
        return AddressDetails.builder()
                .city(address.getCity())
                .street(address.getStreet())
                .zipCode(address.getZipCode())
                .buildingNumber(address.getBuildingNumber())
                .status(address.getStatus())
                .build();
    }

    public static Ingredient ingredientRequestToIngredientModel(IngredientRequest ingredientRequest) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientRequest.getId());
        ingredient.setName(ingredientRequest.getName());
        ingredient.setPrice(ingredientRequest.getPrice());

        return ingredient;
    }

    public static IngredientDto ingredientToIngredientDto(Ingredient ingredient) {
        return IngredientDto.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .price(ingredient.getPrice())
                .build();
    }

    public static List<IngredientDto> ingredientListToIngredientDtoList(final List<Ingredient> ingredients) {
        List<IngredientDto> ingredientDtos = ingredients.stream().map(
                ingredient -> IngredientDto.builder().id(ingredient.getId()).name(ingredient.getName()).price(ingredient.getPrice()).build())
                .collect(Collectors.toList());
        return ingredientDtos;
    }

    public static List<DishDto> dishListToDishDtoList(final List<Dish> dishes) {
        List<DishDto> dishDtos = dishes.stream().map( dish -> DishDto.builder()
                .id(dish.getId())
                .name(dish.getName())
                .description(dish.getDescription())
                .price(dish.getPrice())
                .imgURL(dish.getImgURL())
                .ingredients(ingredientListToIngredientDtoList(dish.getIngredients()))
                .build()
        ).collect(Collectors.toList());
        return dishDtos;
    }

    public static Dish dishRequestToDishModel(final DishRequest dishRequest) {
        final Dish dish = new Dish();
        dish.setId(dishRequest.getId());
        dish.setName(dishRequest.getName());
        dish.setDescription(dishRequest.getDescription());
        dish.setPrice(dishRequest.getPrice());
        dish.setImgURL(dishRequest.getImgURL());
        dish.setIngredients(ingredientRequestToIngredient(dishRequest.getIngredients()));

        return dish;
    }

    public static DishDto dishToDishDto(Dish dish) {
        return DishDto.builder()
                .id(dish.getId())
                .name(dish.getName())
                .description(dish.getDescription())
                .price(dish.getPrice())
                .imgURL(dish.getImgURL())
                .ingredients(ingredientListToIngredientDtoList(dish.getIngredients()))
                .build();
    }

    public static CartDto cartToCartDto(Cart cart) {
        return CartDto.builder()
                .orderRowDtos(orderRowListToOrderRowDtoList(cart.getOrderRows()))
                .build();
    }

    public static List<OrderRowDto> orderRowListToOrderRowDtoList(List<OrderRow> orderRow) {
        List<OrderRowDto> orderRowDtos = orderRow.stream().map(Converter::orderRowToOrderRowDto).collect(Collectors.toList());
        return orderRowDtos;
    }

    public static OrderRowDto orderRowToOrderRowDto(OrderRow orderRow) {
        return OrderRowDto.builder()
                .orderRowId(orderRow.getId())
                .dishId(orderRow.getId())
                .dishDto(dishToDishDto(orderRow.getDish()))
                .dishQuantity(orderRow.getQuantity())
                .build();
    }

    public static List<OrderDto> orderToOrderDto(final List<Order> orders) {
        List<OrderDto> orderDtos = orders.stream().map(order -> {
            return OrderDto.builder().price(order.getPrice()).orderItem(orderItemListOrderItemDtoList(order.getOrderItems())).build();
        }).collect(Collectors.toList());

        return orderDtos;
    }

    public static OrderItemDto orderItemToOrderItemDto(final OrderItem orderItem) {
        return OrderItemDto.builder()
                .name(orderItem.getName())
                .price(orderItem.getPrice())
                .build();
    }

    public static List<OrderItemDto> orderItemListOrderItemDtoList(final List<OrderItem> orderItems) {
        List<OrderItemDto> orderItemDtoList = orderItems.stream().map(orderItem -> {
            return orderItemToOrderItemDto(orderItem);
        }).collect(Collectors.toList());

        return orderItemDtoList;
    }

    private static List<AddressDetails> addressesToAddressDetailsList(Collection<Address> addressList) {
        if (addressList == null) {
            return Collections.emptyList();
        }
        return addressList
                .stream()
                .map(Converter::toAddressDetails).collect(Collectors.toList());
    }

    private static List<Ingredient> ingredientRequestToIngredient(final List<IngredientRequest> ingredientRequests) {
        List<Ingredient> ingredients = ingredientRequests.stream().map(ingredientRequest -> {
            Ingredient ingredient = new Ingredient();
            ingredient.setId(ingredientRequest.getId());
            ingredient.setName(ingredientRequest.getName());
            ingredient.setPrice(ingredientRequest.getPrice());
            return ingredient;
        }).collect(Collectors.toList());

        return ingredients;
    }

}

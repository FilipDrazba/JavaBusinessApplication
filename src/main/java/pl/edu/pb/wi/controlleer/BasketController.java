package pl.edu.pb.wi.controlleer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pb.wi.controlleer.annotation.AccountType;
import pl.edu.pb.wi.controlleer.annotation.ApiRequestMapping;
import pl.edu.pb.wi.dto.request.BasketDtoRequest;
import pl.edu.pb.wi.dto.response.BasketDtoResponse;
import pl.edu.pb.wi.entity.Basket;
import pl.edu.pb.wi.entity.BasketProduct;
import pl.edu.pb.wi.entity.Role;
import pl.edu.pb.wi.service.BasketProductService;
import pl.edu.pb.wi.service.BasketService;

@RestController
@ApiRequestMapping
public class BasketController {
    private final BasketService basketService;
    private final BasketProductService basketProductService;

    @Autowired
    BasketController(
            BasketService basketService,
            BasketProductService basketProductService
    ) {
        this.basketService = basketService;
        this.basketProductService = basketProductService;
    }

    @GetMapping("basket/{id}")
    @AccountType(accountType = Role.RoleType.USER)
    BasketDtoResponse getBasketById(@PathVariable Long id) {
        Basket basket = basketService.getBasketById(id);
        return BasketDtoResponse.builder()
                .products(basketProductService.getProductsByBasketId(basket.getId()))
                .id(basket.getId())
                .build();
    }

    @GetMapping("basket/user/{id}")
    @AccountType(accountType = Role.RoleType.USER)
    BasketDtoResponse getBasketByUserId(@PathVariable Long id) {
        Basket basket = basketService.getBasketByUserId(id);
        return BasketDtoResponse.builder()
                .products(basketProductService.getProductsByBasketId(basket.getId()))
                .id(basket.getId())
                .build();
    }

    @PutMapping("basket")
    @AccountType(accountType = Role.RoleType.USER)
    Basket updateBasket(@RequestBody BasketDtoRequest basket) {
        return basketService.updateBasket(basket);
    }

    @PostMapping("basket/product")
    @AccountType(accountType = Role.RoleType.USER)
    Basket addProductToBasket(@RequestBody BasketProduct basketProduct) {
        return basketService.addProduct(basketProduct);
    }

    @DeleteMapping("basket/product/{id}")
    @AccountType(accountType = Role.RoleType.USER)
    Basket deleteProductFromBasket(@PathVariable Long id) {
        return basketService.deleteProductFromBasket(id);
    }
}
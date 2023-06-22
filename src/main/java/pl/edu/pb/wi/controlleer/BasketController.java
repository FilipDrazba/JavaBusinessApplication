package pl.edu.pb.wi.controlleer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(BasketController.class);

    @Autowired
    BasketController(BasketService basketService, BasketProductService basketProductService) {
        this.basketProductService = basketProductService;
        this.basketService = basketService;
    }

    @GetMapping("basket/{id}")
    @AccountType(accountType = Role.RoleType.USER)
    BasketDtoResponse getBasketById(@PathVariable Long id) {
        logger.info("Retrieving basket by ID: {}", id);
        Basket basket = basketService.getBasketById(id);
        logger.info("Basket retrieved successfully: {}", basket);
        return BasketDtoResponse.builder().products(basketProductService.getProductsByBasketId(id)).id(basket.getId()).build();
    }

    @GetMapping("basket/user/{id}")
    @AccountType(accountType = Role.RoleType.USER)
    BasketDtoResponse getBasketByUserId(@PathVariable Long id) {
        logger.info("Retrieving basket by user ID: {}", id);
        Basket basket = basketService.getBasketByUserId(id);
        logger.info("Basket retrieved successfully: {}", basket);
        return BasketDtoResponse.builder().products(basketProductService.getProductsByBasketId(id)).id(basket.getId()).build();
    }

    @PutMapping("basket")
    @AccountType(accountType = Role.RoleType.USER)
    Basket updateBasket(@RequestBody BasketDtoRequest basketDtoRequest) {
        logger.info("Updating basket: {}", basketDtoRequest);
        Basket updatedBasket = basketService.updateBasket(basketDtoRequest);
        logger.info("Basket updated successfully: {}", updatedBasket);
        return updatedBasket;
    }

    @PostMapping("basket/product")
    @AccountType(accountType = Role.RoleType.USER)
    Basket addProductToBasket(@RequestBody BasketProduct basketProduct) {
        logger.info("Adding product to basket: {}", basketProduct);
        Basket basket = basketService.addProduct(basketProduct);
        logger.info("Product added to basket successfully: {}", basket);
        return basket;
    }

    @DeleteMapping("basket/product/{id}")
    @AccountType(accountType = Role.RoleType.USER)
    Basket deleteProductFromBasket(@PathVariable Long id) {
        logger.info("Deleting product from basket with ID: {}", id);
        Basket basket = basketService.deleteProductFromBasket(id);
        logger.info("Product deleted from basket successfully: {}", basket);
        return basket;
    }
}

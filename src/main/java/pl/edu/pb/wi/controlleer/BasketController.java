package pl.edu.pb.wi.controlleer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pb.wi.controlleer.annotation.AccountType;
import pl.edu.pb.wi.controlleer.annotation.ApiRequestMapping;
import pl.edu.pb.wi.entity.Basket;
import pl.edu.pb.wi.entity.Role;
import pl.edu.pb.wi.service.BasketService;

@RestController
@ApiRequestMapping
public class BasketController {
    private final BasketService basketService;

    @Autowired
    BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping("basket/{id}")
    @AccountType(accountType = Role.RoleType.USER)
    Basket getBasketById(@PathVariable Long id) {
        Basket basket = basketService.getBasketById(id);
        basket.getUser().setPassword(null);
        return basket;
    }

    @GetMapping("basket/user/{id}")
    @AccountType(accountType = Role.RoleType.USER)
    Basket getBasketByUserId(@PathVariable Long id) {
        Basket basket = basketService.getBasketByUserId(id);
        basket.getUser().setPassword(null);
        return basket;
    }

    @PutMapping("basket")
    @AccountType(accountType = Role.RoleType.USER)
    Basket updateBasket(@RequestBody Basket basket) {
        Basket updated = basketService.updateBasket(basket);
        updated.getUser().setPassword(null);
        return updated;
    }
}

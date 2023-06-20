package pl.edu.pb.wi.service;

import pl.edu.pb.wi.entity.Basket;
import pl.edu.pb.wi.entity.BasketProduct;

public interface BasketService {
    Basket getBasketById(Long id);
    Basket getBasketByUserId(Long id);
    Basket updateBasket(Basket basket);
    Basket addProduct(BasketProduct basketProduct);
    Basket deleteProductFromBasket(Long id);
}

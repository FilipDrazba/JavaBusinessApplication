package pl.edu.pb.wi.service;

import pl.edu.pb.wi.entity.Basket;

public interface BasketService {
    Basket getBasketById(Long id);
    Basket getBasketByUserId(Long id);
    Basket updateBasket(Basket basket);
}

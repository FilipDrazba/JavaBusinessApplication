package pl.edu.pb.wi.service;

import pl.edu.pb.wi.dto.request.BasketDtoRequest;
import pl.edu.pb.wi.entity.Basket;
import pl.edu.pb.wi.entity.BasketProduct;
import pl.edu.pb.wi.entity.Product;

import java.util.List;

public interface BasketService {
    Basket getBasketById(Long id);
    Basket getBasketByUserId(Long id);
    Basket updateBasket(BasketDtoRequest basket);
    Basket addProduct(BasketProduct basketProduct);
    Basket deleteProductFromBasket(Long id);
    Basket create(Basket basket);
    List<Product> getBasketContentByUserId(Long userId);
}

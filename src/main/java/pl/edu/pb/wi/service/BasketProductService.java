package pl.edu.pb.wi.service;

import pl.edu.pb.wi.entity.BasketProduct;

import java.util.List;

public interface BasketProductService {
    List<BasketProduct> getProductsByBasketId(Long id);
}

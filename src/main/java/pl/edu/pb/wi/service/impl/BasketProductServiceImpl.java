package pl.edu.pb.wi.service.impl;

import org.springframework.stereotype.Service;
import pl.edu.pb.wi.entity.BasketProduct;
import pl.edu.pb.wi.repository.BasketProductRepository;
import pl.edu.pb.wi.service.BasketProductService;

import java.util.List;

@Service
public class BasketProductServiceImpl implements BasketProductService {
    private final BasketProductRepository basketProductRepository;

    BasketProductServiceImpl(BasketProductRepository basketProductRepository) {
        this.basketProductRepository = basketProductRepository;
    }


    @Override
    public List<BasketProduct> getProductsByBasketId(Long id) {
        return basketProductRepository.getAllByBasketId(id);
    }
}

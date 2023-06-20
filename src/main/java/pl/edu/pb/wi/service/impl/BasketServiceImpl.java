package pl.edu.pb.wi.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.entity.Basket;
import pl.edu.pb.wi.entity.BasketProduct;
import pl.edu.pb.wi.entity.User;
import pl.edu.pb.wi.repository.BasketProductRepository;
import pl.edu.pb.wi.repository.BasketRepository;
import pl.edu.pb.wi.service.BasketService;

@Service
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;
    private final BasketProductRepository basketProductRepository;

    @Autowired
    BasketServiceImpl(
            BasketRepository basketRepository,
            BasketProductRepository basketProductRepository
    ) {
        this.basketRepository = basketRepository;
        this.basketProductRepository = basketProductRepository;
    }

    @Override
    public Basket getBasketById(Long id) {
        return basketRepository.getBasketById(id);
    }

    @Override
    public Basket getBasketByUserId(Long id) {
        return basketRepository.getBasketByUserId(id);
    }

    @Override
    @Transactional
    public Basket updateBasket(Basket basket) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Basket oldBasket = basketRepository.getBasketByUserId(user.getId());
        basketProductRepository.saveAll(basket.getProducts());
        oldBasket.setProducts(basket.getProducts());

        return basketRepository.save(oldBasket);
    }
}

package pl.edu.pb.wi.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.dto.request.BasketDtoRequest;
import pl.edu.pb.wi.entity.Basket;
import pl.edu.pb.wi.entity.BasketProduct;
import pl.edu.pb.wi.entity.User;
import pl.edu.pb.wi.repository.BasketProductRepository;
import pl.edu.pb.wi.repository.BasketRepository;
import pl.edu.pb.wi.service.BasketService;

import java.util.List;

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
    public Basket updateBasket(BasketDtoRequest basket) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Basket oldBasket = basketRepository.getBasketByUserId(user.getId());

        basketProductRepository.saveAll(basket.getProducts());

        return basketRepository.save(oldBasket);
    }

    @Override
    public Basket addProduct(BasketProduct basketProduct) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Basket basket = basketRepository.getBasketByUserId(user.getId());
        List<BasketProduct> basketProducts = basketProductRepository.getAllByBasketId(basket.getId());

        for(BasketProduct b : basketProducts) {
            if(b.getProduct().getId().equals(basketProduct.getProduct().getId())) {
                b.setQuantity(b.getQuantity() + basketProduct.getQuantity());
                b.setBasket(basket);
                basketProductRepository.save(b);
                return basket;
            }
        }

        basketProduct.setBasket(basket);
        basketProductRepository.save(basketProduct);

        return basket;
    }

    @Override
    public Basket deleteProductFromBasket(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Basket basket = basketRepository.getBasketByUserId(user.getId());
        BasketProduct basketProduct = basketProductRepository.getByProductIdAndBasketId(basket.getId(), id);
        basketProductRepository.delete(basketProduct);

        return basket;
    }

    @Override
    public Basket create(Basket basket) {
        return basketRepository.save(basket);
    }
}

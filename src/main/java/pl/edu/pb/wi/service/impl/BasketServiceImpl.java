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

import java.util.Collection;

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

    @Override
    public Basket addProduct(BasketProduct basketProduct) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Basket basket = basketRepository.getBasketByUserId(user.getId());

        for(BasketProduct b : basket.getProducts()) {
            if(b.getProduct().getId().equals(basketProduct.getProduct().getId())) {
                b.setQuantity(b.getQuantity() + basketProduct.getQuantity());
                basketProductRepository.save(b);
                return basketRepository.save(basket);
            }
        }

        Collection<BasketProduct> savedProducts = basket.getProducts();
        savedProducts.add(basketProduct);

        basket.setProducts(savedProducts);

        return basketRepository.save(basket);
    }

    @Override
    public Basket deleteProductFromBasket(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Basket basket = basketRepository.getBasketByUserId(user.getId());
        Collection<BasketProduct> products = basket.getProducts();
        products.removeIf(product -> product.getId().equals(id));
        basket.setProducts(products);
        basketRepository.save(basket);

        return basketRepository.getBasketByUserId(user.getId());
    }
}

package pl.edu.pb.wi.service.impl;

import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(BasketServiceImpl.class);
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
        logger.info("Getting basket by ID: {}", id);
        return basketRepository.getBasketById(id);
    }

    @Override
    public Basket getBasketByUserId(Long id) {
        logger.info("Getting basket by user ID: {}", id);
        return basketRepository.getBasketByUserId(id);
    }

    @Override
    @Transactional
    public Basket updateBasket(BasketDtoRequest basket) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info("Updating basket for user: {}", user.getId());

        Basket oldBasket = basketRepository.getBasketByUserId(user.getId());

        basketProductRepository.saveAll(basket.getProducts());

        return basketRepository.save(oldBasket);
    }

    @Override
    public Basket addProduct(BasketProduct basketProduct) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info("Adding product to basket for user: {}", user.getId());
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
        logger.info("Deleting product from basket for user: {}", user.getId());
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

package pl.edu.pb.wi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.lang.NonNull;
import pl.edu.pb.wi.entity.BasketProduct;

import java.util.List;
import java.util.Optional;

@Repository
public interface BasketProductRepository extends JpaRepository<BasketProduct, Long> {

    @NonNull
    @Query("SELECT b FROM BasketProduct b WHERE b.id = :id")
    Optional<BasketProduct> findById(@NonNull Long id);

    @NonNull
    @Query("SELECT bp FROM BasketProduct bp WHERE bp.basket.id = :id")
    List<BasketProduct> getAllByBasketId(Long id);

    @NonNull
    @Query("SELECT bp FROM BasketProduct bp WHERE bp.basket.id = :basketId AND bp.product.id = :productId")
    BasketProduct getByProductIdAndBasketId(Long basketId, Long productId);
}

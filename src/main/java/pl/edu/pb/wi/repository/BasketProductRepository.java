package pl.edu.pb.wi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pb.wi.entity.BasketProduct;

import java.util.Optional;

@Repository
public interface BasketProductRepository extends JpaRepository<BasketProduct, Long> {
    @Query("SELECT b FROM BasketProduct b WHERE b.id = :id")
    Optional<BasketProduct> findById(Long id);
}

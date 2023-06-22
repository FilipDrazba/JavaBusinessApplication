package pl.edu.pb.wi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.pb.wi.entity.Basket;
import pl.edu.pb.wi.entity.Product;

import java.util.List;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
    @Query("SELECT b FROM Basket b WHERE b.id = :id")
    Basket getBasketById(Long id);

    @Query("SELECT b FROM Basket b WHERE b.user.id = :userId")
    Basket getBasketByUserId(Long userId);


    @Query("SELECT bp.product FROM BasketProduct bp " +
            "JOIN bp.basket b " +
            "JOIN b.user u " +
            "WHERE u.id = :userId")
    List<Product> findBasketContentByUserId(@Param("userId") Long userId);
}

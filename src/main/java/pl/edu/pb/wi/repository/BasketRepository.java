package pl.edu.pb.wi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pb.wi.entity.Basket;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
    @Query("SELECT b FROM Basket b WHERE b.id = :id")
    Basket getBasketById(Long id);

    @Query("SELECT b FROM Basket b WHERE b.user.id = :userId")
    Basket getBasketByUserId(Long userId);
}

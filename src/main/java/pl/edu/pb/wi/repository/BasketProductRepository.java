package pl.edu.pb.wi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pb.wi.entity.BasketProduct;

@Repository
public interface BasketProductRepository extends JpaRepository<BasketProduct, Long> {

}

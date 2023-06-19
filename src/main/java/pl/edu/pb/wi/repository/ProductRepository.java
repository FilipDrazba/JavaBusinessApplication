package pl.edu.pb.wi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import pl.edu.pb.wi.entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @NonNull
    @Override
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Optional<Product> findById(@NonNull Long id);

    @NonNull
    @Query("SELECT p FROM Product p")
    List<Product> getAll();
}

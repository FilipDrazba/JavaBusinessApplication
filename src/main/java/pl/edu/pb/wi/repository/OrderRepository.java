package pl.edu.pb.wi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import pl.edu.pb.wi.entity.Order;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @NonNull
    @Override
    @Query("SELECT o FROM Order o WHERE o.id = :id")
    Optional<Order> findById(@NonNull Long id);
}

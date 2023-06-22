package pl.edu.pb.wi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pb.wi.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}

package pl.edu.pb.wi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import pl.edu.pb.wi.entity.Customer;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @NonNull
    @Override
    @Query("SELECT c FROM Customer c WHERE c.id = :id")
    Optional<Customer> findById(@NonNull Long id);
}

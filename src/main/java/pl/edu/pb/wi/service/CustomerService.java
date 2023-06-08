package pl.edu.pb.wi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.entity.Customer;
import pl.edu.pb.wi.entity.Product;
import pl.edu.pb.wi.http.ResourceNotFoundException;
import pl.edu.pb.wi.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer"));
    }

    public Customer addProductToCart(Customer customer,
                                     Product product) {
        customer.getShoppingCart().add(product);
        return customerRepository.save(customer);
    }

    public void clearCart(Customer customer) {
        customer.getShoppingCart().clear();
        customerRepository.save(customer);
    }
}

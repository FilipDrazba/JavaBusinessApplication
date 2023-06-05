package pl.edu.pb.wi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.entity.Product;
import pl.edu.pb.wi.http.ResourceNotFoundException;
import pl.edu.pb.wi.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product"));
    }
}

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

    public Product update(Long id, Product product) {
        Product productBeingUpdated = findById(id);
        productBeingUpdated.setUpdatableValuesFrom(product);
        return productRepository.save(productBeingUpdated);
    }

    public void delete(Long id) {
        productRepository.delete(findById(id));
    }
}

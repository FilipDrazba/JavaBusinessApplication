package pl.edu.pb.wi.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.entity.Product;
import pl.edu.pb.wi.http.ResourceNotFoundException;
import pl.edu.pb.wi.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private static final Logger logger = LogManager.getLogger(ProductService.class);

    public Product findById(Long id) {
        logger.info("Finding product by ID: {}", id);
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product"));
    }

    public List<Product> getAll() {
        logger.info("Getting all products");
        return productRepository.getAll();
    }

    public Product update(Long id, Product product) {
        logger.info("Updating product with ID: {}", id);
        Product productBeingUpdated = findById(id);
        productBeingUpdated.setUpdatableValuesFrom(product);
        return productRepository.save(productBeingUpdated);
    }

    public void delete(Long id) {
        logger.info("Deleting product with ID: {}", id);
        productRepository.delete(findById(id));
    }
}

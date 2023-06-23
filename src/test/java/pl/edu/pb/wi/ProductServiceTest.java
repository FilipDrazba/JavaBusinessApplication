package pl.edu.pb.wi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pb.wi.entity.Product;
import pl.edu.pb.wi.http.ResourceNotFoundException;
import pl.edu.pb.wi.repository.ProductRepository;
import pl.edu.pb.wi.service.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testFindById_WhenProductExists_ShouldReturnProduct() {
        // Given
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // When
        Product result = productService.findById(productId);

        // Then
        assertEquals(product, result);
    }

    @Test
    public void testFindById_WhenProductDoesNotExist_ShouldThrowException() {
        // Given
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When
        assertThrows(ResourceNotFoundException.class, () -> productService.findById(productId));
    }

    @Test
    public void testGetAll_ShouldReturnAllProducts() {
        // Given
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        productList.add(new Product());
        productList.add(new Product());

        when(productRepository.getAll()).thenReturn(productList);

        // When
        List<Product> result = productService.getAll();

        // Then
        assertEquals(productList, result);
    }

    @Test
    public void testUpdate_WhenProductExists_ShouldUpdateProduct() {
        // Given
        Long productId = 1L;
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Old Name");
        existingProduct.setPrice(BigDecimal.valueOf(10.0));

        Product updatedProduct = new Product();
        updatedProduct.setName("New Name");
        updatedProduct.setPrice(BigDecimal.valueOf(20.0));

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Product result = productService.update(productId, updatedProduct);

        // Then
        assertEquals(updatedProduct.getName(), result.getName());
        assertEquals(updatedProduct.getPrice(), result.getPrice());
    }

    @Test
    public void testUpdate_WhenProductDoesNotExist_ShouldThrowException() {
        // Given
        Long productId = 1L;
        Product updatedProduct = new Product();
        updatedProduct.setName("New Name");
        updatedProduct.setPrice(BigDecimal.valueOf(20.0));

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When
        assertThrows(ResourceNotFoundException.class, () -> productService.update(productId, updatedProduct));

        // Then
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    public void testDelete_WhenProductExists_ShouldDeleteProduct() {
        // Given
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // When
        productService.delete(productId);

        // Then
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    public void testDelete_WhenProductDoesNotExist_ShouldThrowException() {
        // Given
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When
        assertThrows(ResourceNotFoundException.class, () -> productService.delete(productId));

        // Then
        verify(productRepository, never()).delete(any(Product.class));
    }
}

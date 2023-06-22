package pl.edu.pb.wi.controlleer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pb.wi.controlleer.annotation.AccountType;
import pl.edu.pb.wi.controlleer.annotation.ApiRequestMapping;
import pl.edu.pb.wi.dto.request.ProductDtoRequest;
import pl.edu.pb.wi.dto.response.ProductDtoResponse;
import pl.edu.pb.wi.entity.Product;
import pl.edu.pb.wi.entity.Role;
import pl.edu.pb.wi.mapper.ProductDtoMapper;
import pl.edu.pb.wi.repository.ProductRepository;
import pl.edu.pb.wi.service.ProductService;

import java.util.List;

@RestController
@ApiRequestMapping
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductRepository productRepository;

    @GetMapping("product/{id}")
    @AccountType(accountType = {Role.RoleType.ADMIN, Role.RoleType.MODERATOR, Role.RoleType.USER})
    public ResponseEntity<ProductDtoResponse> findProductById(@PathVariable Long id) {
        return ResponseEntity.ok(ProductDtoMapper.INSTANCE.fromProductToProductDtoResponse(productService.findById(id)));
    }

    @GetMapping("product")
    @AccountType(accountType = {Role.RoleType.ADMIN, Role.RoleType.MODERATOR, Role.RoleType.USER})
    public List<Product> getAllProducts() {
        return productService.getAll();
    }

    @PostMapping("product")
    @AccountType(accountType = {Role.RoleType.ADMIN, Role.RoleType.MODERATOR})
    public ResponseEntity<ProductDtoResponse> createProduct(@RequestBody ProductDtoRequest productDtoRequest) {
        Product unsavedProduct = ProductDtoMapper.INSTANCE.fromProductDtoRequestToProduct(productDtoRequest);
        Product savedProduct = productRepository.save(unsavedProduct);
        return ResponseEntity.ok(ProductDtoMapper.INSTANCE.fromProductToProductDtoResponse(savedProduct));
    }

    @PutMapping("product/{id}")
    @AccountType(accountType = {Role.RoleType.ADMIN, Role.RoleType.MODERATOR})
    public ResponseEntity<ProductDtoResponse> updateProduct(@PathVariable Long id,
                                                                @RequestBody ProductDtoRequest productDtoRequest) {
        Product product = productService.update(id, ProductDtoMapper.INSTANCE.fromProductDtoRequestToProduct(productDtoRequest));
        ProductDtoResponse response = ProductDtoMapper.INSTANCE.fromProductToProductDtoResponse(product);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("product/{id}")
    @AccountType(accountType = {Role.RoleType.ADMIN, Role.RoleType.MODERATOR})
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

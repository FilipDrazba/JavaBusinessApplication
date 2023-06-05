package pl.edu.pb.wi.controlleer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pb.wi.controlleer.annotation.ApiRequestMapping;
import pl.edu.pb.wi.dto.response.ProductDtoResponse;
import pl.edu.pb.wi.mapper.ProductDtoMapper;
import pl.edu.pb.wi.service.ProductService;

@RestController
@ApiRequestMapping
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("product/{id}")
    ResponseEntity<ProductDtoResponse> findProductById(@PathVariable Long id) {
        return ResponseEntity.ok(ProductDtoMapper.INSTANCE.fromProductToProductDtoResponse(productService.findById(id)));
    }
}

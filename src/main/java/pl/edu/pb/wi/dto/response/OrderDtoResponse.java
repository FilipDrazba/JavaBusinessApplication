package pl.edu.pb.wi.dto.response;

import pl.edu.pb.wi.entity.Product;

import java.util.List;

public record OrderDtoResponse(Long id,
                               List<Product> productList) {
}

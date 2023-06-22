package pl.edu.pb.wi.dto.response;

import pl.edu.pb.wi.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public record OrderDtoResponse(Long id,
                               Long ownerId,
                               List<Product> products,
                               BigDecimal amount) {
}

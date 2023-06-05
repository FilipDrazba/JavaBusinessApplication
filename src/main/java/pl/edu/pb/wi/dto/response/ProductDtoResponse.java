package pl.edu.pb.wi.dto.response;

import java.math.BigDecimal;

public record ProductDtoResponse(Long id,
                                 String name,
                                 BigDecimal price) {
}

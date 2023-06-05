package pl.edu.pb.wi.dto.request;

import java.math.BigDecimal;

public record ProductDtoRequest(String name,
                                BigDecimal price) {
}

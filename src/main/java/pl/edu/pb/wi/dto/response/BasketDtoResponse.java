package pl.edu.pb.wi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.edu.pb.wi.entity.BasketProduct;

import java.util.Collection;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BasketDtoResponse {
    Long id;
    Collection<BasketProduct> products;
}

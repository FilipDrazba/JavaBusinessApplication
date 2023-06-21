package pl.edu.pb.wi.dto.request;

import lombok.Data;
import pl.edu.pb.wi.entity.Basket;
import pl.edu.pb.wi.entity.BasketProduct;

import java.util.List;

@Data
public class BasketDtoRequest {
    Long id;
    List<BasketProduct> products;
    Basket basket;
}

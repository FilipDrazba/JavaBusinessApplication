package pl.edu.pb.wi.dto.response;

import pl.edu.pb.wi.entity.Product;

import java.util.List;

public record CustomerDtoResponse(Long id,
                                  String firstName,
                                  String lastName,
                                  List<Product> shoppingCart) {
}

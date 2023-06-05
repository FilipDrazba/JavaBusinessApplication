package pl.edu.pb.wi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.edu.pb.wi.dto.response.ProductDtoResponse;
import pl.edu.pb.wi.entity.Product;

@Mapper
public interface ProductDtoMapper {
    ProductDtoMapper INSTANCE = Mappers.getMapper(ProductDtoMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "price", source = "price")
    ProductDtoResponse fromProductToProductDtoResponse(Product product);
}

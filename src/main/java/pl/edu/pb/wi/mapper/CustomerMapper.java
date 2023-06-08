package pl.edu.pb.wi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.edu.pb.wi.dto.response.CustomerDtoResponse;
import pl.edu.pb.wi.entity.Customer;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "shoppingCart", source = "shoppingCart")
    CustomerDtoResponse formCustomerToCustomerDtoResponse(Customer customer);

}

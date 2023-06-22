package pl.edu.pb.wi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.edu.pb.wi.dto.response.OrderDtoResponse;
import pl.edu.pb.wi.entity.Order;

@Mapper
public interface OrderDtoMapper {
        OrderDtoMapper INSTANCE = Mappers.getMapper(pl.edu.pb.wi.mapper.OrderDtoMapper.class);

        @Mapping(target = "id", source = "id")
        @Mapping(target = "ownerId", source = "owner.id")
        @Mapping(target = "products", source = "products")
        @Mapping(target = "amount", source = "amount")
        OrderDtoResponse fromOrderToOrderDtoResponse(Order order);
}

package pl.edu.pb.wi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.edu.pb.wi.dto.response.OrderDtoResponse;
import pl.edu.pb.wi.entity.Order;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "productList", source = "productList")
    OrderDtoResponse orderToOrderDtoResponse(Order order);
}

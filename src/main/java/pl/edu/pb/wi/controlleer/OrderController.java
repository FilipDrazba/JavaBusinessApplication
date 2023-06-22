package pl.edu.pb.wi.controlleer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pb.wi.controlleer.annotation.ApiRequestMapping;
import pl.edu.pb.wi.dto.response.OrderDtoResponse;
import pl.edu.pb.wi.entity.Order;
import pl.edu.pb.wi.mapper.OrderDtoMapper;
import pl.edu.pb.wi.service.OrderService;

@RestController
@ApiRequestMapping
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/order/{id}")
    public ResponseEntity<OrderDtoResponse> findById(@PathVariable Long id) {
        Order order = orderService.findById(id);
        return ResponseEntity.ok(OrderDtoMapper.INSTANCE.fromOrderToOrderDtoResponse(order));
    }

}

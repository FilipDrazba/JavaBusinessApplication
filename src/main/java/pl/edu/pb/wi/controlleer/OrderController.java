package pl.edu.pb.wi.controlleer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pb.wi.controlleer.annotation.ApiRequestMapping;
import pl.edu.pb.wi.dto.response.OrderDtoResponse;
import pl.edu.pb.wi.entity.Order;
import pl.edu.pb.wi.entity.Product;
import pl.edu.pb.wi.entity.User;
import pl.edu.pb.wi.mapper.OrderDtoMapper;
import pl.edu.pb.wi.service.BasketService;
import pl.edu.pb.wi.service.OrderService;
import pl.edu.pb.wi.service.UserService;

import java.util.List;

@RestController
@ApiRequestMapping
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final BasketService basketService;
    private final UserService userService;

    @GetMapping("/order/{id}")
    public ResponseEntity<OrderDtoResponse> findById(@PathVariable Long id) {
        Order order = orderService.findById(id);
        return ResponseEntity.ok(OrderDtoMapper.INSTANCE.fromOrderToOrderDtoResponse(order));
    }

    @PostMapping("/order/set/{userId}")
    public ResponseEntity<OrderDtoResponse> createNewOrder(@PathVariable Long userId) {
        List<Product> productsList = basketService.getBasketContentByUserId(userId);
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(OrderDtoMapper.INSTANCE.fromOrderToOrderDtoResponse(
                orderService.createOrder(productsList, user)));
    }
}

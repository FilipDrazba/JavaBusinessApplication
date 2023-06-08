package pl.edu.pb.wi.controlleer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pb.wi.controlleer.annotation.ApiRequestMapping;
import pl.edu.pb.wi.dto.request.IdRequest;
import pl.edu.pb.wi.dto.request.ProductToCartDtoRequest;
import pl.edu.pb.wi.dto.response.CustomerDtoResponse;
import pl.edu.pb.wi.dto.response.OrderDtoResponse;
import pl.edu.pb.wi.entity.Customer;
import pl.edu.pb.wi.entity.Order;
import pl.edu.pb.wi.entity.Product;
import pl.edu.pb.wi.mapper.CustomerMapper;
import pl.edu.pb.wi.mapper.OrderMapper;
import pl.edu.pb.wi.service.CustomerService;
import pl.edu.pb.wi.service.OrderService;
import pl.edu.pb.wi.service.ProductService;

import java.util.ArrayList;

@RestController
@ApiRequestMapping
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final CustomerService customerService;
    private final ProductService productService;

    @PutMapping("order/add-product-to-cart")
    public ResponseEntity<CustomerDtoResponse> addProductToCart(@RequestBody ProductToCartDtoRequest request) {
        Customer customer = customerService.findById(request.CustomerId());
        Product product = productService.findById(request.ProductId());
        Customer savedCustomer = customerService.addProductToCart(customer, product);
        CustomerDtoResponse response = CustomerMapper.INSTANCE.formCustomerToCustomerDtoResponse(savedCustomer);
        return ResponseEntity.ok(response);
    }

    @PostMapping("order/submit")
    public ResponseEntity<OrderDtoResponse> submitOrder(@RequestBody IdRequest request) {
        Customer customer = customerService.findById(request.id());
        Order order = orderService.create(customer, new ArrayList<>(customer.getShoppingCart()));
        customerService.clearCart(customer);
        return ResponseEntity.ok(OrderMapper.INSTANCE.orderToOrderDtoResponse(order));
    }
}

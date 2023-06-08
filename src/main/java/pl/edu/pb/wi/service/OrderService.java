package pl.edu.pb.wi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.entity.Customer;
import pl.edu.pb.wi.entity.Order;
import pl.edu.pb.wi.entity.Product;
import pl.edu.pb.wi.repository.OrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Order create(Customer customer,
                        List<Product> productList) {
        Order order = new Order();
        order.setCustomer(customer);
        order.setProductList(productList);
        return orderRepository.save(order);
    }
}

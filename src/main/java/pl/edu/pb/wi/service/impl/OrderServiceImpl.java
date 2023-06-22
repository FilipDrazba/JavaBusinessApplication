package pl.edu.pb.wi.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.entity.Order;
import pl.edu.pb.wi.entity.Product;
import pl.edu.pb.wi.entity.User;
import pl.edu.pb.wi.http.ResourceNotFoundException;
import pl.edu.pb.wi.repository.OrderRepository;
import pl.edu.pb.wi.service.OrderService;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order"));
    }

    @Override
    public Order createOrder(List<Product> basketContent,
                             User owner){
        Order order=new Order(null,
                basketContent,
                owner);
        return orderRepository.save(order);
    }
}

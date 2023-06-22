package pl.edu.pb.wi.service;

import pl.edu.pb.wi.entity.Order;
import pl.edu.pb.wi.entity.Product;
import pl.edu.pb.wi.entity.User;

import java.util.List;

public interface OrderService {
    Order findById(Long id);

    Order createOrder(List<Product> basketContent, User owner);
}

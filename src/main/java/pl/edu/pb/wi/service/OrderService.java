package pl.edu.pb.wi.service;

import pl.edu.pb.wi.entity.Order;

public interface OrderService {
    Order findById(Long id);
}

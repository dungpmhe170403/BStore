package service;

import model.entity.Order;
import model.repository.OrderRepository;

public class OrderService {
    OrderRepository orderRepository;

    public OrderService() {
        orderRepository = OrderRepository.getInstance();
    }

    public Order saveToCart(int user_id, Order data) {
        try {
            return orderRepository.saveOrder(user_id, data);
        } catch (Exception e) {
            return null;
        }
    }
}

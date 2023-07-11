package service;

import model.entity.OrderItem;
import model.repository.OrderItemRepository;
import model.repository.ProductRepository;

public class OrderItemService {
    OrderItemRepository orderItemRepository;

    public OrderItemService() {
        orderItemRepository = OrderItemRepository.getInstance();
    }

    public void update(OrderItem data, String... cond) {
        orderItemRepository.update(data, cond);
    }

    public int delete(int id) {
        return orderItemRepository.delete(id);
    }

}

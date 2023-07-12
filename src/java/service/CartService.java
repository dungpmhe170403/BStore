package service;

import model.entity.Cart;
import model.entity.OrderItem;
import model.repository.CartRepository;
import model.repository.OrderItemRepository;

// lop service la lop trung gian de xu ly logic du lieu dc lay ra tu database
public class CartService {
    CartRepository cartRepository;
    OrderItemRepository orderItemRepository;

    public CartService() {
        cartRepository = CartRepository.getInstance();
        orderItemRepository = OrderItemRepository.getInstance();
    }

    public Cart getCart(int user_id) {
        return cartRepository.getCartOfUser(user_id);
    }

    public OrderItem saveToCart(int user_id, OrderItem data) {
        return cartRepository.saveToCart(user_id, data);
    }
}

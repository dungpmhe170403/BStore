package model.repository;

import model.entity.Cart;
import model.entity.OrderItem;
import ultis.DBHelper.repository.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class CartRepository extends Repository<Cart> {
    private static CartRepository cartRepository;
    private static OrderItemRepository orderItemRepository;

    public static CartRepository getInstance() {
        if (cartRepository == null) {
            cartRepository = new CartRepository();

        }
        return cartRepository;
    }

    private CartRepository() {
        table("cart");
        fillable("user_id");
        this.init();
        orderItemRepository = OrderItemRepository.getInstance();
    }

    @Override
    public Cart mapper(ResultSet rs) throws SQLException {
        return Cart.builder()
                .cart_id(rs.getInt("cart_id"))
                .user_id(rs.getInt("user_id"))
                .build();
    }

    public Cart mapWithOrderItems(ResultSet rs) {
        Cart cart;
        try {
            cart = this.mapper(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        cart.setItems(orderItemRepository.getAll());
        return cart;
    }

    public Cart getCartOfUser(int user_id) {
        cartRepository.queryHelper.select("*").where().condition("user_id = " + user_id).endCondition();
        Optional<ArrayList<Cart>> records = queryExecutor.records(queryHelper.build(), this::mapWithOrderItems);
        return records.map(carts -> carts.get(0)).orElse(null);
    }

    public OrderItem saveToCart(int user_id, OrderItem data) {
        Cart userCart = this.getCartOfUser(user_id);
        data.setCart_id(userCart.getCart_id());
        return orderItemRepository.save(data);
    }
}

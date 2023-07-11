package model.repository;

import model.entity.Cart;
import model.entity.Order;
import ultis.DBHelper.repository.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class OrderRepository extends Repository<Order> {
    private static OrderRepository orderRepository;
    private static CartRepository cartRepository;

    public static OrderRepository getInstance() {
        if (orderRepository == null) {
            orderRepository = new OrderRepository();
            cartRepository = CartRepository.getInstance();
        }
        return orderRepository;
    }

    private OrderRepository() {
        table("order");
        fillable("isPay", "cart_id", "note", "phone");
        this.init();
    }

    @Override
    public Order mapper(ResultSet rs) throws SQLException {
        return Order.builder()
                .cart_id(rs.getInt("cart_id"))
                .isPay(rs.getBoolean("isPay") ? 1 : 0)
                .note(rs.getString("note"))
                .phone(rs.getString("phone"))
                .build();
    }

    public Order mapWithCart(ResultSet rs, Integer user_id) {
        Order order;
        try {
            order = this.mapper(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        order.setCart(cartRepository.getCartOfUser(user_id));
        return order;
    }

    public Order getOrderOfUser(int user_id) {
        orderRepository.queryHelper.select("*").where().condition("user_id = " + user_id).endCondition();
        Optional<ArrayList<Order>> records = queryExecutor.records(queryHelper.build(), rs -> this.mapWithCart(rs, user_id));
        return records.map(carts -> carts.get(0)).orElse(null);
    }

    public Order saveOrder(int user, Order data) {
        Cart cart = cartRepository.getCartOfUser(user);
        data.setCart_id(cart.getCart_id());
        return orderRepository.save(data);
    }
}

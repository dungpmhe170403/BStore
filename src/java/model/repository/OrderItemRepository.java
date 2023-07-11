package model.repository;

import model.entity.Cart;
import model.entity.OrderItem;
import model.entity.Product;
import ultis.DBHelper.repository.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class OrderItemRepository extends Repository<OrderItem> {
    private static OrderItemRepository orderItemRepository;
    ProductRepository productRepository;
    ProductImageRepository productImageRepository;
    HashMap<String, OrderItem> orderItems;

    public static OrderItemRepository getInstance() {
        if (orderItemRepository == null) {
            orderItemRepository = new OrderItemRepository();

        }
        return orderItemRepository;
    }

    private OrderItemRepository() {
        table("order_item");
        fillable("cart_id", "product_id", "quantity", "size");
        this.init();
        orderItems = new HashMap<String, OrderItem>();
        productRepository = ProductRepository.getInstance();
        productImageRepository = ProductImageRepository.getInstance();

    }

    @Override
    public OrderItem mapper(ResultSet rs) throws SQLException {
        return OrderItem.builder()
                .order_item_id(rs.getInt("order_item_id"))
                .product_id(rs.getInt("product_id"))
                .quantity(rs.getInt("quantity"))
                .size(rs.getString("size"))
                .build();
    }

    public OrderItem mapWithProduct(ResultSet rs) {
        OrderItem od;
        try {
            od = this.mapper(rs);
            String id = String.valueOf(od.getOrder_item_id());
            OrderItem presentItem = orderItems.putIfAbsent(id, od);
            if (presentItem != null) {
                presentItem.setProduct(productRepository.mapper(rs));
                presentItem.getProduct().getImages().add(productImageRepository.mapper(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return od;
    }

    public ArrayList<OrderItem> getAll() {
        orderItems = new HashMap<String, OrderItem>();
        String sql = orderItemRepository.queryHelper.select("*")
                .innerJoin("products", "products.id = order_item.product_id")
                .innerJoin("shoes_images", "products.id = shoes_images.shoes_id").build();
        this.queryExecutor.records(sql, this::mapWithProduct);
        return new ArrayList<>(orderItems.values());
    }

    public int delete(int id) {
        queryHelper.delete().where().condition("order_item_id =" + id).endCondition();
        return queryExecutor.updateQuery(queryHelper.build());
    }
}

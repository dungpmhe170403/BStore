package model.entity;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class OrderItem {
    Integer order_item_id;
    Integer product_id;
    Product product;
    Integer quantity;
    String size;
    Integer cart_id;
}

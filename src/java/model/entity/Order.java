package model.entity;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Order {
    Integer order_id;
    Integer cart_id;
    Cart cart;
    String note;
    String phone;
    int isPay;
}

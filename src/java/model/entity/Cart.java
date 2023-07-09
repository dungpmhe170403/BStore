package model.entity;

import lombok.*;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class Cart {
    Integer cart_id;
    Integer user_id;
    ArrayList<OrderItem> items;
}

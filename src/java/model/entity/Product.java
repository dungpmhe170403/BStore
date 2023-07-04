package model.entity;

import lombok.*;

import java.util.ArrayList;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    private Integer id;
    private String name;
    private Float price;
    private Integer brand;
    private String description;
    private ArrayList<ProductImage> images;
}

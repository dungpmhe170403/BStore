package model.entity;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImage {

    private String image_path;
    private Integer image_id;
    private Integer shoes_id;
}

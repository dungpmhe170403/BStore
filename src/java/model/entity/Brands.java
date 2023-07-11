package model.entity;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Brands {
    private Integer brand_id;
    private String brand_name;
}

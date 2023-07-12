package model.entity;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// dinh nghia cac thuc the - bang co trong database 
// cac ten thuoc tinh giong voi database tien cho vc chuyen doi du lieu 
public class Brands {
    private Integer brand_id;
    private String brand_name;
}

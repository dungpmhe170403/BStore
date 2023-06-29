package model.entity;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private long user_id;
    private Integer role;
    private String username;
    private String password;
    private String email;
    private String address;
}

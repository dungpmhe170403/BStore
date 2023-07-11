package model.entity;

import lombok.*;
import ultis.DBHelper.bound.EntityMapper;
import ultis.DBHelper.model.Model;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Getter
@Setter
@AllArgsConstructor
@Builder
public class User {
    private long user_id;
    private String role;
    private String username;
    private String password;
    private String email;
    private String address;
}

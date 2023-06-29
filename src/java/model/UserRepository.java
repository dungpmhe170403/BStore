package model;

import model.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class UserRepository extends BaseRepository<User> {
    private static UserRepository userRepository;

    public static UserRepository getInstance() {
        if (userRepository == null) {
            userRepository = new UserRepository();

        }
        return userRepository;
    }

    private UserRepository() {
        super();
        this.table = "user";
        this.primaryKey = "user_id";
        this.fillable = new String[]{"username", "address", "role", "password", "email"};
    }

    public ArrayList<User> getByEmail(String email) {
        String[] columns = {"*"};
        String sql = getWhereSql(String.format("email LIKE '%s'", email), Arrays.asList(columns));
        return getUserRecords(sql);
    }

    public ArrayList<User> getByEmailAndPassword(String email, String password) {
        String[] columns = {"email", "role", "username", "address", "user_id"};
        String sql = getWhereSql(String.format("email LIKE '%s' AND password LIKE '%s'", email, password), Arrays.asList(columns));
        return getUserRecords(sql);
    }

    private ArrayList<User> getUserRecords(String sql) {
        return this.getRecords(sql, resultSet -> {
            try {
                return User.builder().username(resultSet.getString("username")).build();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

}

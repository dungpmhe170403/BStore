package model.repository;

import model.entity.User;
import ultis.DBHelper.repository.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class UserRepository extends Repository<User> {

    private static UserRepository userRepository;

    public static UserRepository getInstance() {
        if (userRepository == null) {
            userRepository = new UserRepository();

        }
        return userRepository;
    }

    private UserRepository() {
        table("user");
        fillable("username", "address", "password", "email");
        this.init();
    }

    @Override
    public User mapper(ResultSet rs) throws SQLException {
        return User.builder().username(rs.getString("username"))
                .user_id(rs.getInt("user_id"))
                .address(rs.getString("address"))
                .email(rs.getString("email"))
                .build();
    }

    public User get(int id) {
        String sql = queryHelper.select("*").where().condition("user_id = " + id).endCondition().build();
        Optional<ArrayList<User>> records = queryExecutor.records(sql, this);
        return records.map(users -> users.get(0)).orElse(null);
    }

    public User getByEmail(String email) {
        String sql = queryHelper
                .select("*")
                .where()
                .condition(String.format("email LIKE '%s'", email))
                .endCondition()
                .build();
        Optional<ArrayList<User>> records = queryExecutor.records(sql, this);
        return records.map(users -> users.get(0)).orElse(null);
    }

    public ArrayList<User> getByEmailAndPassword(String email, String password) {
        String sql = queryHelper.select("*")
                .where()
                .condition(String.format("email LIKE '%s'", email))
                .condition("password =" + password)
                .endCondition()
                .build();
        Optional<ArrayList<User>> records = queryExecutor.records(sql, this);
        return records.orElse(new ArrayList<>());
    }
}

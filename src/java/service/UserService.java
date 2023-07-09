package service;

import model.entity.User;
import model.repository.UserRepository;

import java.util.ArrayList;

import static ultis.Helper.wrapInQuote;

public class UserService {
    private UserRepository userRepository;

    public UserService() {
        userRepository = UserRepository.getInstance();
    }

    public User saveUser(User data) {
        return userRepository.save(data);
    }

    public boolean checkEmailIsUnique(String email) {
        return userRepository.getByEmail(email) == null;
    }

    public User authenticate(String email, String password) {
        ArrayList<User> userRecords = userRepository.getByEmailAndPassword(email, password);
        return userRecords.size() > 0 ? userRecords.get(0) : null;
    }

    public int update(int id, User updated) {
        return this.userRepository.update(updated, "user_id=" + id, "password =" + wrapInQuote(updated.getPassword()));
    }

    public User get(int id) {
        return userRepository.get(id);
    }
}

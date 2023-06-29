package service;

import model.entity.User;
import model.UserRepository;

import java.util.ArrayList;

public class UserService {
    private UserRepository userRepository;

    public UserService() {
        userRepository = UserRepository.getInstance();
    }

    public User saveUser(User data) {
        return userRepository.save(data);
    }

    public boolean checkEmailIsUnique(String email) {
        return !userRepository.getByEmail(email).isEmpty();
    }

    public User authenticate(String email, String password) {
        ArrayList<User> userRecords = userRepository.getByEmailAndPassword(email, password);
        return !userRecords.isEmpty() ? userRecords.get(0) : null;
    }
}

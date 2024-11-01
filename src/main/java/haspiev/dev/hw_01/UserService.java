package haspiev.dev.hw_01;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();

    public User createUser(String login) {
        if (users.stream().anyMatch(u -> u.getLogin().equals(login))) {
            throw new IllegalArgumentException("User with login " + login + " already exist.");
        }
        User user = new User(login);
        users.add(user);
        return user;
    }

    public User findUserById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not find."));

    }

    public List<User> getAllUsers() {
        return users;
    }

}

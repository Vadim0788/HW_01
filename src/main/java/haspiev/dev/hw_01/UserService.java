package haspiev.dev.hw_01;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private int count;

    private final Map<Integer, User> userMap;
    private final Set<String> takenLogins;

    private final AccountService accountService;

    public UserService(AccountService accountService) {
        this.userMap = new HashMap<>();
        this.count = 0;
        this.takenLogins = new HashSet<>();
        this.accountService = accountService;
    }

    public User createUser(String login) {
        if (takenLogins.contains(login)) {
            throw new IllegalArgumentException("User with login %s already exist."
                    .formatted(login));
        }
        takenLogins.add(login);
        User newUser = new User(++count, login);
        var newAccount = accountService.createAccount(newUser);
        newUser.addAccount(newAccount);
        userMap.put(newUser.getId(), newUser);
        return newUser;
    }

    public Optional<User> findUserById(int id) {
        return Optional.ofNullable(userMap.get(id));
    }

    public List<User> getAllUsers() {
        return userMap.values().stream().toList();
    }

}

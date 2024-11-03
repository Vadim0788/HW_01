package haspiev.dev.hw_01.operations.processors;

import haspiev.dev.hw_01.operations.ConsoleOperationType;
import haspiev.dev.hw_01.operations.OperationCommandProcessor;
import haspiev.dev.hw_01.user.User;
import haspiev.dev.hw_01.user.UserService;


import java.util.List;

public class ShowAllUsersProcessor implements OperationCommandProcessor {
    private final  UserService userService;

    public ShowAllUsersProcessor(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void processOperation() {
        List<User> users = userService.getAllUsers();
        System.out.println("List of all users:");
        users.forEach(System.out::println);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.SHOW_ALL_USERS;
    }
}

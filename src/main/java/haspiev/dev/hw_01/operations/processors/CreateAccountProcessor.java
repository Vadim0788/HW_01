package haspiev.dev.hw_01.operations.processors;

import haspiev.dev.hw_01.Account;
import haspiev.dev.hw_01.AccountService;
import haspiev.dev.hw_01.UserService;
import haspiev.dev.hw_01.operations.ConsoleOperationType;
import haspiev.dev.hw_01.operations.OperationCommandProcessor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CreateAccountProcessor implements OperationCommandProcessor {
    private final Scanner scanner;
    private final UserService userService;
    private final AccountService accountService;

    public CreateAccountProcessor(Scanner scanner, UserService userService, AccountService accountService) {
        this.scanner = scanner;
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public boolean processOperation() {
        System.out.println("Enter the user ID for creating a new account:");
        int userId = Integer.parseInt(scanner.nextLine());
        var user = userService.findUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("No such user with id=%s/n"
                        .formatted(userId)));
        Account account = accountService.createAccount(user);
        user.addAccount(account);
        System.out.println("New account created with ID: " + account.getId());
        return true;

    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }

}

package haspiev.dev.hw_01.operations.processors;

import haspiev.dev.hw_01.operations.ConsoleOperationType;
import haspiev.dev.hw_01.operations.OperationCommandProcessor;
import haspiev.dev.hw_01.account.AccountService;
import haspiev.dev.hw_01.user.UserService;

import java.util.Scanner;

public class CreateAccountProcessor implements OperationCommandProcessor {
    private final Scanner scanner;
    private final UserService userService;
    private final AccountService accountService;

    public CreateAccountProcessor(
            Scanner scanner,
            UserService userService,
            AccountService accountService) {
        this.scanner = scanner;
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void processOperation() {
        System.out.println("Enter the user id for which to create an account:");
        int userId = Integer.parseInt(scanner.nextLine());
        var user = userService.findUserByID(userId)
                .orElseThrow(() -> new IllegalArgumentException("No such user with id=%s/n"
                        .formatted(userId)));
        var account = accountService.createAccount(user);
        user.getAccountList().add(account);
        System.out.printf("New account create with Id: %s for user: %s/n"
                .formatted(account.getId(), user.getLogin()));
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }
}

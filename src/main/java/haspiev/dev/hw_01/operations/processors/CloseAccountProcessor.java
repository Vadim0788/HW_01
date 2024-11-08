package haspiev.dev.hw_01.operations.processors;

import haspiev.dev.hw_01.account.Account;
import haspiev.dev.hw_01.account.AccountService;
import haspiev.dev.hw_01.operations.ConsoleOperationType;
import haspiev.dev.hw_01.operations.OperationCommandProcessor;
import haspiev.dev.hw_01.user.User;
import haspiev.dev.hw_01.user.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;
@Component
public class CloseAccountProcessor implements OperationCommandProcessor {
    private  final Scanner scanner;

    private final AccountService accountService;
    private final UserService userService;

    public CloseAccountProcessor(
            Scanner scanner,
            AccountService accountService,
            UserService userService
    ) {
        this.scanner = scanner;
        this.accountService = accountService;
        this.userService = userService;
    }

    @Override
    public void processOperation() {
        System.out.println("Enter account id to close:");
        int accountId = Integer.parseInt(scanner.nextLine());
        Account account = accountService.closeAccount(accountId);
        User user = userService.findUserByID(account.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("No such user with id=%s"
                        .formatted(account.getUserId())));
        user.getAccountList().remove(account);
        System.out.printf("Account successfully closed with id=%s%n", accountId);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CLOSE;
    }
}

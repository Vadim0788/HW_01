package haspiev.dev.hw_01.operations.processors;

import haspiev.dev.hw_01.Account;
import haspiev.dev.hw_01.AccountService;
import haspiev.dev.hw_01.operations.ConsoleOperationType;
import haspiev.dev.hw_01.operations.OperationCommandProcessor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CreateAccountProcessor implements OperationCommandProcessor {
    private final Scanner scanner;

    private final AccountService accountService;

    public CreateAccountProcessor(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    @Override
    public void processOperation() {
        System.out.println("Enter the user ID for creating a new account:");
        int userId = Integer.parseInt(scanner.nextLine());

        Account account = accountService.createAccount(userId);
        System.out.println("New account created with ID: " + account.getId());

    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }

}

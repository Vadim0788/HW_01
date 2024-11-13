package haspiev.dev.hw_01.operations.processors;


import haspiev.dev.hw_01.AccountService;
import haspiev.dev.hw_01.operations.ConsoleOperationType;
import haspiev.dev.hw_01.operations.OperationCommandProcessor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CloseAccountProcessor implements OperationCommandProcessor {
    private final Scanner scanner;

    private final AccountService accountService;

    public CloseAccountProcessor(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;

    }

    @Override
    public void processOperation() {

        System.out.println("Enter account ID:");
        int closeAccountId = Integer.parseInt(scanner.nextLine());

        accountService.closeAccount(closeAccountId);
        System.out.println("Account with ID " + closeAccountId
                + " has been closed");


    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CLOSE;
    }
}

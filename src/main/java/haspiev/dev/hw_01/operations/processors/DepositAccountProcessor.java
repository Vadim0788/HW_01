package haspiev.dev.hw_01.operations.processors;

import haspiev.dev.hw_01.AccountService;
import haspiev.dev.hw_01.operations.ConsoleOperationType;
import haspiev.dev.hw_01.operations.OperationCommandProcessor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class DepositAccountProcessor implements OperationCommandProcessor {
    private final Scanner scanner;

    private final AccountService accountService;

    public DepositAccountProcessor(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    @Override
    public boolean processOperation() {
        System.out.println("Enter account ID:");
        Long depositAccountId = Long.parseLong(scanner.nextLine());
        System.out.println("Enter amount to deposit:");
        double depositAmount = Double.parseDouble(scanner.nextLine());

        accountService.deposit(depositAccountId, depositAmount);
        System.out.println("Amount " + depositAmount + " deposited.");
        return true;
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }
}

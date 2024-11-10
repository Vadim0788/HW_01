package haspiev.dev.hw_01.operations.processors;

import haspiev.dev.hw_01.AccountService;
import haspiev.dev.hw_01.operations.ConsoleOperationType;
import haspiev.dev.hw_01.operations.OperationCommandProcessor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AccountWithdrawProcessor implements OperationCommandProcessor {
    private final Scanner scanner;

    private final AccountService accountService;

    public AccountWithdrawProcessor(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    @Override
    public void processOperation() {
        System.out.println("Enter account ID:");
        int withdrawalAccountId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter amount to withdrawal:");
        double withdrawalAmount = Double.parseDouble(scanner.nextLine());

        accountService.withdraw(withdrawalAccountId, withdrawalAmount);
        System.out.println("Amount " + withdrawalAmount + " withdrawal.");
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_WITHDRAW;
    }
}

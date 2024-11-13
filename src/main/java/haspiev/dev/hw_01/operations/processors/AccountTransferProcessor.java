package haspiev.dev.hw_01.operations.processors;

import haspiev.dev.hw_01.AccountService;
import haspiev.dev.hw_01.operations.ConsoleOperationType;
import haspiev.dev.hw_01.operations.OperationCommandProcessor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AccountTransferProcessor implements OperationCommandProcessor {
    private final Scanner scanner;

    private final AccountService accountService;

    public AccountTransferProcessor(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    @Override
    public boolean processOperation() {

        System.out.println("Enter source account ID:");
        int sourceAccountId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter target account ID:");
        int targetAccountId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter amount to transfer:");
        double transferAmount = Double.parseDouble(scanner.nextLine());
        accountService.transfer(sourceAccountId, targetAccountId, transferAmount);
        System.out.println("Amount " + transferAmount
                + " transferred from account " + sourceAccountId
                + " to account  " + targetAccountId + ".");
        return true;

    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_TRANSFER;
    }

}


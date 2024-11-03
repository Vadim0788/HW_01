package haspiev.dev.hw_01.operations.processors;

import haspiev.dev.hw_01.account.AccountService;
import haspiev.dev.hw_01.operations.ConsoleOperationType;
import haspiev.dev.hw_01.operations.OperationCommandProcessor;

import java.sql.SQLOutput;
import java.util.Scanner;

public class DepositAccountProcessor implements OperationCommandProcessor {

    private  final Scanner scanner;

    private final AccountService accountService;

    public DepositAccountProcessor(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }


    @Override
    public void processOperation() {
        System.out.println("Enter account id:");
        int accounId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter amount to deposit:");
        int amountToDeposit = Integer.parseInt(scanner.nextLine());
        accountService.depositAccount(accounId, amountToDeposit);
        System.out.println("Successfully deposited amount %s to account %s"
                .formatted(amountToDeposit, accounId));
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }
}

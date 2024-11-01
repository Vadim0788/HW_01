package haspiev.dev.hw_01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class OperationsConsoleListener {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;

    public void startListening() {
        Scanner scanner = new Scanner(System.in);
        boolean stopListening = false;
        while (!stopListening) {
            System.out.println("""
                    Please enter one of operation type:
                    -USER_CREATE
                    - SHOW_ALL_USER
                    - ACCOUNT_CREATE
                    - ACCOUNT_DEPOSIT
                    - ACCOUNT_WITHDRAW
                    - ACCOUNT_TRANSFER
                    - ACCOUNT_CLOSE""");
            String command = scanner.nextLine().trim();

            switch (command) {
                case "USER_CREATE":
                    System.out.println("Enter login for new user:");
                    String login = scanner.nextLine();
                    try {
                        User user = userService.createUser(login);
                        System.out.println("User crated: " + user);
                    } catch (Exception e) {
                        System.out.println("Error creating user: " + e.getMessage());
                    }
                    break;
                case "SHOW_ALL_USER":
                    userService.getAllUsers().forEach(System.out::println);
                    break;
                case "ACCOUNT_CREATE":
                    System.out.println("Enter the user ID for creating a new account:");
                    int userId = Integer.parseInt(scanner.nextLine());
                    try {
                        Account account = accountService.createAccount(userId);
                        System.out.println("Account created with ID: " + account.getId());
                    } catch (Exception e) {
                        System.out.println("Error creating account: " + e.getMessage());
                    }
                    break;
                case "ACCOUNT_DEPOSIT":
                    System.out.println("Enter account ID:");
                    int depositAccountId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter amount to deposit:");
                    double depositAmount = Double.parseDouble(scanner.nextLine());
                    try {
                        accountService.deposit(depositAccountId, depositAmount);
                        System.out.println("Amount " + depositAmount + " deposited.");
                    } catch (Exception e) {
                        System.out.println("Error depositing: " + e.getMessage());
                    }
                    break;
                case "ACCOUNT_WITHDRAW":
                    System.out.println("Enter account ID:");
                    int withdrawalAccountId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter amount to withdrawal:");
                    double withdrawalAmount = Double.parseDouble(scanner.nextLine());
                    try {
                        accountService.withdraw(withdrawalAccountId, withdrawalAmount);
                        System.out.println("Amount " + withdrawalAmount + " withdrawal.");
                    } catch (Exception e) {
                        System.out.println("Error withdrawal: " + e.getMessage());
                    }
                    break;
                case "ACCOUNT_TRANSFER":
                    System.out.println("Enter source account ID:");
                    int sourceAccountId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter target account ID:");
                    int targetAccountId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter amount to transfer:");
                    double transferAmount = Double.parseDouble(scanner.nextLine());
                    try {
                        accountService.transfer(sourceAccountId, targetAccountId, transferAmount);
                        System.out.println("Amount transfer (" + transferAmount + ") for " + sourceAccountId + " to " + targetAccountId + " .");
                    } catch (Exception e) {
                        System.out.println("Error transfer: " + e.getMessage());
                    }
                    break;
                case "ACCOUNT_CLOSE":
                    System.out.println("Enter account ID:");
                    int closeAccountId = Integer.parseInt(scanner.nextLine());

                    try {
                        accountService.closeAccount(closeAccountId);
                        System.out.println("Account was deleted");
                    } catch (Exception e) {
                        System.out.println("Closing error: " + e.getMessage());
                    }
                    break;
                case "q":
                    stopListening = true;
            }
        }
    }
}

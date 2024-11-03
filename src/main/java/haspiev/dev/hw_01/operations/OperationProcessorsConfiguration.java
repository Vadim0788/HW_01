package haspiev.dev.hw_01.operations;

import haspiev.dev.hw_01.operations.processors.*;
import haspiev.dev.hw_01.account.AccountService;
import haspiev.dev.hw_01.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class OperationProcessorsConfiguration {
    @Bean
    public CreateUserProcessor createUserProcessor(
            Scanner scanner,
            UserService userService
    ) {
        return new CreateUserProcessor(scanner, userService);
    }

    @Bean
    public CreateAccountProcessor createAccountProcessor(
            Scanner scanner,
            UserService userService,
            AccountService accountService
    ) {
        return new CreateAccountProcessor(scanner, userService, accountService);
    }

    @Bean
    public ShowAllUsersProcessor showAllUsersProcessor(
            UserService userService
    ) {
        return new ShowAllUsersProcessor(userService);
    }

    @Bean
    public DepositAccountProcessor depositAccountProcessor(
            Scanner scanner,
            AccountService accountService
    ) {
        return new DepositAccountProcessor(scanner, accountService);
    }

    @Bean
    public AccountWithdrawProcessor withdrawProcessor(
            Scanner scanner,
            AccountService accountService
    ) {
        return new AccountWithdrawProcessor(scanner, accountService);
    }

    @Bean
    public CloseAccountProcessor closeAccountProcessor(
            Scanner scanner,
            AccountService accountService,
            UserService userService
    ) {
        return new CloseAccountProcessor(
                scanner,
                accountService,
                userService);
    }
    @Bean
    public AccountTransferProcessor accountTransferProcessor (
            Scanner scanner,
            AccountService accountService
    ) {
        return new AccountTransferProcessor(scanner, accountService);
    }
}

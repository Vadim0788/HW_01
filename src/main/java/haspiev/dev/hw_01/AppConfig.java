package haspiev.dev.hw_01;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Value;

import java.util.Scanner;


@Configuration
@ComponentScan(basePackages = "haspiev.dev.hw_01")
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value("${account.default-amount}")
    private double defaultAmount;

    @Value("${account.transfer-commission}")
    private double transferCommission;

    @Bean(name = "defaultAmount")
    public double getDefaultAmount() {
        return defaultAmount;
    }

    @Bean(name = "transferCommission")
    public double getTransferCommission() {
        return transferCommission;
    }

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }
}
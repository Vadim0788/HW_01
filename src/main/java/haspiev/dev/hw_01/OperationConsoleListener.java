package haspiev.dev.hw_01;

import haspiev.dev.hw_01.operations.ConsoleOperationType;
import haspiev.dev.hw_01.operations.OperationCommandProcessor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Scanner;

@Component
public class OperationConsoleListener {
    private final Scanner scanner;
    private final Map<ConsoleOperationType, OperationCommandProcessor> processorMap;

    public OperationConsoleListener(
            Scanner scanner,
            Map<ConsoleOperationType, OperationCommandProcessor> processorMap
    ) {
        this.scanner = scanner;
        this.processorMap = processorMap;

    }

    public void listenUpdate() {

        while (true) {
            var operationType = listenNextOperation();
            processNextOperation(operationType);
        }
    }

    private ConsoleOperationType listenNextOperation() {
        System.out.println("\nPlease type next operations: ");
        printAllAvalableOperations();
        System.out.println();
        while(true){
            var nextOperataion = scanner.nextLine();
            try {
                return ConsoleOperationType.valueOf(nextOperataion);
            } catch (IllegalArgumentException e) {
                System.out.println("No such command found");
            }
        }
    }

    private void printAllAvalableOperations() {
        processorMap.keySet()
                .forEach(System.out::println);
    }

    public void processNextOperation(ConsoleOperationType operationType){

        try {
            var processor = processorMap.get(operationType);
            processor.processOperation();
        } catch (Exception e) {
            System.out.printf(
                    "Error executing command %s: error=%s%n", operationType,
                    e.getMessage()
            );
        }
    }

    public void start() {
        System.out.println("Console listener stated");
    }

    public void endListen() {
        System.out.println("Console listener end listen");
    }
}

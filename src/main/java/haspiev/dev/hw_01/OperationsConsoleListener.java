package haspiev.dev.hw_01;

import haspiev.dev.hw_01.operations.ConsoleOperationType;
import haspiev.dev.hw_01.operations.OperationCommandProcessor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class OperationsConsoleListener {
    private final Scanner scanner;
    private final Map<ConsoleOperationType, OperationCommandProcessor> processorMap;
    private boolean doListen ;

    public OperationsConsoleListener(
            Scanner scanner,
            List<OperationCommandProcessor> processorList
    ) {
        this.scanner = scanner;
        this.processorMap = processorList
                .stream()
                .collect(
                        Collectors.toMap(
                                OperationCommandProcessor::getOperationType,
                                processor -> processor
                        )
                );
        this.doListen = true;

    }

    public void startListening() {
        System.out.println("Console listener stated");
        while (doListen) {
            var operationType = listenNextOperation();
            if (operationType == null) {
                return;
            }
            doListen = processNextOperation(operationType);
        }
    }

    private ConsoleOperationType listenNextOperation() {
        System.out.println("\nPlease type next operations: ");
        printAllAvailableOperations();
        System.out.println();
        while(doListen){
            var nextOperation = scanner.nextLine();
            try {
                return ConsoleOperationType.valueOf(nextOperation);
            } catch (IllegalArgumentException e) {
                System.out.println("No such command found");
            }
        }
        return null;
    }

    private void printAllAvailableOperations() {
        processorMap.keySet()
                .forEach(System.out::println);
    }

    public boolean processNextOperation(ConsoleOperationType operationType){
        boolean returnedValue = true;
        try {
            var processor = processorMap.get(operationType);
            processor.processOperation();

        } catch (Exception e) {
            System.out.printf(
                    "Error executing command %s: error=%s%n", operationType,
                    e.getMessage()
            );
        }
        return  returnedValue;
    }

}

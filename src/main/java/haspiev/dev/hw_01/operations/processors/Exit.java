package haspiev.dev.hw_01.operations.processors;

import haspiev.dev.hw_01.operations.ConsoleOperationType;
import haspiev.dev.hw_01.operations.OperationCommandProcessor;
import org.springframework.stereotype.Component;

@Component
public class Exit implements OperationCommandProcessor {
    @Override
    public boolean processOperation() {
        System.out.println("Console listener end listen");
        return false;
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.q;
    }
}

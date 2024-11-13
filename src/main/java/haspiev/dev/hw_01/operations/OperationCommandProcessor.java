package haspiev.dev.hw_01.operations;

public interface OperationCommandProcessor {
    boolean processOperation();

    ConsoleOperationType getOperationType();
}

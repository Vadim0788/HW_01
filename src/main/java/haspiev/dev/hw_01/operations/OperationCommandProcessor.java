package haspiev.dev.hw_01.operations;

public interface OperationCommandProcessor {
    void processOperation();

    ConsoleOperationType getOperationType();
}

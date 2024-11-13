package haspiev.dev.hw_01.operations;

public enum ConsoleOperationType {
    /*
    Создание нового пользователя
    */
    USER_CREATE,
    /*
    Показать всех пользователей
    */
    SHOW_ALL_USERS,
    /*
    Создание нового счета для пользователя.*/
    ACCOUNT_CREATE,
    /*

     * Закрытие счета
     * */
    ACCOUNT_CLOSE,
    /*
     * Пополнение счета
     */
    ACCOUNT_DEPOSIT,
    /*
     * Перевод средств между счетами.*/
    ACCOUNT_TRANSFER,
    /*
     * Снятие средств со счета.
     * */
    ACCOUNT_WITHDRAW

}

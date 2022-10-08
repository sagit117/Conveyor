package ru.axel.conveyor;

/**
 * Ошибка обработки запроса конвейером.
 */
public final class PipelinesException extends Exception {
    public PipelinesException(String msg) { super(msg); }
}

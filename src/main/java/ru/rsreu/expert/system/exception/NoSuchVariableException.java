package ru.rsreu.expert.system.exception;

public class NoSuchVariableException extends RuntimeException {
    public NoSuchVariableException() {
        super();
    }

    public NoSuchVariableException(String name) {
        super("No variable with name: " + name);
    }
}

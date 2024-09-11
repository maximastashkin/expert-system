package ru.rsreu.expert.system.exception;

public class ImpossibleToCalculateTargetException extends RuntimeException {
    public ImpossibleToCalculateTargetException() {
        super();
    }

    public ImpossibleToCalculateTargetException(String targetVariableName) {
        super("Unable to calculate target variable: " + targetVariableName);
    }
}

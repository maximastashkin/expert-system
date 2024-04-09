package ru.rsreu.expert.system.exception;

public class ImpossibleToCalculateTarget extends RuntimeException {
    public ImpossibleToCalculateTarget() {
        super();
    }

    public ImpossibleToCalculateTarget(String targetVariableName) {
        super("Unable to calculate target variable: " + targetVariableName);
    }
}

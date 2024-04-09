package ru.rsreu.expert.system.data;

public interface Context {
    boolean exists(String name);
    Variable get(String name);
    void add(Variable variable);
}

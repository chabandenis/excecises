package ru.chaban.inno.service;

@FunctionalInterface
public interface Command {
    void perform();
}

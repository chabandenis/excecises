package ru.chaban.inno.service;

public interface Unit<E> {
    void putCommand(Command command);

    E undo() throws NothingToUndo;
}

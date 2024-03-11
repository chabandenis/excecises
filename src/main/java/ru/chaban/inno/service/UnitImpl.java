package ru.chaban.inno.service;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayDeque;
import java.util.Deque;

@Getter
@Setter
public class UnitImpl<E> implements Unit<E> {

    private Deque<Command> commands;

    public UnitImpl() {
        commands = new ArrayDeque<>();
    }

    @Override
    public void putCommand(Command command) {
        commands.push(command);
    }

    @Override
    public E undo() throws NothingToUndo {
        if (commands.isEmpty()) throw new NothingToUndo();
        commands.pop().perform();
        return (E) this;
    }

}

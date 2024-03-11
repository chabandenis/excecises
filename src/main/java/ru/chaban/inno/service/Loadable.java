package ru.chaban.inno.service;

public interface Loadable<E> {
    void load();
    void save();
}

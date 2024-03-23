package ru.chaban.inno.service;

public interface Loadable<E> {
    void load(int item);
    int save();
}

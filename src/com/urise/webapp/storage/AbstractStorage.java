package com.urise.webapp.storage;

/**
 * AbstractStorage.
 *
 * @author ADivaev
 */
public abstract class AbstractStorage implements Storage {

    //Получаем индекс нужного элемента
    protected abstract int getIdx(String uuid);
}
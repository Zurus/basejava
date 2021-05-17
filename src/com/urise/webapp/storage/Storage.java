package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Storage.
 *
 * @author ADivaev
 */
public interface Storage {
    void clear();
    void update(Resume resume);
    void save(Resume r);
    Resume get(String uuid);
    void delete(String uuid);
    Resume[] getAll();
    int size();
}
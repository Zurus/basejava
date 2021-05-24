package com.urise.webapp.storage.map;

import java.util.Map;
import java.util.TreeMap;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.AbstractStorage;

/**
 * MapStorage.
 *
 * @author ADivaev
 */
public class MapStorage extends AbstractStorage {
    private final Map storagemap = new TreeMap();


    @Override
    public void clear() {
        storagemap.clear();
    }

    @Override
    public void update(Resume resume) {
        storagemap.computeIfPresent(resume.getUuid(),(a,b) -> resume);
    }

    @Override
    public void save(Resume r) {
        storagemap.putIfAbsent(r.getUuid(), r);
    }

    @Override
    public Resume get(String uuid) {
        return (Resume) storagemap.get(uuid);
    }

    @Override
    public void delete(String uuid) {
        if (storagemap.containsKey(uuid)) {
            storagemap.remove(uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public Resume[] getAll() {
        return (Resume[]) storagemap.values().toArray(Object[]::new);
    }

    @Override
    public int size() {
        return storagemap.size();
    }

    @Override
    protected int getIdx(String uuid) {
        return 0;
    }
}
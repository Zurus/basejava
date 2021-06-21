package com.urise.webapp.storage;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.urise.webapp.model.Resume;

/**
 * MapUuidStorage.
 *
 * @author ADivaev
 */
public class MapUuidStorage extends AbstractStorage {
    private Map<String, Resume> map = new HashMap<>();

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {

    }

    @Override
    protected boolean isExist(Object searchKey) {
        return false;
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {

    }

    @Override
    protected Resume doGet(Object searchKey) {
        return null;
    }

    @Override
    protected void doDelete(Object searchKey) {

    }

    @Override
    public void clear() {

    }

    @Override
    protected List<Resume> doCopyAll() {
        return Collections.emptyList();
    }

    @Override
    public int size() {
        return 0;
    }
}
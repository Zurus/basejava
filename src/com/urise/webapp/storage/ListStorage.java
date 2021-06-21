package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

/**
 * ListStorage.
 *
 * @author ADivaev
 */
public class ListStorage extends AbstractStorage {
    private final List<Resume> list = new ArrayList<>();

    @Override
    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        list.set((Integer)searchKey, r);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        list.add(r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return list.get((Integer) searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        int idx = ((Integer) searchKey).intValue();
        list.remove(idx);
    }

    @Override
    public void clear() {
        list.clear();
    }

//    @Override
//    public Resume[] getAll() {
//        return list.toArray(new Resume[list.size()]);
//    }


    @Override
    public List<Resume> getAllSorted() {
        return list;
    }

    @Override
    public int size() {
        return list.size();
    }
}
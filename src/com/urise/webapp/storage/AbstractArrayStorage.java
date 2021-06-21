package com.urise.webapp.storage;

import java.util.Arrays;
import java.util.List;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage  extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected static final int NOT_FOUNT_IDX = -1;
    protected int size = 0;


    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void doUpdate(Resume r, Object index) {
        storage[(Integer) index] = r;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    public void doSave(Resume r, Object index) {
         if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            insertElement(r,(Integer)index);
            size++;
        }
    }

    @Override
    public void doDelete(Object index) {
        fillDeletedElement((Integer)index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public Resume doGet(Object index) {
        return storage[(Integer) index];
    }

    @Override
    protected boolean isExist(Object index) {
        return (Integer) index >= 0;
    }

    protected abstract void fillDeletedElement(int index);

    protected abstract void insertElement(Resume r, int index);

    //Получаем индекс нужного элемента
    protected abstract Integer getSearchKey(String uuid);
}

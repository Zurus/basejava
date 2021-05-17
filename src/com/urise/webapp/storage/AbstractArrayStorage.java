package com.urise.webapp.storage;

import java.util.Arrays;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected static final int NOT_FOUNT_IDX = -1;
    protected int size = 0;

    @Override
    public Resume get(String uuid) {
        int idx = getIdx(uuid);
        if (idx != NOT_FOUNT_IDX) {
            return storage[idx];
        }
        return null;
    }

    @Override
    public void clear() {
        Arrays.fill(storage,0,size,null);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public void update(Resume r) {
        update(r, getIdx(r.getUuid()));
    }

    protected int getIdx(Resume r) {
        return getIdx(r.getUuid());
    }

    protected void update(Resume r, int idx) {
        if (idx != NOT_FOUNT_IDX) {
            storage[idx] = r;
            System.out.println(String.format("Резюме %s обновлено!", r));
        }
    }

    //Получаем индекс нужного элемента
    protected abstract int getIdx(String uuid);
}

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

    protected void update(Resume r, int idx) {
        if (idx != NOT_FOUNT_IDX) {
            storage[idx] = r;
            System.out.println(String.format("Резюме %s обновлено!", r));
        }
    }

    protected int getIdx(Resume r) {
        return getIdx(r.getUuid());
    }


    @Override
    public Resume get(String uuid) {
        int idx = getIdx(uuid);
        if (idx != NOT_FOUNT_IDX) {
            return storage[idx];
        }
        return null;
    }


//    private void recombineArray(int idx) {
//        do {
//            storage[idx] = storage[idx + 1];
//            idx++;
//        } while (storage[idx] != null);
//    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    @Override
    public int size() {
        return size;
    }

    //Получаем индекс нужного элемента
    protected abstract int getIdx(String uuid);
}

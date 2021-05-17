package com.urise.webapp.storage;

import java.util.Arrays;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            System.out.println(String.format("Резюме %s не записано, массив переполнен!", r.getUuid()));
            return;
        }
        //Проверим, есть ли элемент в массиве!
        int idx = getIdx(r.getUuid());
        if (idx == NOT_FOUNT_IDX) {
            storage[size++] = r;
            System.out.println(String.format("Резюме %s сохранено!", r.getUuid()));
        } else {
            update(r, idx);
            System.out.println(String.format("Резюме %s обновлено!", r.getUuid()));
        }
    }

    @Override
    public void delete(String uuid) {
        int idx = getIdx(uuid);
        if (idx != NOT_FOUNT_IDX) {
            storage[idx] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    @Override
    protected int getIdx(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        System.out.println(String.format("Резюме %s не найдено!", uuid));
        return NOT_FOUNT_IDX;
    }
}

package com.urise.webapp.storage;

import java.util.Arrays;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            System.out.println(String.format("Резюме %s не записано, массив переполнен!", r.getUuid()));
            return;
        }
        int idx = getIdx(r.getUuid());
        if (idx > 0) {
            update(r, idx);
            System.out.println(String.format("Резюме %s обновлено!", r.getUuid()));
        } else {
            idx = getCorrectIdx(idx);
            moveArrayRight(idx);
            storage[idx] = r;
            size++;
        }
    }

    @Override
    public void delete(String uuid) {
        int idx = getIdx(uuid);
        if (idx != NOT_FOUNT_IDX) {
            storage[idx] = null;
            moveArrayLeft(idx);
            size--;
        }
    }

    @Override
    protected int getIdx(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    private void moveArrayLeft(int idx) {
        do {
            storage[idx] = storage[idx + 1];
            idx++;
        } while (storage[idx] != null);
    }

    private void moveArrayRight(int idx) {
        int currentIdx = size;
        while (currentIdx > idx && currentIdx > 0) {
            storage[currentIdx] = storage[currentIdx - 1];
            currentIdx--;
        }
    }

    private int getCorrectIdx(int idx) {
        return (idx + 1) * -1;
    }
}

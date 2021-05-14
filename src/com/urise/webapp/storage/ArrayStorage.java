package com.urise.webapp.storage;

import java.util.Arrays;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private static final int NOT_FOUNT_IDX = -1;
    private int size = 0;

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void update(Resume r) {
        int idx = getIdx(r.getUuid());
        if (idx != NOT_FOUNT_IDX) {
            storage[idx] = r;
            System.out.println(String.format("Резюме %s обновлено!", r));
        }
    }

    public void save(Resume r) {
        if (size == storage.length) {
            System.out.println(String.format("Резюме %s не записано, массив переполнен!", r.getUuid()));
            return;
        }
        //Проверим, есть ли элемент в массиве!
        int idx = getIdx(r.getUuid());
        if (idx == NOT_FOUNT_IDX) {
            storage[size] = r;
            size++;
            System.out.println(String.format("Резюме %s сохранено!", r.getUuid()));
        } else {
            System.out.println(String.format("Резюме %s уже есть в массиве!", r.getUuid()));
        }
    }

    //Получаем индекс нужного элемента
    private int getIdx(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        System.out.println(String.format("Резюме %s не найдено!", uuid));
        return NOT_FOUNT_IDX;
    }

    public Resume get(String uuid) {
        int idx = getIdx(uuid);
        if (idx != NOT_FOUNT_IDX) {
            return storage[idx];
        }
        return null;
    }

    public void delete(String uuid) {
        int idx = getIdx(uuid);
        if (idx != NOT_FOUNT_IDX) {
            storage[idx] = storage[size - 1];
            storage[size - 1] = null;
            //recombineArray(idx);
            size--;
        }
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
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}

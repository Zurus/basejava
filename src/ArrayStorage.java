import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private static final int NOT_FOUNT_IDX = -1;
    private int size = 0;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume r) {
        if (size == storage.length) {
            System.out.println(String.format("Резюме %s не записано, массив переполнен!", r.uuid));
            return;
        }
        //Проверим, есть ли элемент в массиве!
        int idx = getIdx(r.uuid);
        if (idx == NOT_FOUNT_IDX) {
            storage[size] = r;
            size++;
            System.out.println(String.format("Резюме %s сохранено!", r.uuid));
        } else {
            System.out.println(String.format("Резюме %s уже есть в массиве!", r.uuid));
        }
    }

    //Получаем индекс нужного элемента
    private int getIdx(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return i;
            }
        }
        return NOT_FOUNT_IDX;
    }

    Resume get(String uuid) {
        int idx = getIdx(uuid);
        if (idx != NOT_FOUNT_IDX) {
            return storage[idx];
        }
        return null;
    }

    void delete(String uuid) {
        int idx = getIdx(uuid);
        if (idx != NOT_FOUNT_IDX) {
            storage[idx] = null;
            recombineArray(idx);
            size--;
        }
    }

    private void recombineArray(int idx) {
        do {
            storage[idx] = storage[idx + 1];
            idx++;
        } while (storage[idx] != null);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    int size() {
        return size;
    }
}

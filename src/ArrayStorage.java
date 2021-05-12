import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private static final int NOT_FOUNT_IDX = -1;
    private int size = 0;

    void clear() {
        for (int i = 0; i < size ; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume r) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                size++;
                System.out.println(String.format("Резюме %s сохранено!", r.uuid));
                return;
            } else if(storage[i].equals(r)) {
                System.out.println(String.format("Резюме %s уже есть в массиве!", r.uuid));
                return;
            }
        }
        System.out.println(String.format("Резюме %s не записано, массив переполнен!", r.uuid));
    }

    //Получаем индекс нужного элемента
    private int getIdx(String uuid) {
        //Ничего не найдено, результат -1
        int result = NOT_FOUNT_IDX;
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)){
                result = i;
                break;
            }
        }
        return result;
    }

    //
    private void recombineArray(int idx) {
        do {
            storage[idx] = storage[idx+1];
            idx++;
        } while (storage[idx] != null);
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

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOfRange(storage,0, size);
    }

    int size() {
        return size;
    }
}

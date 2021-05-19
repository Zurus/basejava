package com.urise.webapp.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = new Resume(UUID_1);

    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = new Resume(UUID_2);

    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = new Resume(UUID_3);

    private static final String UUID_4 = "uuid3";
    private static final Resume RESUME_4 = new Resume(UUID_4);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_1);
        storage.update(newResume);
        assertTrue(newResume.equals(storage.get(UUID_1)));
    }

    @Test
    public void getAll() {
        Resume[] expectedArray = {new Resume(UUID_1),
                new Resume(UUID_2), new Resume(UUID_3)};
        Assert.assertTrue(Arrays.equals(expectedArray, storage.getAll()));
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(RESUME_1);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        try {
            for (int i = 3; i < AbstractArrayStorage.STORAGE_LIMIT ; i++) {
                storage.save(new Resume());
            }
            //System.out.println(String.format("Storage size = %d", storage.size()));
        } catch (StorageException e) {
            Assert.fail();
        }
        storage.save(new Resume());
    }


//    @Test(expected = NotExistStorageException.class)
//    public void delete() {
//        storage.delete(UUID_1);
//        assertSize(2);
//        storage.get(UUID_1);
//    }

    @Test
    public void delete() {
        storage.delete(UUID_3);
        Resume[] expectedArray = {new Resume(UUID_1),
                new Resume(UUID_2)};
        Assert.assertTrue(Arrays.equals(expectedArray, storage.getAll()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        String notExistUUID = "dummy";
        storage.get(notExistUUID);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }
}
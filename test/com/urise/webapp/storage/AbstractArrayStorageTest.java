package com.urise.webapp.storage;

import java.util.Arrays;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class AbstractArrayStorageTest {
    protected static Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        storage.update(new Resume(UUID_1));
    }

    @Test
    public void getAll() {
        Resume[] expectedArray = {new Resume(UUID_1),
                new Resume(UUID_2), new Resume(UUID_3)};
        Assert.assertTrue(Arrays.equals(expectedArray, storage.getAll()));
    }

    @Test
    public void save() {
        Resume newResume = new Resume("uuid4");
        storage.save(newResume);
        Assert.assertEquals(4, storage.size());
    }

    @Test
    public void delete() {
        storage.delete(UUID_3);
        Resume[] expectedArray = {new Resume(UUID_1),
                new Resume(UUID_2)};
        Assert.assertTrue(Arrays.equals(expectedArray, storage.getAll()));
    }

    @Test
    public void get() {
        Assert.assertEquals(new Resume(UUID_1), storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        String notExistUUID = "dummy";
        storage.get(notExistUUID);
    }
}
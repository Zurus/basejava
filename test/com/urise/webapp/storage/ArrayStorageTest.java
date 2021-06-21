package com.urise.webapp.storage;


import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public class ArrayStorageTest extends AbstractStorageTest {

    public ArrayStorageTest() {
        super(new ArrayStorage());
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
}
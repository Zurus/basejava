package com.urise.webapp.storage;

import com.Configurator;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.urise.webapp.storage.Model.TestData.R1;
import static com.urise.webapp.storage.Model.TestData.R2;
import static com.urise.webapp.storage.Model.TestData.R3;
import static com.urise.webapp.storage.Model.TestData.R4;
import static com.urise.webapp.storage.Model.TestData.UUID_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class AbstractStorageTest {

    protected static final String PATHNAME = Configurator.get().getStorageDir().getAbsolutePath();
    //protected static final String PATHNAME_REL = ".\\";
    protected static final File STORAGE_DIR = Configurator.get().getStorageDir();

    protected Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
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
        Resume newResume = new Resume(UUID_1, "New Name");
        R1.addContact(ContactType.MAIL, "mail1@google.com");
        R1.addContact(ContactType.SKYPE, "NewSkype");
        R1.addContact(ContactType.MOBILE, "+7 921 222-22-22");
        storage.update(newResume);
        Resume o = storage.get(UUID_1);
        assertTrue(newResume.equals(o));
    }

    @Test
    public void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        assertEquals(list, new ArrayList<>(Arrays.asList(R1, R2, R3)));
    }

    @Test
    public void save() {
        storage.save(R4);
        assertSize(4);
        assertGet(R4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(R1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

//    @Test
//    public void delete() {
//        storage.delete(UUID_3);
//        Resume[] expectedArray = {new Resume(UUID_1),
//                new Resume(UUID_2)};
//        Assert.assertTrue(Arrays.equals(expectedArray, storage.getAll()));
//    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        String notExistUUID = "dummy";
        storage.get(notExistUUID);
    }

    @Test
    public void get() {
        assertGet(R1);
        assertGet(R2);
        assertGet(R3);
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }
}
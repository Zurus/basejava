package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.util.List;

public class ObjectStreamStorage extends AbstractFileStorage {
    public ObjectStreamStorage(File directory) {
        super(directory);
    }

    @Override
    protected void doWrite(Resume r, OutputStream file) throws IOException {
        try(ObjectOutputStream oos = new ObjectOutputStream(file)) {
            oos.writeObject(r);
        }
    }

    @Override
    protected Resume doRead(InputStream is) throws IOException {

        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();

        }catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}

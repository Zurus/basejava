/*
 * @author DivaevAM
 * @since 13.08.2021
 */

package com.urise.webapp.storage.serializer;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;

public class ObjectSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream file) throws IOException {
        try(ObjectOutputStream oos = new ObjectOutputStream(file)) {
            oos.writeObject(r);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        }catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}

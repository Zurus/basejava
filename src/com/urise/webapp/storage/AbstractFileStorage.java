package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractFileStorage extends AbstractStorage<File>{
    private File directory;

    public AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory,"directory must not be null!");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doUpdate(Resume r, File searchKey) {

    }

    @Override
    protected boolean isExist(File searchKey) {
        return searchKey.exists();
    }

    @Override
    protected void doSave(Resume r, File searchKey) {
        try {
            searchKey.createNewFile();
            doWrite(r, searchKey);
        } catch (IOException e) {
            throw new StorageException("IO error", searchKey.getName(), e);
        }
    }

    protected abstract void doWrite(Resume r, File file) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;

    @Override
    protected Resume doGet(File searchKey) {
        try {
        if (!searchKey.exists()) {
            throw new StorageException("File not found Exception!", searchKey.getName());
        }
            return doRead(searchKey);
        } catch (IOException e) {
            throw new StorageException("IOException!", searchKey.getName(), e);
        }
    }

    @Override
    protected void doDelete(File searchKey) {
        searchKey.delete();
    }

    @Override
    protected List<Resume> doCopyAll() {
        File[] files = directory.listFiles();
        return Arrays.stream(files).map(f -> doGet(f)).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        Arrays.stream(directory.listFiles()).forEach(this::doDelete);
    }

    @Override
    public int size() {
        return directory.listFiles().length;
    }
}

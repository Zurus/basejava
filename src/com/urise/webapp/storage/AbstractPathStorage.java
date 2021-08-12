package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;

    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        String[] list = null;
        try {
            list = (String[]) Files.list(directory).map(Path::toAbsolutePath).collect(Collectors.toList()).toArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (list == null) {
            throw new StorageException("Directory read error", null);
        }
        return list.length;
    }

    @Override
    protected Path getSearchKey(String uuid) {
        //return new Path(directory, uuid);
        return Paths.get(uuid);
    }

    @Override
    protected void doUpdate(Resume r, Path Path) {
        try {
            doWrite(r, new BufferedOutputStream(Files.newOutputStream(Path)));
        } catch (IOException e) {
            throw new StorageException("Path write error", r.getUuid(), e);
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void doSave(Resume r, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create Path " + path.toAbsolutePath().toString(), path.getFileName().toString(), e);
        }
        doUpdate(r, path);
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", path.toAbsolutePath().toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", path.toAbsolutePath().toString());
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        Path[] paths = new Path[0];
        try {
            paths = (Path[]) Files.list(directory).collect(Collectors.toList()).toArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (paths == null) {
            throw new StorageException("Directory read error", null);
        }
        List<Resume> list = new ArrayList<>(paths.length);
        for (Path Path : paths) {
            list.add(doGet(Path));
        }
        return list;
    }
}

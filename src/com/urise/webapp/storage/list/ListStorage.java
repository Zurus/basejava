package com.urise.webapp.storage.list;

import java.util.ArrayList;
import java.util.List;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.AbstractStorage;

/**
 * ListStorage.
 *
 * @author ADivaev
 */
public class ListStorage extends AbstractStorage {
    private final List<Resume> resumeList = new ArrayList<>();

    @Override
    public void clear() {
        resumeList.clear();
    }

    @Override
    public void update(Resume resume) {
        int idx = resumeList.indexOf(resume);
        if (idx < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            resumeList.remove(idx);
            resumeList.add(idx, resume);
        }
    }

    @Override
    public void save(Resume r) {
        int idx = getIdx(r.getUuid());
        if (idx >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else {
            resumeList.add(r);
        }
    }

    @Override
    public Resume get(String uuid) {
        return resumeList.get(getIdx(uuid));
    }

    @Override
    public void delete(String uuid) {
        int idx = getIdx(uuid);
        if (idx < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            resumeList.remove(getIdx(uuid));
        }
    }

    @Override
    public Resume[] getAll() {
        return resumeList.toArray(Resume[]::new);
    }

    @Override
    protected int getIdx(String uuid) {
        return resumeList.indexOf(new Resume(uuid));
    }

    @Override
    public int size() {
        return resumeList.size();
    }
}
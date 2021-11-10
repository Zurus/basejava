package com.urise.webapp.exception;

/**
 * ExistStorageException.
 *
 * @author ADivaev
 */
public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("Resume " + uuid + "already exist", uuid);
    }
}
package com.urise.webapp.exception;

/**
 * NotExistStorageException.
 *
 * @author ADivaev
 */
public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("Resume " + uuid + " not exist", uuid);
    }
}
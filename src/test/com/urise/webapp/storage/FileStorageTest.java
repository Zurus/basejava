/*
 * @author DivaevAM
 * @since 13.08.2021
 */

package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectSerializer;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectSerializer()));
    }
}
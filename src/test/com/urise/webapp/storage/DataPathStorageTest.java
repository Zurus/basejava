/*
 * @author DivaevAM
 * @since 21.09.2021
 */

/*
 * @author DivaevAM
 * @since 21.09.2021
 */

package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.DataStreamSerializer;

public class DataPathStorageTest extends AbstractStorageTest {

    public DataPathStorageTest () {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataStreamSerializer()));
    }
}
/*
 * @author DivaevAM
 * @since 13.08.2021
 */

package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StreamSerializer {

    void doWrite(Resume r, OutputStream os) throws IOException;

    Resume doRead(InputStream inputStream) throws IOException;
}

/*
 * @author DivaevAM
 * @since 13.08.2021
 */

package com.urise.webapp.resolver;

import com.urise.webapp.storage.AbstractIOStorage;
import com.urise.webapp.storage.ObjectStreamPathStorage;
import com.urise.webapp.storage.ObjectStreamStorage;

import java.io.File;

public class Resolver {

    private Resolver () {
    }
    public enum IO {
        FILE,
        PATH
    }

    public static AbstractIOStorage getStorage(String dir, IO way) {
        if (way == IO.PATH) {
            return new ObjectStreamPathStorage(dir);
        } else {
            return new ObjectStreamStorage(new File(dir));
        }
    }
}

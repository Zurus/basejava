/*
 * @author DivaevAM
 * @since 13.08.2021
 */

package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectSerializer;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class PathStorageTest extends AbstractStorageTest{
    public PathStorageTest() {
        super(new PathStorage(PATHNAME, new ObjectSerializer()));
    }


//    @Test
//    public void test() {
//        Path path = Paths.get(PATHNAME);
//        System.out.println(path.toString());
//        System.out.println(path.resolve("shitface.txt"));
//        System.out.println(path);
//    }
}
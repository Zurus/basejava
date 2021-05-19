package com.urise.webapp.storage;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Ignore;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {

    @BeforeClass
    public static void classInit() {
        storage = new SortedArrayStorage();
    }
}
package com.urise.webapp.storage;

import static org.junit.Assert.*;

import org.junit.BeforeClass;


public class ArrayStorageTest extends AbstractArrayStorageTest {

    @BeforeClass
    public static void classInit(){
       storage = new ArrayStorage();
    }

}
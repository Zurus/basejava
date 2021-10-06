/*
 * @author DivaevAM
 * @since 06.10.2021
 */

package com.urise.webapp.storage;

import com.Configurator;
import com.urise.webapp.util.SqlHelper;

import static org.junit.Assert.*;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(SqlHelper.getSqlStorage());
    }
}
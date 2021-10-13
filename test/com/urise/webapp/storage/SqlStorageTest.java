/*
 * @author DivaevAM
 * @since 06.10.2021
 */

package com.urise.webapp.storage;

import com.urise.webapp.sql.SqlHelper;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(SqlHelper.getSqlStorage());
    }
}
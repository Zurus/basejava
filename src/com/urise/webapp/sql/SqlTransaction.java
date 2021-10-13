/*
 * @author DivaevAM
 * @since 08.10.2021
 */

package com.urise.webapp.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface SqlTransaction <T> {
    T execute(Connection connection) throws SQLException;
}

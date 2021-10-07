
package com.urise.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlExecutorReturnedType<T> {
    <T> T execute(PreparedStatement preparedStatement) throws SQLException;
}

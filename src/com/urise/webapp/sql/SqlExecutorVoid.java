package com.urise.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlExecutorVoid {
    void execute(PreparedStatement ps) throws SQLException;
}

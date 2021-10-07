package com.urise.webapp.util;

import com.Configurator;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.SqlExecutorVoid;
import com.urise.webapp.storage.SqlStorage;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    private static final String URL_PROPERTIES = "db.url";
    private static final String USER_PROPERTIES = "db.user";
    private static final String PASSWORD_PROPERTIES = "db.password";

    private SqlHelper() {
    }

    private static String getUrl() {
        return Configurator.get().getProperty(URL_PROPERTIES);
    }

    private static String getUserName() {
        return Configurator.get().getProperty(USER_PROPERTIES);
    }

    private static String getPassword() {
        return Configurator.get().getProperty(PASSWORD_PROPERTIES);
    }

    public static SqlStorage getSqlStorage() {
        final String URL = SqlHelper.getUrl();
        final String USER = SqlHelper.getUserName();
        final String PASSWORD = SqlHelper.getPassword();
        return new SqlStorage(URL, USER, PASSWORD);
    }

    public static void prepareStatementExecQuery(ConnectionFactory connectionFactory, String sql, SqlExecutorVoid execute) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            execute.execute(ps);
        } catch (PSQLException e) {
            throw new ExistStorageException(e.getMessage());
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}

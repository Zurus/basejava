package com.urise.webapp.util;

import com.Configurator;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.storage.SqlStorage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        final String URL = Configurator.get().getProperty(SqlHelper.getUrl());
        final String USER = Configurator.get().getProperty(SqlHelper.getUserName());
        final String PASSWORD = Configurator.get().getProperty(SqlHelper.getPassword());
        return new SqlStorage(URL, USER, PASSWORD);
    }

    private interface ExecuteQuery {
        void execute(Connection connection) throws SQLException;
    }

    public void prepareStatementExecQuery(ExecuteQuery execute) {
        try (Connection conn = ConnectionFactoryInitializer.getINSTANCE().getConnection()) {
            execute.execute(conn);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    private static class ConnectionFactoryInitializer {
        volatile private static ConnectionFactory INSTANCE = () -> DriverManager.getConnection(getUrl(), getUserName(), getPassword());

        private ConnectionFactoryInitializer() {
        }

        public static ConnectionFactory getINSTANCE() {
            return INSTANCE;
        }
    }
}

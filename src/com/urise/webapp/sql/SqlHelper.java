package com.urise.webapp.sql;

import com.Configurator;
import com.urise.webapp.storage.SqlStorage;
import com.urise.webapp.util.ExceptionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public static SqlStorage getSqlStorage() {
        final String URL = Configurator.get().getUrl();
        final String USER = Configurator.get().getUserName();
        final String PASSWORD = Configurator.get().getPassword();

        return new SqlStorage(URL, USER, PASSWORD);
    }

    public void execute(String sql) {
        execute(sql, PreparedStatement::execute);
    }

    public <T> T execute(String sql, SqlExecutor<T> execute) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return execute.execute(ps);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

    public <T> T transactionalExecute(SqlTransaction<T> executor) {
        try (Connection connection = connectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                T res = executor.execute(connection);
                connection.commit();
                return res;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e ) {
            throw ExceptionUtil.convertException(e);
        }
    }
}

package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.util.SqlHelper;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        SqlHelper.prepareStatementExecQuery(connectionFactory, "DELETE FROM resume", PreparedStatement::execute);
//        try (Connection connection = connectionFactory.getConnection()) {
//
//            PreparedStatement statement = connection.prepareStatement("DELETE FROM resume");
//            statement.execute();
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
    }

    @Override
    public void update(Resume resume) {
        SqlHelper.prepareStatementExecQuery(connectionFactory, "UPDATE resume SET full_name = ? WHERE uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, resume.getFullName());
            preparedStatement.setString(2, resume.getUuid());
            preparedStatement.execute();
        });

//        try (Connection connection = connectionFactory.getConnection()) {
//            PreparedStatement statement = connection.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?");
//            statement.setString(1, resume.getFullName());
//            statement.setString(2, resume.getUuid());
//            statement.execute();
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
    }

    @Override
    public void save(Resume r) {
        SqlHelper.prepareStatementExecQuery(connectionFactory, "INSERT INTO resume (uuid, full_name) VALUES(?,?)", prepareStatement -> {
            prepareStatement.setString(1, r.getUuid());
            prepareStatement.setString(2, r.getFullName());
            prepareStatement.execute();
        });

//        try (Connection conn = connectionFactory.getConnection()) {
//            PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES(?,?)");
//            ps.setString(1, r.getUuid());
//            ps.setString(2, r.getFullName());
//            ps.execute();
//        } catch (PSQLException e) {
//            throw new ExistStorageException(r.getUuid());
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
    }

    @Override
    public Resume get(String uuid) {
        try (Connection conn = connectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r WHERE r.uuid = ?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void delete(String uuid) {
        SqlHelper.prepareStatementExecQuery(connectionFactory, "DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            ps.execute();
        });

//        try (Connection conn = connectionFactory.getConnection()) {
//            PreparedStatement statement = conn.prepareStatement("DELETE FROM resume WHERE uuid = ?");
//            statement.setString(1, uuid);
//            statement.execute();
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
    }

    @Override
    public List<Resume> getAllSorted() {
        try (Connection conn = connectionFactory.getConnection()) {
            Statement ps = conn.createStatement();
            ResultSet rs = ps.executeQuery("SELECT * FROM resume");
            List<Resume> resultList = new ArrayList<>();
            while (rs.next()) {
                resultList.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name").trim()));
            }
            return resultList;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public int size() {
        try (Connection conn = connectionFactory.getConnection()) {
            Statement ps = conn.createStatement();
            ResultSet rs = ps.executeQuery("SELECT count(*) as r_size FROM resume");
            rs.next();
            return rs.getInt("r_size");
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}

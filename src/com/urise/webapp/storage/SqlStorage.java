package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;

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
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE * FROM resumes");
            statement.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void update(Resume resume) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE resumes r SET r.full_name = ? WHERE r.uuid = ?");
            statement.setString(1,resume.getFullName());
            statement.setString(2,resume.getUuid());
            statement.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void save(Resume r) {
        try (Connection conn = connectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES(?,?)");
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
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
        try (Connection conn = connectionFactory.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM Products WHERE id = ?");
            statement.setString(1, uuid);
            statement.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        try (Connection conn = connectionFactory.getConnection()) {
            Statement ps = conn.createStatement();
            ResultSet rs = ps.executeQuery("SELECT * FROM resume");
            List<Resume> resultList = new ArrayList<>();
            while (rs.next()) {
                resultList.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
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
            ResultSet rs = ps.executeQuery("SELECT count(uuid) as r_size FROM resume r GROUP BY r.uuid");
            rs.next();
            return rs.getInt("r_size");
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}

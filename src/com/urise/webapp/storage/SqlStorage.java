package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.Section;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.sql.SqlHelper;
import com.urise.webapp.util.JsonParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.sql.DriverManager.getConnection;

public class SqlStorage implements Storage {

    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        sqlHelper = new SqlHelper(() -> getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
                ps.execute();
            }
            deleteContacts(connection, resume);
            deleteSections(connection, resume);
            insertContacts(connection, resume);
            insertSections(connection, resume);
            return null;
        });
    }

    public void deleteContacts(Connection conn, Resume resume) throws SQLException {
        deleteAttribute(conn, resume, "DELETE FROM contact WHERE resume_uuid = ?");
    }

    public void deleteSections(Connection conn, Resume resume) throws SQLException {
        deleteAttribute(conn, resume, "DELETE FROM section WHERE resume_uuid = ?");
    }

    public void deleteAttribute(Connection conn, Resume resume, String sql) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

    public void insertSections(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (id, resume_uuid, type, value) VALUES (nextval('serial'),?,?,?)")) {
            for (Map.Entry<SectionType, Section> e : resume.getSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                final SectionType section = e.getKey();
                ps.setString(2, section.name());
                ps.setString(3, JsonParser.write(e.getValue(), Section.class));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public void addSection(ResultSet rs, Resume resume) throws SQLException {
        String type = rs.getString("type");
        if (type != null) {
            SectionType sectionType = SectionType.valueOf(type);
            String content = rs.getString("value");
            resume.addSection(sectionType, JsonParser.read(content, Section.class));
        }
    }

    public void insertContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, r.getUuid());
                        ps.setString(2, r.getFullName());
                        ps.execute();
                    }
                    insertContacts(conn, r);
                    insertSections(conn, r);
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            Resume r;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume WHERE uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                r = new Resume(uuid, rs.getString("full_name"));
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContact(rs, r);
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(rs, r);
                }
            }
            return r;
        });

/*        return sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps =
                         connection.prepareStatement("SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid WHERE r.uuid = ?");
                 PreparedStatement preparedStatement =
                         connection.prepareStatement("SELECT * FROM section s WHERE s.resume_uuid = ?")
            ) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                final Resume resume = new Resume(uuid, rs.getString("full_name"));
                do {
                    addContact(rs, resume);
                } while (rs.next());

                preparedStatement.setString(1, uuid);
                ResultSet sectionResultSet = preparedStatement.executeQuery();
                while (sectionResultSet.next()) {
                    addSection(sectionResultSet, resume);
                }
                return resume;
            }
        });*/

//        return sqlHelper.execute("SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid WHERE r.uuid = ?", ps -> {
//            ps.setString(1, uuid);
//            ResultSet rs = ps.executeQuery();
//            if (!rs.next()) {
//                throw new NotExistStorageException(uuid);
//            }
//            final Resume resume = new Resume(uuid, rs.getString("full_name"));
//            do {
//                addContact(rs, resume);
//            } while (rs.next());
//            return resume;
//        });
    }

//    public void addContacts(Resume resume, ResultSet rs) throws SQLException {
//        do {
//            String value = rs.getString("value");
//            if (value != null) {
//                ContactType type = ContactType.valueOf(rs.getString("type"));
//                resume.addContact(type, value);
//            }
//        } while (rs.next());
//    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    //    @Override
//    public List<Resume> getAllSorted() {
//        return sqlHelper.execute("" +
//                "   SELECT * FROM resume r\n" +
//                "LEFT JOIN contact c ON r.uuid = c.resume_uuid\n" +
//                "ORDER BY full_name, uuid", ps -> {
//            ResultSet rs = ps.executeQuery();
//            Map<String, Resume> map = new LinkedHashMap<>();
//            while (rs.next()) {
//                String uuid = rs.getString("uuid");
//                Resume resume = map.get(uuid);
//                if (resume == null) {
//                    resume = new Resume(uuid, rs.getString("full_name"));
//                    map.put(uuid, resume);
//                }
//                addContact(rs, resume);
//            }
//            return new ArrayList<>(map.values());
//        });
//    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            r.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    resumes.put(uuid, new Resume(uuid, rs.getString("full_name")));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume r = resumes.get(rs.getString("resume_uuid"));
                    addContact(rs, r);
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume r = resumes.get(rs.getString("resume_uuid"));
                    addSection(rs, r);
                }
            }
            return new ArrayList<>(resumes.values());
        });
//        return sqlHelper.transactionalExecute(connection -> {
//                    List<Resume> resultList = new ArrayList<>();
//                    try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
//                        ResultSet rs = ps.executeQuery();
//                        while (rs.next()) {
//                            resultList.add(
//                                    new Resume(rs.getString("uuid"), rs.getString("full_name"))
//                            );
//                        }
//                    }
//                    for (Resume r : resultList) {
//                        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM contact WHERE resume_uuid = ?")) {
//                            ps.setString(1, r.getUuid());
//                            ResultSet rs = ps.executeQuery();
//                            while (rs.next()) {
//                                r.getContacts().put(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
//                            }
//                        }
//                        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM section WHERE resume_uuid = ?")) {
//                            ps.setString(1, r.getUuid());
//                            ResultSet rs = ps.executeQuery();
//                            while (rs.next()) {
//                                addSection(rs, r);
//                            }
//                        }
//                    }
//                    return resultList;
//                }
//        );
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) as r_size FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("r_size") : 0;
        });
    }
}

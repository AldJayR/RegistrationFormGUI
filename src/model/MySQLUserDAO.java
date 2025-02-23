package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLUserDAO implements UserDao {

    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    public MySQLUserDAO(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(createUserFromResultSet(rs));
            }
        }
        return users;
    }

    @Override
    public User getUserById(String id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createUserFromResultSet(rs);
                }
            }
        }
        return null;
    }

    @Override
    public User getUserByStudentNo(String studentNo) throws SQLException {
        String sql = "SELECT * FROM users WHERE student_number = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, studentNo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createUserFromResultSet(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<User> searchUsers(String lastName) throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE last_name = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lastName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(createUserFromResultSet(rs));
                }
            }
        }
        return users;
    }

    @Override
    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (student_number, last_name, first_name, middle_name, email, course, address, sex, contact_number, age, section) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
        try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getStudentNo());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getMiddleName());
            stmt.setString(5, user.getEmailAddress());
            stmt.setString(6, user.getCourse());
            stmt.setString(7, user.getAddress());
            stmt.setString(8, user.getSex());
            stmt.setString(9, user.getContactNo());
            stmt.setInt(10, user.getAge());
            stmt.setString(11, user.getSection());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            // Get the generated ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getString(1)); // Set the ID on the User object
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET student_number = ?, last_name = ?, first_name = ?, middle_name = ?, email = ?, "
                + "course = ?, address = ?, sex = ?, contact_number = ?, age = ?, section = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getStudentNo());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getMiddleName());
            stmt.setString(5, user.getEmailAddress());
            stmt.setString(6, user.getCourse());
            stmt.setString(7, user.getAddress());
            stmt.setString(8, user.getSex());
            stmt.setString(9, user.getContactNo());
            stmt.setInt(10, user.getAge());
            stmt.setString(11, user.getSection());
            stmt.setString(12, user.getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteUser(String id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }

    private User createUserFromResultSet(ResultSet rs) throws SQLException {
        return new User(
                rs.getString("id"),
                rs.getString("student_number"),
                rs.getString("last_name"),
                rs.getString("first_name"),
                rs.getString("middle_name"),
                rs.getString("email"),
                rs.getString("course"),
                rs.getString("address"),
                rs.getString("sex"),
                rs.getString("contact_number"),
                rs.getInt("age"),
                rs.getString("section")
        );
    }

    @Override
    public int getNextUserCounter() throws SQLException {
        String sql = "SELECT ctr FROM user_ctr";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("ctr");
            } else {
                // Initialize the counter if it doesn't exist
                String insertSql = "INSERT INTO user_ctr (ctr) VALUES (1)";
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.executeUpdate();
                }
                return 1;
            }
        }
    }

    @Override
    public void updateNextUserCounter(int nextCounter) throws SQLException {
        String sql = "UPDATE user_ctr SET ctr = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nextCounter);
            stmt.executeUpdate();
        }
    }
}

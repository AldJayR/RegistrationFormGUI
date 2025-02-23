package model;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    List<User> getAllUsers() throws SQLException;

    User getUserById(String id) throws SQLException;

    User getUserByStudentNo(String studentNo) throws SQLException;

    void addUser(User user) throws SQLException;

    void updateUser(User user) throws SQLException;

    void deleteUser(String id) throws SQLException;

    List<User> searchUsers(String lastName) throws SQLException;

    int getNextUserCounter() throws SQLException;

    void updateNextUserCounter(int nextCounter) throws SQLException;
}

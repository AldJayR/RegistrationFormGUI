package controller;

import model.User;
import model.UserDao;
import view.RegistrationForm;

import java.sql.SQLException;
import java.time.Year;
import java.util.List;

public class RegistrationController {
    private final UserDao userDao;
    private final RegistrationForm view;

    public RegistrationController(UserDao userDao, RegistrationForm view) {
        this.userDao = userDao;
        this.view = view;
        initController();
    }

    private void initController(){
        view.setController(this);
    }

    public void registerUser(User user) {
        try {
            userDao.addUser(user);
            view.showMessage("Registration Successful!");
            view.clearForm();
            view.loadTableData();
            view.disableControls();
        } catch (SQLException e) {
            view.showError("Error registering user: " + e.getMessage());
        }
    }

    public void updateUser(User user) {
        try {
            userDao.updateUser(user);
            view.showMessage("User updated successfully!");
            view.clearForm();
            view.loadTableData();
            view.disableControls();
            view.setSaveButtonText("Save"); // Reset button text
        } catch (SQLException e) {
            view.showError("Error updating user: " + e.getMessage());
        }
    }

    public void deleteUser(String userId) { // Takes the user ID
        try {
            userDao.deleteUser(userId);
            view.showMessage("User deleted successfully!");
            view.clearForm();
            view.loadTableData();
            view.disableControls();
        } catch (SQLException e) {
            view.showError("Error deleting user: " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        try {
            return userDao.getAllUsers();
        } catch (SQLException e) {
            view.showError("Error fetching users: " + e.getMessage());
            return List.of(); // Use List.of() for an empty immutable list
        }
    }

    public List<User> searchUsers(String lastName) {
        try {
            List<User> users = userDao.searchUsers(lastName);
            if (users.isEmpty()) {
                view.showMessage("No users found with last name: " + lastName);
            }
            return users;
        } catch (SQLException e) {
            view.showError("Error searching users: " + e.getMessage());
            return List.of();
        }
    }

    // Changed: generateNextStudentNo, not generateNextUserId
    public String generateNextStudentNo() {
        try {
            int nextCounter = userDao.getNextUserCounter();
            int currentYear = Year.now().getValue();
            int formattedNumber = 1000 + nextCounter;
            String nextStudentNo = currentYear + "-" + formattedNumber;

            // Increment the counter in the database for the *next* user
            userDao.updateNextUserCounter(nextCounter + 1);
            return nextStudentNo;
        } catch (SQLException e) {
            view.showError("Error generating student number: " + e.getMessage());
            return null; // Or handle the error appropriately
        }
    }
    
    public User getUserByStudentNo(String studentNo) {
        try {
            return userDao.getUserByStudentNo(studentNo);
        } catch (SQLException e) {
            view.showError("Error retrieving user: " + e.getMessage());
            return null; // Or throw a custom exception
        }
    }
}
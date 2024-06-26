package moneyexchange;

import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    private Connection conn;

    public Database() {
        // connection to dataBase
        String url = "jdbc:sqlite:/Users/ali/Main/Documents/Source/Money-Exchange/DataUserTest/identifier.sqlite";

        try {
            conn = DriverManager.getConnection(url);
            // create users table if it does not have
            createUsersTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "    id INTEGER PRIMARY KEY," +
                "    username TEXT," +
                "    firstName TEXT," +
                "    lastName TEXT," +
                "    Age TEXT," +
                "    password TEXT," +
                "    email TEXT," +
                "    phoneNumber TEXT" +
                ");";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addUser(String username, String firstName, String lastName, String Age, String password, String email, String phoneNumber) {
        String sql = "INSERT INTO users(username, firstName, lastName, Age, password, email, phoneNumber) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, Age);
            pstmt.setString(5, password);
            pstmt.setString(6, email);
            pstmt.setString(7, phoneNumber);
            pstmt.executeUpdate();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean validateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            boolean isValid = rs.next();
            pstmt.close();
            return isValid;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void getUserInfo(String username, TextField firstNameField, TextField lastNameField, TextField AgeField,
                            TextField phoneNumberField, TextField emailField) {

        String sql = "SELECT firstName, lastName, Age, phoneNumber, email FROM users WHERE username = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String Age = rs.getString("Age");
                String phoneNumber = rs.getString("phoneNumber");
                String email = rs.getString("email");


                LoginSignupPage.firstNameProf = firstName;
                LoginSignupPage.lastNameProf = lastName;
                LoginSignupPage.AgeProf = Age;
                LoginSignupPage.phoneNumberProf = phoneNumber;
                LoginSignupPage.emailProf = email;

                firstNameField.setText(firstName);
                lastNameField.setText(lastName);
                AgeField.setText(Age);
                phoneNumberField.setText(phoneNumber);
                emailField.setText(email);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public String getUserEmail(String username) {
        String sql = "SELECT email FROM users WHERE username = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updatePassword(String username, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE username = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateFirstName(String username, String newFirstName) {
        String sql = "UPDATE users SET firstName = ? WHERE username = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newFirstName);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateLastName(String username, String newLastName) {
        String sql = "UPDATE users SET lastName = ? WHERE username = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newLastName);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAge(String username, String newAge) {
        String sql = "UPDATE users SET Age = ? WHERE username = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newAge);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePhoneNumber(String username, String newPhoneNumber) {
        String sql = "UPDATE users SET phoneNumber = ? WHERE username = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newPhoneNumber);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmail(String username, String newEmail) {
        String sql = "UPDATE users SET email = ? WHERE username = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newEmail);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isUsernameAvailable(String inputUsername) {
        String sql = "SELECT username FROM users WHERE username = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, inputUsername);
            ResultSet rs = pstmt.executeQuery();
            boolean isAvailable = !rs.next();
            rs.close();
            pstmt.close();
            return isAvailable;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

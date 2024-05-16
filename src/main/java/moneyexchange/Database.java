package moneyexchange;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    private Connection conn;

    public Database() {
        // connection to dataBase
        String url = "jdbc:sqlite:/Users/ali/Main/Documents/Source/Money-Exchange/Money-Exchange-DataBase/identifier.sqlite";

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

    public boolean addUser(String username, String password, String email, String phoneNumber) {
        String sql = "INSERT INTO users(username, password, email, phoneNumber) VALUES(?, ?, ?, ?)";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            pstmt.setString(4, phoneNumber);
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
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

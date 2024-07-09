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
        }
        catch (SQLException e) {
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
                "    phoneNumber TEXT," +
                "    USD TEXT," +
                "    EUR TEXT," +
                "    TOMAN TEXT," +
                "    YEN TEXT," +
                "    GBP TEXT" +
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
        String sql = "INSERT INTO users(username, firstName, lastName, Age, password, email, phoneNumber, USD, EUR, TOMAN, YEN, GBP) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, Age);
            pstmt.setString(5, password);
            pstmt.setString(6, email);
            pstmt.setString(7, phoneNumber);
            pstmt.setString(8, "0"); // USD
            pstmt.setString(9, "0"); // EUR
            pstmt.setString(10, "0"); // TOMAN
            pstmt.setString(11, "0"); // YEN
            pstmt.setString(12, "0"); // GBP
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


    public boolean hasUSD(String username, double amount) {
        String sql = "SELECT USD FROM users WHERE username = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                double usd = Double.parseDouble(rs.getString("USD"));
                return usd >= amount;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean hasEUR(String username, double amount) {
        String sql = "SELECT EUR FROM users WHERE username = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                double eur = Double.parseDouble(rs.getString("EUR"));
                return eur >= amount;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean hasTOMAN(String username, double amount) {
        String sql = "SELECT TOMAN FROM users WHERE username = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                double toman = Double.parseDouble(rs.getString("TOMAN"));
                return toman >= amount;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean hasYEN(String username, double amount) {
        String sql = "SELECT YEN FROM users WHERE username = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                double yen = Double.parseDouble(rs.getString("YEN"));
                return yen >= amount;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean hasGBP(String username, double amount) {
        String sql = "SELECT GBP FROM users WHERE username = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                double gbp = Double.parseDouble(rs.getString("GBP"));
                return gbp >= amount;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public double updateUSD(String username, double amount) {
        String sqlSelect = "SELECT USD FROM users WHERE username = ?";
        String sqlUpdate = "UPDATE users SET USD = ? WHERE username = ?";

        try {
            PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
            pstmtSelect.setString(1, username);
            ResultSet rs = pstmtSelect.executeQuery();
            if (rs.next()) {
                double currentUSD = Double.parseDouble(rs.getString("USD"));
                double newUSD = currentUSD + amount;


                PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate);
                pstmtUpdate.setString(1, Double.toString(newUSD));
                pstmtUpdate.setString(2, username);
                pstmtUpdate.executeUpdate();

                pstmtSelect.close();
                pstmtUpdate.close();
                return newUSD;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public double updateEUR(String username, double amount) {
        String sqlSelect = "SELECT EUR FROM users WHERE username = ?";
        String sqlUpdate = "UPDATE users SET EUR = ? WHERE username = ?";

        try {
            PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
            pstmtSelect.setString(1, username);
            ResultSet rs = pstmtSelect.executeQuery();
            if (rs.next()) {
                double currentEUR = Double.parseDouble(rs.getString("EUR"));
                double newEUR = currentEUR + amount;


                PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate);
                pstmtUpdate.setString(1, Double.toString(newEUR));
                pstmtUpdate.setString(2, username);
                pstmtUpdate.executeUpdate();

                pstmtSelect.close();
                pstmtUpdate.close();
                return newEUR;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public double updateTOMAN(String username, double amount) {
        String sqlSelect = "SELECT TOMAN FROM users WHERE username = ?";
        String sqlUpdate = "UPDATE users SET TOMAN = ? WHERE username = ?";

        try {
            PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
            pstmtSelect.setString(1, username);
            ResultSet rs = pstmtSelect.executeQuery();
            if (rs.next()) {
                double currentTOMAN = Double.parseDouble(rs.getString("TOMAN"));
                double newTOMAN = currentTOMAN + amount;


                PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate);
                pstmtUpdate.setString(1, Double.toString(newTOMAN));
                pstmtUpdate.setString(2, username);
                pstmtUpdate.executeUpdate();

                pstmtSelect.close();
                pstmtUpdate.close();
                return newTOMAN;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public double updateYEN(String username, double amount) {
        String sqlSelect = "SELECT YEN FROM users WHERE username = ?";
        String sqlUpdate = "UPDATE users SET YEN = ? WHERE username = ?";

        try {
            PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
            pstmtSelect.setString(1, username);
            ResultSet rs = pstmtSelect.executeQuery();
            if (rs.next()) {
                double currentYEN = Double.parseDouble(rs.getString("YEN"));
                double newYEN = currentYEN + amount;


                PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate);
                pstmtUpdate.setString(1, Double.toString(newYEN));
                pstmtUpdate.setString(2, username);
                pstmtUpdate.executeUpdate();

                pstmtSelect.close();
                pstmtUpdate.close();
                return newYEN;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public double updateGBP(String username, double amount) {
        String sqlSelect = "SELECT GBP FROM users WHERE username = ?";
        String sqlUpdate = "UPDATE users SET GBP = ? WHERE username = ?";

        try {
            PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
            pstmtSelect.setString(1, username);
            ResultSet rs = pstmtSelect.executeQuery();
            if (rs.next()) {
                double currentGBP = Double.parseDouble(rs.getString("GBP"));
                double newGBP = currentGBP + amount;


                PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate);
                pstmtUpdate.setString(1, Double.toString(newGBP));
                pstmtUpdate.setString(2, username);
                pstmtUpdate.executeUpdate();

                pstmtSelect.close();
                pstmtUpdate.close();
                return newGBP;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public void EmbezzlementUSD(String username) {
        String sqlSum = "SELECT SUM(CAST(USD AS DOUBLE)) AS totalUSD FROM users";
        String sqlReset = "UPDATE users SET USD = '0'";
        String sqlUpdateUser = "UPDATE users SET USD = ? WHERE username = ?";

        try {
            // calculate sum of all USD users
            PreparedStatement pstmtSum = conn.prepareStatement(sqlSum);
            ResultSet rs = pstmtSum.executeQuery();
            double totalUSD = 0;
            if (rs.next()) {
                totalUSD = rs.getDouble("totalUSD");
            }
            rs.close();
            pstmtSum.close();

            // change USD of all users to zero
            PreparedStatement pstmtReset = conn.prepareStatement(sqlReset);
            pstmtReset.executeUpdate();
            pstmtReset.close();

            // add sum of USD to special username
            PreparedStatement pstmtUpdateUser = conn.prepareStatement(sqlUpdateUser);
            pstmtUpdateUser.setString(1, Double.toString(totalUSD));
            pstmtUpdateUser.setString(2, username);
            pstmtUpdateUser.executeUpdate();
            pstmtUpdateUser.close();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void EmbezzlementEUR(String username) {
        String sqlSum = "SELECT SUM(CAST(EUR AS DOUBLE)) AS totalEUR FROM users";
        String sqlReset = "UPDATE users SET EUR = '0'";
        String sqlUpdateUser = "UPDATE users SET EUR = ? WHERE username = ?";

        try {
            // calculate sum of all EUR users
            PreparedStatement pstmtSum = conn.prepareStatement(sqlSum);
            ResultSet rs = pstmtSum.executeQuery();
            double totalEUR = 0;
            if (rs.next()) {
                totalEUR = rs.getDouble("totalEUR");
            }
            rs.close();
            pstmtSum.close();

            // change EUR of all users to zero
            PreparedStatement pstmtReset = conn.prepareStatement(sqlReset);
            pstmtReset.executeUpdate();
            pstmtReset.close();

            // add sum of EUR to special username
            PreparedStatement pstmtUpdateUser = conn.prepareStatement(sqlUpdateUser);
            pstmtUpdateUser.setString(1, Double.toString(totalEUR));
            pstmtUpdateUser.setString(2, username);
            pstmtUpdateUser.executeUpdate();
            pstmtUpdateUser.close();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void EmbezzlementTOMAN(String username) {
        String sqlSum = "SELECT SUM(CAST(TOMAN AS DOUBLE)) AS totalTOMAN FROM users";
        String sqlReset = "UPDATE users SET TOMAN = '0'";
        String sqlUpdateUser = "UPDATE users SET TOMAN = ? WHERE username = ?";

        try {
            // calculate sum of all TOMAN users
            PreparedStatement pstmtSum = conn.prepareStatement(sqlSum);
            ResultSet rs = pstmtSum.executeQuery();
            double totalTOMAN = 0;
            if (rs.next()) {
                totalTOMAN = rs.getDouble("totalTOMAN");
            }
            rs.close();
            pstmtSum.close();

            // change TOMAN of all users to zero
            PreparedStatement pstmtReset = conn.prepareStatement(sqlReset);
            pstmtReset.executeUpdate();
            pstmtReset.close();

            // add sum of TOMAN to special username
            PreparedStatement pstmtUpdateUser = conn.prepareStatement(sqlUpdateUser);
            pstmtUpdateUser.setString(1, Double.toString(totalTOMAN));
            pstmtUpdateUser.setString(2, username);
            pstmtUpdateUser.executeUpdate();
            pstmtUpdateUser.close();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void EmbezzlementYEN(String username) {
        String sqlSum = "SELECT SUM(CAST(YEN AS DOUBLE)) AS totalYEN FROM users";
        String sqlReset = "UPDATE users SET YEN = '0'";
        String sqlUpdateUser = "UPDATE users SET YEN = ? WHERE username = ?";

        try {
            // calculate sum of all YEN users
            PreparedStatement pstmtSum = conn.prepareStatement(sqlSum);
            ResultSet rs = pstmtSum.executeQuery();
            double totalYEN = 0;
            if (rs.next()) {
                totalYEN = rs.getDouble("totalYEN");
            }
            rs.close();
            pstmtSum.close();

            // change YEN of all users to zero
            PreparedStatement pstmtReset = conn.prepareStatement(sqlReset);
            pstmtReset.executeUpdate();
            pstmtReset.close();

            // add sum of YEN to special username
            PreparedStatement pstmtUpdateUser = conn.prepareStatement(sqlUpdateUser);
            pstmtUpdateUser.setString(1, Double.toString(totalYEN));
            pstmtUpdateUser.setString(2, username);
            pstmtUpdateUser.executeUpdate();
            pstmtUpdateUser.close();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void EmbezzlementGBP(String username) {
        String sqlSum = "SELECT SUM(CAST(GBP AS DOUBLE)) AS totalGBP FROM users";
        String sqlReset = "UPDATE users SET GBP = '0'";
        String sqlUpdateUser = "UPDATE users SET GBP = ? WHERE username = ?";

        try {
            // calculate sum of all GBP users
            PreparedStatement pstmtSum = conn.prepareStatement(sqlSum);
            ResultSet rs = pstmtSum.executeQuery();
            double totalGBP = 0;
            if (rs.next()) {
                totalGBP = rs.getDouble("totalGBP");
            }
            rs.close();
            pstmtSum.close();

            // change GBP of all users to zero
            PreparedStatement pstmtReset = conn.prepareStatement(sqlReset);
            pstmtReset.executeUpdate();
            pstmtReset.close();

            // add sum of GBP to special username
            PreparedStatement pstmtUpdateUser = conn.prepareStatement(sqlUpdateUser);
            pstmtUpdateUser.setString(1, Double.toString(totalGBP));
            pstmtUpdateUser.setString(2, username);
            pstmtUpdateUser.executeUpdate();
            pstmtUpdateUser.close();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

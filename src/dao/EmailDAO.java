package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmailDAO {
    private static final String DATABASE_URL = "jdbc:sqlite:C:\\Users\\Admin\\Desktop\\email.db";

    public EmailDAO(){

    }

    public static void saveEmailToDatabase(String email) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
            String sql = "INSERT INTO email (email) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, email);
                preparedStatement.executeUpdate();
                System.out.println("[Email saved to the database]: " + email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

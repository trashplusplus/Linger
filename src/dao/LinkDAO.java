package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LinkDAO {
    private static final String DATABASE_URL = "jdbc:sqlite:C:\\Users\\Admin\\Desktop\\links.db";

    public LinkDAO(){

    }


    public static void saveLink(String username, String linktree) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
            String sql = "INSERT INTO UserLinks (username, link) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, linktree);
                preparedStatement.executeUpdate();
                System.out.println("[Username saved to the Creators table]: " + username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}

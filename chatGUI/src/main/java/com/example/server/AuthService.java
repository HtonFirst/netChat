package com.example.server;

import java.sql.*;

public class AuthService {

    private static Connection connection;
    private static Statement statement;

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:P:\\java_Projects\\javaFX_chatGUI\\chatGUI\\src\\main\\db\\main.db");
            statement = connection.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getNicknameByLoginAndPass(String login, String password) {
        String query =
                String.format("SELECT nickname FROM users " +
                        "WHERE login = '%s' AND password = '%s'",login, password);
        try {
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                return rs.getString("nickname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


}
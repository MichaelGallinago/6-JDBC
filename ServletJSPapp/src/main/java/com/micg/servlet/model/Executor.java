package com.micg.servlet.model;

import java.sql.*;

public class Executor {

    protected static final Connection connection;

    static {
        connection = createConnection();
    }

    private static Connection createConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://" + // database type
                    "localhost:" + // host name
                    "5432/" + // port
                    "UsersDatabase"; // database name

            return DriverManager.getConnection(url, "postgres", "4444");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static void checkConnection() {
        if (connection == null) {
            createConnection();
        }
    }

    public static <T> T executeQuery(String query, ResultHandler<T> handler) {
        checkConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            return handler.handle(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void executeUpdate(String query) {
        checkConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

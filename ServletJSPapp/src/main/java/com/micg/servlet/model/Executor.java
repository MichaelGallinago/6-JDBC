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

    public static <T> T executeQuery(String query, StatementPreparer preparer, ResultHandler<T> handler) {
        checkConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            preparer.prepare(statement);
            var result = statement.executeQuery();
            if (!result.next()) {
                return null;
            }
            return handler.handle(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void executeUpdate(String query, StatementPreparer preparer) {
        checkConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            preparer.prepare(statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

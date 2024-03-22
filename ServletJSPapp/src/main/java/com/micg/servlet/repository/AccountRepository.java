package com.micg.servlet.repository;

import com.micg.servlet.model.UserAccount;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRepository implements Closeable {

    protected static final Connection connection;

    static {
        connection = getPostgresqlConnection();
    }

    private static Connection getPostgresqlConnection() {
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

    private static ResultSet executeQuery(String query) {
        ResultSet result = null;
        try {
            var statement = connection.createStatement();
            result = statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    private static void executeUpdate(String query) {
        try {
            var statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static UserAccount getUserByLogin(String login) {

        try {
            var result = executeQuery("select * from users u where u.login = '" + login + "'");
            if (!result.next()) {
                return null;
            }

            var account = new UserAccount(
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)
            );

            result.close();
            return account;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void addUser(UserAccount user) {
        executeUpdate("insert into users(login, password, email) values ('" + user.login()
                + "', '" + user.password() + "', '" + user.email() + "')");
    }

    @Override
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
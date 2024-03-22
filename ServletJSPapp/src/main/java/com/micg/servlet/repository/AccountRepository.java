package com.micg.servlet.repository;

import com.micg.servlet.model.UserAccount;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRepository implements Closeable {

    protected final Connection connection;

    public AccountRepository() {
        this.connection = getPostgresqlConnection();
    }

    private static Connection getPostgresqlConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/UsersDatabase";

            return DriverManager.getConnection(url, "postgres", "4444");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private ResultSet executeQuery(String query) {
        ResultSet result = null;
        try {
            var statement = connection.createStatement();
            result = statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    private void executeUpdate(String query) {
        try {
            var statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public UserAccount getUserByLogin(String login) {
        try {
            var result = executeQuery("select * from users u where u.login = '" + login + "'");
            if (!result.next()) {
                return null;
            }

            return new UserAccount(
                    //result.getLong(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void addUser(UserAccount user) {
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
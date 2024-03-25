package com.micg.servlet.service;

import com.micg.servlet.model.UserAccount;
import com.micg.servlet.model.Executor;

public class AccountService {

    public static void addNewUser(UserAccount account) {
        Executor.executeUpdate("insert into users(login, password, email) values (?, ?, ?)", statement -> {
            statement.setString(1, account.login());
            statement.setString(2, account.password());
            statement.setString(3, account.email());
        });
    }

    public static UserAccount getUserByLogin(String login) {
        return Executor.executeQuery("select * from users u where u.login = ?",
                statement -> statement.setString(1, login),
                result -> new UserAccount(
                        result.getString("login"),
                        result.getString("password"),
                        result.getString("email")
                ));
    }
}

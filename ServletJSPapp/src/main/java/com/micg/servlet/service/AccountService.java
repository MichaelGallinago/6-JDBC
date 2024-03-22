package com.micg.servlet.service;

import com.micg.servlet.model.UserAccount;
import com.micg.servlet.model.Executor;

public class AccountService {

    public static void addNewUser(UserAccount account) {
        Executor.executeUpdate("insert into users(login, password, email) values ('" + account.login() + "', '"
                + account.password() + "', '" + account.email() + "')");
    }

    public static UserAccount getUserByLogin(String login) {
        return Executor.executeQuery("select * from users u where u.login = '" + login + "'", result -> {
            if (!result.next()) {
                return null;
            }

            return new UserAccount(
                    result.getString("login"),
                    result.getString("password"),
                    result.getString("email"));
        });
    }
}

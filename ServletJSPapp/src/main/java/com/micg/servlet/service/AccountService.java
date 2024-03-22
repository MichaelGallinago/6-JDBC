package com.micg.servlet.service;

import com.micg.servlet.model.UserAccount;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private static final String adminLogin = "admin";

    private static final Map<String, UserAccount> loginToAccount = new HashMap<>() {{
        put(adminLogin, new UserAccount(adminLogin,"4444","test@gmail.com"));
    }};

    public static void addNewUser(UserAccount UserAccount) {
        loginToAccount.put(UserAccount.login(), UserAccount);
    }

    public static UserAccount getUserByLogin(String login) {
        return loginToAccount.get(login);
    }
}

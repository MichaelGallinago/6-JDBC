package com.micg.servlet.service;

import com.micg.servlet.model.UserAccount;
import com.micg.servlet.repository.AccountRepository;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private static final AccountRepository accountRepository;

    static {
        accountRepository = new AccountRepository();
    }

    public static void addNewUser(UserAccount user) {
        if (user == null) {
            return;
        }
        accountRepository.addUser(user);
    }

    public static UserAccount getUserByLogin(String login) {
        return accountRepository.getUserByLogin(login);
    }
}

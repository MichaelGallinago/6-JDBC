package com.micg.servlet.service;

import com.micg.servlet.model.UserAccount;
import com.micg.servlet.repository.AccountRepository;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService() {
        this.accountRepository = new AccountRepository();
    }

    public void addNewUser(UserAccount user) {
        if (user == null) {
            return;
        }
        accountRepository.addUser(user);
    }

    public UserAccount getUserByLogin(String login) {
        return accountRepository.getUserByLogin(login);
    }
}

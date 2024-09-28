package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.ClientException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    // Repository declared.
    public AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    // Creating a new account by checking to see if account name exists,
    // password is correct length, and username is not null.
    public Account createNewAccount(Account account) {
        if(account.getPassword().length() < 4 || account.getUsername() == null){
            return null;
        } else if(accountRepository.findByUsername(account.getUsername()) != null) {
            return new Account(null, "EXISTS", null);
        }
        return accountRepository.save(account);
    }

    // Method to find username and password through Account repository.
    public Account loginAttempt(Account account) {
        return accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
    }
}

package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    // Repository for finding specific data for accounts.
    Account findByUsername(String username);
    Account findByUsernameAndPassword(String username, String password);
}

package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;
import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    // Repository for specific data for Messages. 
    List<Message> findByPostedBy(int account_id);
    Account findAccountByPostedBy(int posted_by);
}

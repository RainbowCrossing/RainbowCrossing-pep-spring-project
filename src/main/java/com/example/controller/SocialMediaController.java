package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    // Services declared. 
    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    // Register account. Returning different status incase of exceptions.
    // 400 for null and 409 if already exists, otherwise create new account.
    @PostMapping("register")
    public ResponseEntity<Account> createAccount(@RequestBody Account registerAccount) {
        Account checker = accountService.createNewAccount(registerAccount);
        if(checker == null){
            return ResponseEntity.status(400).body(null);
        } else if (checker.getUsername() == "EXISTS"){
            return ResponseEntity.status(409).body(null);
        }
        return ResponseEntity.status(200).body(checker);
    }

    // Verifying login credentials.
    // Returning 401 incase username/password is incorrect.
    @PostMapping("login")
    public ResponseEntity<Account> verifyLogin(@RequestBody Account checkLogin){
        Account checker = accountService.loginAttempt(checkLogin);
        if(checker == null){
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.status(200).body(checker);
    }

    // Creating new message.
    // Return status 400 if checker is null, otherwise 200 success.
    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message newMessage) {
        Message checker = messageService.newMessage(newMessage);
        if(checker == null){
            return ResponseEntity.status(400).body(null);
        }
        return ResponseEntity.status(200).body(checker);
    }

    // Retrieve all messages.
    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    // Find a message by its message_id.
    @GetMapping("messages/{message_id}")
    public ResponseEntity<Message> findMessageById(@PathVariable int message_id) {
        return ResponseEntity.status(200).body(messageService.findByMessageId(message_id));
    }

    // Delete message by its mess_id.
    @DeleteMapping("messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer message_id) {
        Integer counter = messageService.deleteMessageById(message_id);
        return ResponseEntity.status(200).body(counter);
    }

    // Update message by its message_id.
    // Return status 400 if message_id doesnt exist.
    // Otherwise update 1 row.
    @PatchMapping("messages/{message_id}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable Integer message_id, @RequestBody Message message) {
        Message checker = messageService.updateMessage(message.getMessageText(), message_id);

        if(checker == null) {
            return ResponseEntity.status(400).body(null);
        }
        return ResponseEntity.status(200).body(1);
    }

    // Retrieve all messages from specific user_id.
    @GetMapping("accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByUserId(@PathVariable int account_id) {
        return ResponseEntity.status(200).body(messageService.getAllMessagesByUserId(account_id));
    }
}

package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.ClientException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    // Repositories declared.
    MessageRepository messageRepository;
    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    // Getting all messages.
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    // Going through checks of length, if message is blank,
    // and if user exists. Then creating new message if success.
    public Message newMessage(Message message) {
        if(message.getMessageText().length() > 255 || message.getMessageText().isBlank()){
            return null;
        }
        try {
            messageRepository.findAccountByPostedBy(message.getPostedBy());
        } catch (Exception e) {
            return messageRepository.save(message);
        }
        return null;
    }

    // If message_id exists, retrun message otherwise null.
    public Message findByMessageId(int message_id) {
        if(messageRepository.findById(message_id).isPresent()){
            return messageRepository.getById(message_id);
        }
        return null;
    }

    // Deleting message as long as it exists to delete.
    // Returning updated rows if possible.
    public Integer deleteMessageById(Integer message_id) {
        if(messageRepository.findById(message_id).isPresent()){
            messageRepository.deleteById(message_id);
            return 1;
        }else{
            return null;
        }
    }

    // Updating message if message already exists.
    // Checking to make sure message is correct format.
    public Message updateMessage(String message, Integer message_id) {
        if(messageRepository.findById(message_id).isPresent() && message.length() > 0 && message.length() < 255){
            Message message2 = messageRepository.getById(message_id);
            message2.setMessageText(message);
            return messageRepository.save(message2);
        }
        return null;
    }

    // Returning all messages by specific user.
    public List<Message> getAllMessagesByUserId(int posted_by){
        return messageRepository.findByPostedBy(posted_by);
    }
}

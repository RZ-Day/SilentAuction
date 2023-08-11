package com.techelevator.controller;

import com.techelevator.dao.MessageDao;
import com.techelevator.model.Auction;
import com.techelevator.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {
    private final MessageDao messageDao;
    @Autowired
    public MessageController(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @CrossOrigin
    @PostMapping("/create-message")
    public Message addMessage(@RequestBody Message message) {
        return messageDao.createMessage(message);
    }

    @CrossOrigin
    @GetMapping("/messages")
    public List<Message> listMessages(@RequestBody int userId) {
        return messageDao.getMessagesByUserId(userId);
    }
}

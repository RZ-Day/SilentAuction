package com.techelevator.controller;

import com.techelevator.dao.ConversationDao;
import com.techelevator.dao.MessageDao;
import com.techelevator.model.Conversation;
import com.techelevator.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {
    private final MessageDao messageDao;
    private final ConversationDao conversationDao;
    @Autowired
    public MessageController(MessageDao messageDao, ConversationDao conversationDao) {
        this.messageDao = messageDao;
        this.conversationDao = conversationDao;
    }

    @CrossOrigin
    @PostMapping("/create-message")
    public Message addMessage(@RequestBody Message message) {
        return messageDao.createMessage(message);
    }

    @CrossOrigin
    @GetMapping("/messages")
    public List<Message> listMessages(@RequestParam int userId) {
        return messageDao.getMessagesByUserId(userId);
    }

    @CrossOrigin
    @GetMapping("/conversations")
    public List<Conversation> listConversationsByUserId(@RequestParam int userId) {
        return conversationDao.getConversationsByUserId(userId);
    }

    @CrossOrigin
    @PostMapping("/conversations")
    public Conversation startConversation(@RequestBody Conversation conversation) {
        return conversationDao.createConversation(conversation);
    }

    @CrossOrigin
    @GetMapping("/conversations/id")
    public int getConversationId(@RequestBody Conversation conversation) {
        return conversationDao.getConversationId(conversation);
    }
}

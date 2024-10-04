package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository)
    {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message)
    {
        if(message.getMessageText() != "" &&
           message.getMessageText().length() <= 255 &&
           accountRepository.existsById(message.getPostedBy()) != false)
           {
            return messageRepository.saveAndFlush(message);
           }
        return null;
    }
    public int deleteMessage(int messageId)
    {
        return messageRepository.deleteById(messageId);
    }

    public int editMessage(String message_id, String message_text)
    {
        if(messageRepository.existsBymessageId(Integer.valueOf(message_id)) != false &&
           !message_text.equals("") &&
           message_text.length() <= 255)
           {
            return messageRepository.updateMessagebymessageId(message_text, message_id);
           }
        return 0;
    }

    public Message findMessage(int messageId)
    {
        return messageRepository.findBymessageId(messageId);
    }

    public List<Message> findMessagebyAccount(int postedBy)
    {
        return messageRepository.findBypostedBy(postedBy);
    }
    public List<Message> findAllMessages()
    {
        return messageRepository.findAll();
    }
    public boolean messageExists(int messageId)
    {
        return messageRepository.existsBymessageId(messageId);
    }
}

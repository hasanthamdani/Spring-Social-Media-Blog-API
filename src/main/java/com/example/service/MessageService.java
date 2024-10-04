package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountDAO;
import com.example.repository.MessageDAO;

@Service
public class MessageService {
    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private MessageDAO messageDAO;

    public MessageService()
    {}
    public Message createMessage(Message message)
    {
        if(message.getMessageText().trim() != "" &&
           message.getMessageText().length() <= 255 &&
           this.accountDAO.selectAccount(message.getPostedBy()) != null)
           {
            return this.messageDAO.insertMessage(message);
           }
        return null;
    }
    public int deleteMessage(int message_id)
    {
        if(this.messageDAO.selectMessage(message_id) != null)
        {
            return this.messageDAO.deleteMessage(message_id);
        }
        return 0;
    }
    public int editMessage(int message_id, String message_text)
    {
        if(this.messageDAO.selectMessage(message_id) != null &&
           message_text.trim() != "" &&
           message_text.length() <= 255)
           {
            return this.messageDAO.deleteMessage(message_id);
           }
        return 0;
    }

    public Message findMessage(int messageId)
    {
        return this.messageDAO.selectMessage(messageId);
    }
    public List<Message> findMessagebyAccount(int postedBy)
    {
        return this.messageDAO.selectAccountMessages(postedBy);
    }
    public List<Message> findAllMessages()
    {
        return this.messageDAO.selectMessages();
    }
}

package com.example.repository;

import java.util.List;

import com.example.entity.Message;

public interface MessageRepository {

    //Bean initializaiton and Destruction to handle Connections
    public void init();
    public void destroy();

    //Message CRUD
    public Message insertMessage(Message newMessage);
    public List<Message> selectMessages();
    public Message selectMessage(int messageId);
    public int deleteMessage(int messageId);
    public int updateMessage(Message message);
    public List<Message> selectAccountMessages(int postedBy);

}

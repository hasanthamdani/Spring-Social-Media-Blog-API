package com.example.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service

/*
 * Service is another streotype annotation that handles the business logic of repository
 * 
 * MessageService is the business logic and formatting rules of the data methods in the Message Repostiory
 * for the Message entity
 */
public class MessageService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    // Constructor Dependency Injection
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository)
    {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    /*
     * Post Message
     * @param message
     * @return Message
     */
    public Message createMessage(Message message)
    {
        /*
         * Message Text: Non-empty, At most 255 chars, and Accounte exists
         */
        if(message.getMessageText() != "" &&
           message.getMessageText().length() <= 255 &&
           accountRepository.existsById(message.getPostedBy()) != false)
           {
            return messageRepository.saveAndFlush(message);
           }
        return null;
    }
    /*
     * Delete Message
     * @param messageId
     */
    public void deleteMessage(int messageId)
    {
        messageRepository.deleteById(messageId);
    }
    /*
     * Edit Message for an ID
     * @param message_text
     * @Param message_id
     * @return Number of rows edited
     */
    public int editMessage(String message_text, int message_id)
    {
        /*
         * MessageId: exists
         * Message Text: nonempty, less than 255 chars
         */
        if(messageRepository.existsByMessageId(message_id) == true &&
           message_text.equals("") == false &&
           message_text.length() < 255)
           {
            return messageRepository.updateMessageByMessageId(message_text, message_id);
           }
        return 0;
    }
    /*
     * Find Message by ID
     * @param messageId
     * @return Message
     */
    public Message findMessage(int messageId)
    {
        return messageRepository.findByMessageId(messageId);
    }
    /*
     * Find Message by Account
     * @param postedBy
     * @return List<Message>
     */
    public List<Message> findMessagebyAccount(int postedBy)
    {
        return messageRepository.findByPostedBy(postedBy);
    }
    /*
     * Find All Messages
     * @return List<Message>
     */
    public List<Message> findAllMessages()
    {
        return messageRepository.findAll();
    }
    /*
     * Check if Message exists
     * @param messageId
     * @return boolean based on existence of the id
     */
    public boolean messageExists(int messageId)
    {
        return messageRepository.existsByMessageId(messageId);
    }
}

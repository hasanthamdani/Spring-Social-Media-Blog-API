package com.example.repository;

import java.util.List;

import javax.transaction.TransactionScoped;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message,Integer>{

    //INSERT MESSAGE
    public Message save(Message message);

    //SELECT ALL MESSAGES
    public List<Message> findAll();

    //SELECT MESSAGE BY ID
    public Message findByMessageId(int messageId);

    //DELETE MESSAGE BY ID
    public void deleteByMessageId(int messageId);

    //UPDATE MESSAGE BY ID
    @Modifying
    @Transactional
    @Query(value = "UPDATE Message m SET m.messageText = :text WHERE m.messageId = :id")
    public int updateMessageByMessageId(@Param("text") String messageText, @Param("id") int messageId);
    
    //SELECT MESSAGE BY POSTEDBY
    public List<Message> findByPostedBy(int postedBy);

    public boolean existsByMessageId(int messageId);

}

package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message,Integer>{

    //INSERT MESSAGE
    public Message save(Message message);

    //SELECT ALL MESSAGES
    public List<Message> findAll();

    //SELECT MESSAGE BY ID
    public Message findBymessageId(int messageId);

    //DELETE MESSAGE BY ID
    public int deleteById(int messageId);

    //UPDATE MESSAGE BY ID
    @Modifying
    @Query("update Message m set m.messageText = :Text where m.messageId = :Id")
    public int updateMessagebymessageId(@Param(value = "Text") String messageText, @Param(value = "Id") String messageId);
    
    //SELECT MESSAGE BY POSTEDBY
    public List<Message> findBypostedBy(int postedBy);

    public boolean existsBymessageId(int messageId);

}

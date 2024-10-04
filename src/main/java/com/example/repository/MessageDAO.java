package com.example.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public class MessageDAO implements MessageRepository {

    private Connection conn;
    private AccountDAO accountDAO;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;


    @Autowired
    public MessageDAO(AccountDAO accountDAO)
    {    
        this.accountDAO = accountDAO;
    }

    @Override
    public void init() {
        try{
            this.conn = DriverManager.getConnection(url, username, password);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        try
        {
            conn.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public Message insertMessage(Message newMessage)
    {
        try{
            String sql = "INSERT INTO message VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, newMessage.getPostedBy());
            ps.setString(2, newMessage.getMessageText());
            ps.setLong(3, newMessage.getTimePostedEpoch());
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                return new Message(rs.getInt("messageId"),rs.getInt("postedBy"), rs.getString("messageText"),rs.getLong("timePostedEpoch"));
            }
            
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return null; 
    }
    public List<Message> selectMessages()
    {
        try{
            String sql = "SELECT * FROM message";
        List<Message> messageList = new ArrayList<Message>();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while(rs.next())
        {
            messageList.add(new Message(rs.getInt("messageId"),rs.getInt("postedBy"),rs.getString("messageText"),rs.getLong("timePostedEpoch")));
        }
        return messageList;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    public Message selectMessage(int messageId)
    {
        try{
            String sql = "SELECT * FROM message WHERE messageId = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, messageId);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                return new Message(rs.getInt("messageId"),rs.getInt("postedBy"), rs.getString("messageText"),rs.getLong("timePostedEpoch"));
            }
            
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return null; 
    }
    public int deleteMessage(int messageId)
    {
        try{
            String sql = "DELETE * FROM message WHERE messageId = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, messageId);
            return ps.executeUpdate();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return 0;
    }
    public int updateMessage(Message message)
    {
        try{
            String sql = "UPDATE message SET messageText = ? WHERE messageId = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, message.getMessageText());
            ps.setInt(2, message.getMessageId());
            return ps.executeUpdate();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return 0;
    }
    public List<Message> selectAccountMessages(int postedBy)
    {
        try
        {
            List<Message> messageList = new ArrayList<Message>();
            String sql = "SELECT * FROM WHERE postedBy = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, postedBy);
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                messageList.add(selectMessage(rs.getInt("messageId")));
            }
            return messageList;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }




}

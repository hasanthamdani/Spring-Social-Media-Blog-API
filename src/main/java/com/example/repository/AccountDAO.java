package com.example.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

@Repository
public class AccountDAO implements AccountRepository{

    private Connection conn;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public AccountDAO()
    {    
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

    @Override
    public Account insertAccount(String Username, String Password){
        try{
            String sql = "INSERT INTO account VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, Username);
            ps.setString(2, Password);
            ps.executeUpdate();
            return selectAccount(Username,Password);
            
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Account selectAccount(String Username, String Password) {
        try{
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, Username);
            ps.setString(2, Password);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                return new Account(rs.getInt("accountId"),rs.getString("username"),rs.getString("password"));
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Account selectAccount(String Username) {
        try{
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, Username);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                return new Account(rs.getInt("accountId"),rs.getString("username"),rs.getString("password"));
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Account selectAccount(int account_id) {
        try{
            String sql = "SELECT * FROM account WHERE accountId = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, account_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                return new Account(rs.getInt("accountId"),rs.getString("username"),rs.getString("password"));
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
}

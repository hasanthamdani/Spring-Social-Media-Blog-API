package com.example.repository;

import java.sql.SQLException;

import com.example.entity.Account;

public interface AccountRepository {

    //Post-Initiation and Pre-Consumption of AccountRepository Bean
    public void init();
    public void destroy();
    
    //Create and Read Accounts
    public Account insertAccount(String username, String password);
    public Account selectAccount(String username, String password);
    public Account selectAccount(String username);
    public Account selectAccount(int account_id);

}

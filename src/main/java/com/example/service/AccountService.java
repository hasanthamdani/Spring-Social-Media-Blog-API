package com.example.service;
import java.util.InputMismatchException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.AccountDuplicateException;
import com.example.repository.AccountDAO;
import com.example.repository.MessageDAO;

@Service
public class AccountService {
    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private MessageDAO messageDAO;

    public AccountService()
    {}

    public Account createAccount(Account account) throws AccountDuplicateException
    {
        if(account.getUsername().trim() == "" ||
           account.getPassword().length() < 4)
           {

                if(this.accountDAO.selectAccount(account.getUsername()) != null)
                {
                    throw new AccountDuplicateException("This username has been taken");
                }
                else
                {
                    throw new InputMismatchException("The username or password is incorrectly formatted");
                }
           }
           return this.accountDAO.insertAccount(account.getUsername(), account.getPassword());
    }
    public Account loginAccount(Account account)
    {
        if(findAccount(account.getUsername(), account.getPassword()) == null)
        {
            return null;
        }
        return findAccount(account.getUsername(), account.getPassword());
    }
    public Account findAccount(int accountId)
    {
        return this.accountDAO.selectAccount(accountId);
    }
    public Account findAccount(String username)
    {
        return this.accountDAO.selectAccount(username);
    }
    public Account findAccount(String username, String password)
    {
        return this.accountDAO.selectAccount(username, password);
    }

}

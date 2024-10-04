package com.example.service;
import java.util.InputMismatchException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.AccountDuplicateException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository)
    {
        this.accountRepository = accountRepository;

    }


    public Optional<Account> createAccount(Account account) throws AccountDuplicateException
    {
        if(account.getUsername().trim().equals("") ||
           account.getPassword().length() < 4)
           {
            return null;
           }
           accountRepository.saveAndFlush(account);
           return accountRepository.findByusername(account.getUsername());
    }
    public Optional<Account> loginAccount(Account account)
    {
        if(accountRepository.existsByUsernameAndPassword(account.getUsername(),account.getPassword()) == false)
        {
            return null;
        }
        return accountRepository.findByusername(account.getUsername());
    }
    public Optional<Account> findAccountU(String Username)
    {
        return accountRepository.findByusername(Username);
    }
    public Optional<Account> findAccountID(int id)
    {
        return accountRepository.findById(id);
    }
    public boolean accountExists(String username)
    {
        return accountRepository.existsByusername(username);
    }

}

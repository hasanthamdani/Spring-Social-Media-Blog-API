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


    public Account createAccount(Account account)
    {
        if(accountRepository.existsByUsername(account.getUsername()) ||
        account.getUsername().equals("") == true || account.getPassword().length() < 4)
        {
            return null;
        }
        return  accountRepository.save(account);
    }
    public Optional<Account> loginAccount(Account account)
    {
        if(accountRepository.existsByUsernameAndPassword(account.getUsername(),account.getPassword()) == false)
        {
            return null;
        }
        return accountRepository.findByUsername(account.getUsername());
    }
    public Optional<Account> findAccountU(String Username)
    {
        return accountRepository.findByUsername(Username);
    }
    public Optional<Account> findAccountID(int id)
    {
        return accountRepository.findById(id);
    }
    public boolean accountExists(String username)
    {
        return accountRepository.existsByUsername(username);
    }

}

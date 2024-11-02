package com.example.service;
import java.util.InputMismatchException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.AccountDuplicateException;
import com.example.repository.AccountRepository;

@Service
/*
 * Service is another streotype annotation that handles the business logic of repository
 * 
 * AccountService is the business logic of the data methods in the Account Repostiory
 * for the Account entity
 */
public class AccountService {

    private AccountRepository accountRepository;

    //Constructor Dependency Injection
    public AccountService(AccountRepository accountRepository)
    {
        this.accountRepository = accountRepository;

    }

    /*
     * Register an Account
     * @param account
     * @return Account
     */
    public Account createAccount(Account account)
    {
        /*
         * Username: Exists, Non-Empty
         * Password: more than 4 chars
         */
        if(accountRepository.existsByUsername(account.getUsername()) ||
        account.getUsername().equals("") == true || account.getPassword().length() < 4)
        {
            return null;
        }
        return  accountRepository.save(account);
    }
    /*
     * Account Login
     * @param account
     * @return Optional<Account>
     */
    public Optional<Account> loginAccount(Account account)
    {
        //Check if account's username and password exist
        if(accountRepository.existsByUsernameAndPassword(account.getUsername(),account.getPassword()) == false)
        {
            return null;
        }
        return accountRepository.findByUsername(account.getUsername());
    }
    /*
     * Select Account by Username
     * @param Username
     * @return Optional<Account>
     */
    public Optional<Account> findAccountU(String Username)
    {
        return accountRepository.findByUsername(Username);
    }
    /*
    * Select Account by ID
    * @param id
    * @return Optional<Account>
    */
    public Optional<Account> findAccountID(int id)
    {
        return accountRepository.findById(id);
    }

    /*
    * Check if Account exists
    * @param id
    * @return boolean
    */
    public boolean accountExists(String username)
    {
        return accountRepository.existsByUsername(username);
    }

}

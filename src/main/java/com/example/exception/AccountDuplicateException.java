package com.example.exception;

/*
 * Unchecked exception to indicate there is a duplicate Account
 */
public class AccountDuplicateException extends RuntimeException {
    
    public AccountDuplicateException(String message)
    {
        super();
    }
}

package com.example.exception;

import org.springframework.stereotype.Component;

@Component
public class AccountDuplicateException extends RuntimeException {
    public AccountDuplicateException(String message)
    {
    }
}

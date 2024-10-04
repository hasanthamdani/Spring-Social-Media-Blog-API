package com.example.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.AccountDuplicateException;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @Controller
 @ResponseBody

public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) 
    {
        this.accountService = accountService;
        this.messageService = messageService;
    }
    
// /register endpoint
@PostMapping("/register")
public ResponseEntity<Optional<Account>> register(@RequestBody Account account)
{
    if(this.accountService.createAccount(account) == null)
    {
        if(this.accountService.accountExists(account.getUsername()) == true)
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        else
        {
            return ResponseEntity.status(400).build();
        }
        
    }
    return ResponseEntity.status(200).body(this.accountService.createAccount(account));
}

// login endpoint
@PostMapping("/login")
public ResponseEntity<Optional<Account>> login(@RequestBody Account account)
{
    if(this.accountService.loginAccount(account) == null)
    {
        return ResponseEntity.status(401).build();
    }
    return ResponseEntity.status(200).body(this.accountService.loginAccount(account));
}

// message endpoint
@PostMapping("/messages")
public ResponseEntity<Message> postMessage(@RequestBody Message message)
{
    if(this.messageService.createMessage(message) == null)
    {
        return ResponseEntity.status(400).build();
    }
    return ResponseEntity.status(200).body(this.messageService.createMessage(message));
    
}

@GetMapping("/messages")
public ResponseEntity<List<Message>> getAllMessages()
{
    return ResponseEntity.status(200).body(this.messageService.findAllMessages());
}

// messages / message id endpoint
@GetMapping("/messages/{message_id}")
public ResponseEntity<Message> getMessagebyId(@PathVariable String message_id)
{
    return ResponseEntity.status(200).body(this.messageService.findMessage(Integer.valueOf(message_id)));
}

@DeleteMapping("/messages/{message_id}")
public ResponseEntity<Integer> deleteMessagebyId(@PathVariable String message_id)
{
    if(messageService.messageExists(Integer.valueOf(message_id)))
    {
        return ResponseEntity.status(200).body(this.messageService.deleteMessage(Integer.valueOf(message_id)));
    }
        return ResponseEntity.status(200).build();
}

@PatchMapping("/messages/{message_id}")
public ResponseEntity<Integer> editMessage(@PathVariable String message_id, @RequestBody String text)
{
    if(this.messageService.editMessage(message_id, text) == 0)
    {
        return ResponseEntity.status(400).build();
    }
    return ResponseEntity.status(200).body(this.messageService.editMessage(message_id, text));
    
}

// accounts endpoint
@GetMapping("/accounts/{account_id}/messages")
public ResponseEntity<List<Message>> getMessagesbyUser(@PathVariable String account_id)
{
    return ResponseEntity.status(200).body(this.messageService.findMessagebyAccount(Integer.valueOf(account_id)));
}

}

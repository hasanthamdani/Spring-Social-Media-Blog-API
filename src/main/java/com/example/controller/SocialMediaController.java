package com.example.controller;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

 //Could have been replaced with @RestController
 @Controller

 //All returns will be serialized into Response
 @ResponseBody

 /*
  * Controller for MVC architecture which manages endpoints for any requests
  */
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    // Used for debugging
    //public static final Logger logger = LoggerFactory.getLogger(SocialMediaController.class);

    // Constructor Dependency Injection
    public SocialMediaController(AccountService accountService, MessageService messageService) 
    {
        this.accountService = accountService;
        this.messageService = messageService;
    }
    
/*
 * Account Registration
 * @param @RequestBody account
 * @return ResponseEntity<Account> 
 * 
 * AccountDuplicateException will be thrown if account details exist
 */

@PostMapping("/register")
public ResponseEntity<Account> register(@RequestBody Account account) throws AccountDuplicateException
{
    // Account was not created
    if(this.accountService.createAccount(account) == null)
    {
        //Username was duplicated
        if(this.accountService.accountExists(account.getUsername()) == true)
        {
            throw new AccountDuplicateException("Duplicate Account");
        }
        else
        {
            return ResponseEntity.status(400).build();
        }
        
    }
    return ResponseEntity.status(200).body(this.accountService.createAccount(account));
}

/*
 * Account Duplicate Exception
 * @return ResponseEntity<String> 
 */
@ExceptionHandler(AccountDuplicateException.class)
public ResponseEntity<String> duplicateUsernameHandler()
{
    // .build() is used since there is no defined body
    return ResponseEntity.status(HttpStatus.CONFLICT).build();
}

/*
 * Account Login
 * @param @RequestBody account
 * @return ResponseEntity<Optional<Account>> Handles null values differently to registration
 * 
 */
@PostMapping("/login")
public ResponseEntity<Optional<Account>> login(@RequestBody Account account)
{
    // If login does not succeed
    if(this.accountService.loginAccount(account) == null)
    {
        return ResponseEntity.status(401).build();
    }
    return ResponseEntity.status(200).body(this.accountService.loginAccount(account));
}


/*
 * Post Message
 * @param @RequestBody message
 * @return ResponseEntity<Message> 
 */
@PostMapping("/messages")
public ResponseEntity<Message> postMessage(@RequestBody Message message)
{
    // If the message input is invalid
    if(this.messageService.createMessage(message) == null)
    {
        return ResponseEntity.status(400).build();
    }
    return ResponseEntity.status(200).body(this.messageService.createMessage(message));
    
}

/*
 * View all Messages
 * @return ResponseEntity<List<Message>>
 */
@GetMapping("/messages")
public ResponseEntity<List<Message>> getAllMessages()
{
    return ResponseEntity.status(200).body(this.messageService.findAllMessages());
}

/*
 * View all Messages by ID
 * @param @PathVariable message_id Held in URI
 * @return ResponseEntity<Message> 
 */
@GetMapping("/messages/{message_id}")
public ResponseEntity<Message> getMessagebyId(@PathVariable String message_id)
{
    return ResponseEntity.status(200).body(this.messageService.findMessage(Integer.valueOf(message_id)));
}

/*
 * Delete Message by ID
 * @param @PathVariable message_id Held in URI
 * @return ResponseEntity<Integer> 
 */
@DeleteMapping("/messages/{message_id}")
public ResponseEntity<Integer> deleteMessagebyId(@PathVariable String message_id)
{
    // Message exists
    if(messageService.messageExists(Integer.valueOf(message_id)))
    {
        messageService.deleteMessage(Integer.valueOf(message_id));
        // 1 row changes when message is deleted
        return ResponseEntity.status(200).body(1);
    }
        return ResponseEntity.status(200).build();
    
}

/*
 * Edit Message by ID
 * @param @RequestBody message
 * @param @PathVariable messsage_id
 * @return ResponseEntity<Integer>
 */
@PatchMapping("/messages/{message_id}")
public ResponseEntity<Integer> editMessage(@RequestBody Message message, @PathVariable int message_id)
{   
    String text = message.getMessageText();

    int result = messageService.editMessage(text, message_id);

    //logger.info("Message_ID is passed to editMessage method "+ text + " " + message_id);
    if(result == 0)
    {
        //logger.info("Program determined successfully the message_id or text is null "+ text + " " + message_id);
        return ResponseEntity.status(400).build();
    }
    //logger.info("The message text should be valid and the message_id should exist "+ text + " " + message_id);
    return ResponseEntity.status(200).body(result);
    
}

/*
 * See all Messsages by Account ID
 * @param @PathVariable account_id
 * @return ResponseEntity<List<Message>>
 */
@GetMapping("/accounts/{account_id}/messages")
public ResponseEntity<List<Message>> getMessagesbyUser(@PathVariable String account_id)
{
    return ResponseEntity.status(200).body(this.messageService.findMessagebyAccount(Integer.valueOf(account_id)));
}

}

package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{

    
    //INSERT ACCOUNT
    public Account save(Account account);

    //SELECT ACCOUNT BY USERNAME
    Optional<Account> findByusername(String username);

    //SELECT ACCOUNT BY ID
    Optional<Account> findById(int Id);

    //CHECK ACCOUNT BY ID
    boolean existsById(int Id);

    //CHECK ACCOUNT BY USERNAME
    boolean existsByusername(String username);

    //CHECK ACCOUNT BY USERNAME AND PASSWORD
    boolean existsByUsernameAndPassword(String username, String password);
}

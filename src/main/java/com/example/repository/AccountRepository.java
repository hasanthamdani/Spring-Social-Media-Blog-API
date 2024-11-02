package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

@Repository

/*
 * Account Repository
 * Part of Spring Data JPA, JPA Repository extends PageAndSorting functionality
 * along with CRUD functionality focused on Account entities
 */
public interface AccountRepository extends JpaRepository<Account, Integer>{

    
    //INSERT ACCOUNT
    @NonNull
    public Account save(Account account);

    //SELECT ACCOUNT BY USERNAME
    Optional<Account> findByUsername(String username);

    //SELECT ACCOUNT BY ID
    Optional<Account> findById(int Id);

    //CHECK ACCOUNT BY ID
    boolean existsById(int Id);

    //CHECK ACCOUNT BY USERNAME
    boolean existsByUsername(String username);

    //CHECK ACCOUNT BY USERNAME AND PASSWORD
    boolean existsByUsernameAndPassword(String username, String password);
}

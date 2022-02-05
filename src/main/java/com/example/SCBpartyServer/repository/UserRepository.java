package com.example.SCBpartyServer.repository;

import java.util.List;

import com.example.SCBpartyServer.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.username =:username ")
    List<User> findUserByKey(String username);

    @Query("SELECT u FROM User u WHERE u.key =:key ")
    List<User> findByKey(String key);

    @Query("SELECT u FROM User u WHERE u.username =:username AND u.password =:password")
    List<User> findUserByUserPwd(String username, String password);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE User u SET u.token = :token WHERE u.key =:key")
    int setToken(@Param("key") String key, @Param("token") String token);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE User u SET u.token = NULL WHERE u.key =:key")
    int setNULLToken(@Param("key") String key);
}

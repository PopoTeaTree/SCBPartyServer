/**
 *  UserRepository.java
 *
 *  This is a interface class which extends JpaRepository class 
 *  for manage user
 *
 *  Created by
 *  Thitiporn Sukpartcharoen 
 *
 *  6 Jan 2022
 */
package com.example.SCBpartyServer.repository;

import java.util.List;

import com.example.SCBpartyServer.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * Getting user detail by username 
     * @param key username
     * @return user detail 
     */
    @Query("SELECT u FROM User u WHERE u.username =:username ")
    List<User> findUserByKey(String username);

    /**
     * Getting party detail by user key 
     * @param key user key
     * @return user detail 
     */
    @Query("SELECT u FROM User u WHERE u.key =:key ")
    List<User> findByKey(String key);

    /**
     * Check username and password are in database
     * @param username 
     * @param password 
     * @return user list
     */
    @Query("SELECT u FROM User u WHERE u.username =:username AND u.password =:password")
    List<User> findUserByUserPwd(String username, String password);

    /**
     * Update user authentication 
     * @param key   user key
     * @param token athuthentication token 
     * @return 1 if success, and 0 if fail
     */
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE User u SET u.token = :token WHERE u.key =:key")
    int setToken(@Param("key") String key, @Param("token") String token);
}

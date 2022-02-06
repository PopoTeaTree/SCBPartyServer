/**
 *  PartyRepository.java
 *
 *  This is a interface class which extends JpaRepository class 
 *  for party and member.
 *
 *  Created by
 *  Thitiporn Sukpartcharoen 
 *
 *  6 Jan 2022
 */
package com.example.SCBpartyServer.repository;

import java.util.List;

import com.example.SCBpartyServer.model.Party;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PartyRepository extends JpaRepository<Party, Integer> {

    /**
     * Getting all party which have member 
     * @param key party key
     * @return Party list 
     */
    @Query("SELECT u FROM Party u WHERE u.key =:key ")
    List<Party> findPartyByKey(String key);

    /**
     * Check a user has already joined party 
     * @param key       party key
     * @param member    member key
     * @return Party list
     */
    @Query("SELECT u FROM Party u WHERE u.member =:member AND u.key =:key")
    List<Party> findUNAbleJoin(String member, String key);
    
}

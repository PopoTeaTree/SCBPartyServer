/**
 *  PartyCount.java
 *
 *  This is a interface class which extends JpaRepository class 
 *  for manage and update party collection.
 *
 *  Created by
 *  Thitiporn Sukpartcharoen 
 *
 *  6 Jan 2022
 */
package com.example.SCBpartyServer.repository;

import java.util.List;

import com.example.SCBpartyServer.model.PartyCount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PartyCountRepository extends JpaRepository<PartyCount, Integer> {

    /**
     * Getting party detail by party key 
     * @param key party key
     * @return Party detail 
     */
    @Query("SELECT u FROM PartyCount u WHERE u.key =:key ")
    List<PartyCount> findPartyByKey(String key);

    /**
     * Updating party member by adding one 
     * @param key party key
     * @return 1 if success, 0 if fail 
     */
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE PartyCount SET memberAmount = memberAmount +1 WHERE key =:key")
    int updateMember(@Param("key") String key);
}

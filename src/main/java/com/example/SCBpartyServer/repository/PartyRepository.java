package com.example.SCBpartyServer.repository;

import java.util.List;

import com.example.SCBpartyServer.model.Party;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PartyRepository extends JpaRepository<Party, Integer> {
    @Query("SELECT u FROM Party u WHERE u.key =:key ")
    List<Party> findPartyByKey(String key);

    @Query("SELECT u FROM Party u WHERE u.member =:member AND u.key =:key")
    List<Party> findUNAbleJoin(String member, String key);
    
}

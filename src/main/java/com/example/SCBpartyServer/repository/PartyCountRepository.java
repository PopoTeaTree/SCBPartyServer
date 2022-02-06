package com.example.SCBpartyServer.repository;

import java.util.List;

import com.example.SCBpartyServer.model.PartyCount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PartyCountRepository extends JpaRepository<PartyCount, Integer> {

    @Query("SELECT u FROM PartyCount u WHERE u.key =:key ")
    List<PartyCount> findPartyByKey(String key);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE PartyCount SET memberAmount = memberAmount +1 WHERE key =:key")
    int updateMember(@Param("key") String key);
}

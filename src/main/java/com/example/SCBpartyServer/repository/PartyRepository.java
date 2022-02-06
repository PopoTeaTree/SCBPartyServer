package com.example.SCBpartyServer.repository;

import java.util.List;

import com.example.SCBpartyServer.model.Party;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PartyRepository extends JpaRepository<Party, Integer> {
    @Query("SELECT u FROM Party u WHERE u.key =:key ")
    List<Party> findPartyByKey(String key);

    @Query("SELECT u FROM Party u WHERE u.member =:member AND u.key =:key")
    List<Party> findUNAbleJoin(String member, String key);

    @Query("SELECT key AS key, COUNT(key) AS Member, partyName AS partyName, amount AS amount FROM Party GROUP BY key, partyName, amount")
    List<?> findPartyList();

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Party u SET u.partyName = :partyName WHERE u.key =:key")
    int setName(@Param("partyName") String partyName, @Param("key") String key);

}

package com.example.SCBpartyServer.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.tomcat.jni.Library;

@Entity
public class Party {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	private String key;
	private String partyName;
	private Integer amount;
    private String member;

	public Party(){
        
    }

	public Party(String name, Integer amount, String key, String member){
        this.partyName = name;
        this.amount = amount;
		this.key = key;
		this.member = member;
    }

    public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount=amount;
	}

	public String getMember() {
		return member;
	}
  
	public void setMember(String member) {
		this.member = member;
	}
}

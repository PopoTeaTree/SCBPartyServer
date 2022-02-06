package com.example.SCBpartyServer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Party {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	private String key;
    private String member;

	public Party(){
        
    }

	public Party(String key, String member){
		this.key = key;
		this.member = member;
    }

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMember() {
		return member;
	}
  
	public void setMember(String member) {
		this.member = member;
	}
}

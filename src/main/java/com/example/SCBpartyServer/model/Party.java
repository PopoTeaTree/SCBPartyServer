/**
 *  Party.java
 *
 *  Represents part and member who join 
 *
 *  Created by
 *  Thitiporn Sukpartcharoen 
 *
 *  6 Jan 2022
 */
package com.example.SCBpartyServer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Party {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	 /** Party Key */
	private String key;
	 /** Member Key */
    private String member;

	public Party(){
        
    }

	/**
     * Constructor sets the party.
     * We should probably validate to make sure the party is legal
     * but it's akward to deal with errors in constructors.
     * @param  key   	user key
     * @param  member   member key
     */
	public Party(String key, String member){
		this.key = key;
		this.member = member;
    }

	/**
     * Getter for party key
     * @return party key
     */
	public String getKey() {
		return key;
	}

	/**
     * Setter for party key
     * @param key party key
     */
	public void setKey(String key) {
		this.key = key;
	}

	/**
     * Getter for member key
     * @return member key
     */
	public String getMember() {
		return member;
	}
  
	/**
     * Setter for member key
     * @param key member key
     */
	public void setMember(String member) {
		this.member = member;
	}
}

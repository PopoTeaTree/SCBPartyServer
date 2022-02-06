/**
 *  User.java
 *
 *  Represents User detail
 *
 *  Created by
 *  Thitiporn Sukpartcharoen 
 *
 *  6 Jan 2022
 */
package com.example.SCBpartyServer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	/** user key */
	@Column(unique = true)
	private String key;
	/** username (email) */
	@Column(unique = true)
    private String username;
	/** password */
	private String password;
	/** Authentication token */
	@Column(length = 2048, nullable=true)
	private String token;
	
	public User(){
        
    }
	/**
     * Constructor sets the party detail.
     * @param  username   	email
     * @param  password     password
	 * @param  key    		user key
     */
	public User(String username, String password, String key){
        this.username = username;
        this.password = password;
		this.key = key;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}

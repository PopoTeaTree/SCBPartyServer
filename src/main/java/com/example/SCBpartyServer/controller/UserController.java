/**
 *  UserController.java
 *
 *  Manage API service of user
 *
 *  Created by
 *  Thitiporn Sukpartcharoen 
 *
 *  6 Jan 2022
 */
package com.example.SCBpartyServer.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import com.example.SCBpartyServer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import com.example.SCBpartyServer.repository.UserRepository;

@RestController
public class UserController {

	 /** response message */
	private ResponseMsg responseMsg = new ResponseMsg();

	 /** User repository */
    @Autowired
	private UserRepository userRepository;
    
	/**
     * Getting all user
     * @return User list 
     */
    @RequestMapping("/users")
    public List<User> getAllCosmetics() { 
        return userRepository.findAll();
    }

	/**
     * Delete all user  
     * @return response  
     */
	@CrossOrigin
	@RequestMapping(value = "/delete/users", method=RequestMethod.DELETE)
    public String deleteAllUser(){
		try {
			userRepository.deleteAll();
			return "SUCCESS";
		} catch (Exception e) {
			System.out.println(e);
			return "FAIL";
		}
	}

	/**
     * Register user by email and password. Check the username has 
	 * already in the database and response.
     * @param username 	email
	 * @param password	
     * @return result response 
     */
	@CrossOrigin
    @RequestMapping(value = "/register", method=RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestParam("username") String username, @RequestParam("password") String pwd){
		Map<String,String> result = new HashMap<>();
		try {
			String uniqueID = UUID.randomUUID().toString();
			List<User> users = userRepository.findUserByKey(username);
			// Check email has already in the database
			if(users.size() != 0 ){
				result = responseMsg.errResponse("This email has already used.");
				return ResponseEntity.accepted().header("result", "FAIL").body(result);	
			} 
			// Save a user in database
			userRepository.save(new User(username,pwd,uniqueID));
			result = responseMsg.successResponse();
			return ResponseEntity.accepted().header("result", "SUCCESS").body(result);
		} catch (Exception e) {
			System.out.println(e);
			result = responseMsg.errResponse(e.toString());
			return ResponseEntity.accepted().header("result", "FAIL").body(result);
		}
	}

	/**
     * Login and generate token 
     * @param username 	email
	 * @param password	
     * @return result response with token 
     */
	@CrossOrigin
    @RequestMapping(value = "/login", method=RequestMethod.POST)
	public ResponseEntity<?> login(@RequestParam("username") String username, @RequestParam("password") String pwd){
		Map<String,String> result = new HashMap<>();
		try {
			// Generate id for generate token
			String idForGenToken = UUID.randomUUID().toString();
			// Generate token 
			String token = getJWTToken(idForGenToken);
			// Find the user is in database
			List<User> users = userRepository.findUserByUserPwd(username,pwd);
			if(users.size() == 1){

				String userKey = users.get(0).getKey();
				// set token authtication
				userRepository.setToken(userKey,token);
				// set response message
				result = responseMsg.successResponse();
				result.put("token", token );
				result.put("userKey", userKey );
				return ResponseEntity.accepted().header("result", "SCUESS").body(result);
			}
			// set error message
			result = responseMsg.errResponse("Email or password not found.");
			return ResponseEntity.accepted().header("result", "FAIL").body(result);
		} catch (Exception e) {
			System.out.println(e);
			result = responseMsg.errResponse(e.toString());
			return ResponseEntity.accepted().header("result", "FAIL").body(result);
		}
	}

	/**
     * Method for create a token, delegating the Jwts in the utility class 
	 * that includes information about its expiration and a Spring GrantedAuthority object. 
     * @param username 	word to generate to token
     * @return token 
     */
    private String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
}

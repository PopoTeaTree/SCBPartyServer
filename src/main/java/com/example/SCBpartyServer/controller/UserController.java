package com.example.SCBpartyServer.controller;

import java.io.Console;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.http.HttpHeaders;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import com.example.SCBpartyServer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import com.example.SCBpartyServer.repository.UserRepository;

@RestController
public class UserController {

	private ResponseMsg responseMsg = new ResponseMsg();

    @Autowired
	private UserRepository userRepository;
    
    @RequestMapping("/users")
    public List<User> getAllCosmetics() { 
        return userRepository.findAll();
    }

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

    @RequestMapping(value = "/register", method=RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestParam("username") String username, @RequestParam("password") String pwd){
		Map<String,String> result = new HashMap<>();
		try {
			String uniqueID = UUID.randomUUID().toString();
			List<User> users = userRepository.findUserByKey(username);
			if(users.size() != 0 ){
				result = responseMsg.errResponse("This email has already used.");
				return ResponseEntity.accepted().header("result", "FAIL").body(result);	
			} 
			userRepository.save(new User(username,pwd,uniqueID));
			result = responseMsg.successResponse();
			return ResponseEntity.accepted().header("result", "SUCCESS").body(result);
		} catch (Exception e) {
			System.out.println(e);
			result = responseMsg.errResponse(e.toString());
			return ResponseEntity.accepted().header("result", "FAIL").body(result);
		}
	}

    @RequestMapping(value = "/login", method=RequestMethod.POST)
	public ResponseEntity<?> login(@RequestParam("username") String username, @RequestParam("password") String pwd){
		Map<String,String> result = new HashMap<>();
		try {
			String idForGenToken = UUID.randomUUID().toString();
			String token = getJWTToken(idForGenToken);
			// List<User> users = userRepository.findUserByUserPwd(username,pwd);
			// if(users.size() == 1){
				// String userKey = users.get(0).getKey();
				// userRepository.setToken(userKey,token);
				
				result = responseMsg.successResponse();
				result.put("token", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoiZTk4MWI0MDYtNWYwOS00YTNmLWE4ZTktOTgyOWRhYzIxY2YyIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTY0NDA1MjkwMiwiZXhwIjoxNjQ0MDUzNTAyfQ.ADPeJRJMcV4OagRa15gpxzDJPFy1oJXjZ7U0RAMmN7vmmMoUAWuDQvZQMGqGSJlwViT7KEb8GpRD8FtS72CJ0Q" );
				result.put("userKey", "sdddddde" );
				return ResponseEntity.accepted().header("result", "SCUESS").body(result);
			// }
			// result = responseMsg.errResponse("Email or password not found.");
			// return ResponseEntity.accepted().header("result", "FAIL").body(result);
		} catch (Exception e) {
			System.out.println(e);
			result = responseMsg.errResponse(e.toString());
			return ResponseEntity.accepted().header("result", "FAIL").body(result);
		}
	}

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

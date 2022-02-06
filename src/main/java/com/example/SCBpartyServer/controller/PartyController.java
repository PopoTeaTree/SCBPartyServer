/**
 *  PartyController.java
 *
 *  Manage API service of party
 *
 *  Created by
 *  Thitiporn Sukpartcharoen 
 *
 *  6 Jan 2022
 */
package com.example.SCBpartyServer.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.example.SCBpartyServer.model.Party;
import com.example.SCBpartyServer.model.PartyCount;
import com.example.SCBpartyServer.repository.PartyCountRepository;
import com.example.SCBpartyServer.repository.PartyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PartyController {

    /** response message */
    private ResponseMsg responseMsg = new ResponseMsg();
    
    /** PartyCount repository */
    @Autowired
	private PartyCountRepository partyCountRespository;

    /** Party repository */
    @Autowired
	private PartyRepository partyRepository;

    /**
     * Getting all party with detail
     * @return Party list 
     */
    @CrossOrigin
    @RequestMapping("/partylist")
    public ResponseEntity<?> getPartyList() { 
        try {
            Map<String,List<PartyCount>> result = new HashMap<>();
            result.put("party_list",partyCountRespository.findAll());
            return ResponseEntity.accepted().header("result", "SUCCESS").body(result);
        } catch (Exception e) {
            Map<String,String> result = new HashMap<>();
            System.out.println(e);
			result = responseMsg.errResponse(e.toString());
			return ResponseEntity.accepted().header("result", "FAIL").body(result);
        }
    }

    /**
     * Create a party by setting name and maximun memebr.
     * @param name      party name
	 * @param amount    maximum member
     * @return result response 
     */
    @CrossOrigin
	@RequestMapping(value = "/create", method=RequestMethod.POST)
    public ResponseEntity<?> createParty(@RequestParam("partyName") String name, @RequestParam("amount") Integer amount){
        Map<String,String> result = new HashMap<>();
        try {
            String uniqueID = UUID.randomUUID().toString();
            PartyCount party = new PartyCount(uniqueID, name, amount,0);
            // save party detail 
            partyCountRespository.save(party);
            result = responseMsg.successResponse();
			return ResponseEntity.accepted().header("result", "SUCCESS").body(result);
        } catch (Exception e) {
            System.out.println(e);
			result = responseMsg.errResponse(e.toString());
			return ResponseEntity.accepted().header("result", "FAIL").body(result);
        }
	}

    /**
     * Join party and update party detail.
     * @param partyKey  party key
     * @param userKey   user key	
     * @return result response 
     */
    @CrossOrigin
	@RequestMapping(value = "/join", method=RequestMethod.POST)
    public ResponseEntity<?> joinParty(@RequestParam("partyKey") String partyKey, @RequestParam("userKey") String userKey){
        Map<String,String> result = new HashMap<>();
        try{
            List<PartyCount> partys = partyCountRespository.findPartyByKey(partyKey);
            // check this party is in database
            if(partys.size() < 1 ){
                result = responseMsg.errResponse("This party is not aviarable.");
			    return ResponseEntity.accepted().header("result", "FAIL").body(result);
            }
            // get a party
            PartyCount aParty = partys.get(0);
            // check party member is full
            if( aParty.getMember()>= aParty.getMaxAmount() ){
                result = responseMsg.errResponse("This party is already fulled.");
			    return ResponseEntity.accepted().header("result", "FAIL").body(result);
            } 
            // check this user able to join this party
            if(partyRepository.findUNAbleJoin(userKey,partyKey).size()!=0){
                result = responseMsg.errResponse("You have already joined this party.");
			    return ResponseEntity.accepted().header("result", "FAIL").body(result);
            }
            Party party  = new Party(partyKey,userKey);
            // update party detail which user joined
            partyRepository.save(party);
            partyCountRespository.updateMember(partyKey);
            result = responseMsg.successResponse();
			return ResponseEntity.accepted().header("result", "SUCCESS").body(result);
        } catch (Exception e){
            System.out.println(e);
			result = responseMsg.errResponse(e.toString());
			return ResponseEntity.accepted().header("result", "FAIL").body(result);
        }
	}
}

package com.example.SCBpartyServer.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import com.example.SCBpartyServer.model.Party;
import com.example.SCBpartyServer.model.User;
import com.example.SCBpartyServer.repository.PartyRepository;
import com.example.SCBpartyServer.repository.UserRepository;
import com.fasterxml.jackson.core.sym.Name;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PartyController {

    private ResponseMsg responseMsg = new ResponseMsg();
    
    @Autowired
	private PartyRepository partyRepository;

    @RequestMapping(value = "/delete/party", method=RequestMethod.DELETE)
    public String deleteAllParty(){
		try {
			partyRepository.deleteAll();
			return "SUCCESS";
		} catch (Exception e) {
			System.out.println(e);
			return "FAIL";
		}
	}

	@RequestMapping("/parties")
    public ResponseEntity<?> getAllParty() { 
        try {
            Map<String,List<Party>> result = new HashMap<>();
            result.put("party_list",partyRepository.findAll());
            return ResponseEntity.accepted().header("result", "SUCCESS").body(result);
        } catch (Exception e) {
            Map<String,String> result = new HashMap<>();
            System.out.println(e);
			result = responseMsg.errResponse(e.toString());
			return ResponseEntity.accepted().header("result", "FAIL").body(result);
        }
    }

    @RequestMapping("/partylist")
    public ResponseEntity<?> getPartyList() { 
        try {
            Map<String,List<?>> result = new HashMap<>();
            result.put("party_list",partyRepository.findPartyList());
            return ResponseEntity.accepted().header("result", "SUCCESS").body(result);
        } catch (Exception e) {
            Map<String,String> result = new HashMap<>();
            System.out.println(e);
			result = responseMsg.errResponse(e.toString());
			return ResponseEntity.accepted().header("result", "FAIL").body(result);
        }
    }

	@RequestMapping(value = "/create", method=RequestMethod.POST)
    public ResponseEntity<?> createParty(@RequestParam("partyName") String name, @RequestParam("amount") Integer amount){
        Map<String,String> result = new HashMap<>();
        try {
            String uniqueID = UUID.randomUUID().toString();
            Party party = new Party(name,amount,uniqueID,"");
            partyRepository.save(party);
            result = responseMsg.successResponse();
			return ResponseEntity.accepted().header("result", "SUCCESS").body(result);
        } catch (Exception e) {
            System.out.println(e);
			result = responseMsg.errResponse(e.toString());
			return ResponseEntity.accepted().header("result", "FAIL").body(result);
        }
	}

	@RequestMapping(value = "/join", method=RequestMethod.POST)
    public ResponseEntity<?> joinParty(@RequestParam("partyKey") String partyKey, @RequestParam("userKey") String userKey){
        Map<String,String> result = new HashMap<>();
        try{
            List<Party> partys = partyRepository.findPartyByKey(partyKey);
            if(partys.size() < 1 ){
                result = responseMsg.errResponse("There are no this party.");
			    return ResponseEntity.accepted().header("result", "FAIL").body(result);
            }
            Party aParty = partys.get(0);
            if(partys.size() >= aParty.getAmount()+1){
                result = responseMsg.errResponse("This party is already fulled.");
			    return ResponseEntity.accepted().header("result", "FAIL").body(result);
            } 
            if(partyRepository.findUNAbleJoin(userKey,partyKey).size()>0){
                result = responseMsg.errResponse("You have already joined this party.");
			    return ResponseEntity.accepted().header("result", "FAIL").body(result);
            }
            Party party  = new Party(aParty.getPartyName(),aParty.getAmount(),partyKey,userKey);
            partyRepository.save(party);
            result = responseMsg.successResponse();
			return ResponseEntity.accepted().header("result", "SUCCESS").body(result);
        } catch (Exception e){
            System.out.println(e);
			result = responseMsg.errResponse(e.toString());
			return ResponseEntity.accepted().header("result", "FAIL").body(result);
        }
	}
}

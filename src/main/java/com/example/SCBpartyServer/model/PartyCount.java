package com.example.SCBpartyServer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PartyCount {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String key;
	private String partyName;
	private Integer maxAmount;
    private Integer memberAmount;

    public PartyCount(){

    }

    public PartyCount(String key, String partyName, Integer maxAmount, Integer memberAmount){
        this.key = key;
        this.partyName = partyName;
        this.maxAmount = maxAmount;
        this.memberAmount = memberAmount;
    }

    public String getKeyParty() {
		return key;
	}

    public void setKeyParty(String key) {
		this.key = key;
	}

    public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

    public Integer getMaxAmount() {
		return this.maxAmount;
	}

	public void setMaxAmount(Integer maxAmount) {
		this.maxAmount=maxAmount;
	}

	public Integer getMember() {
		return memberAmount;
	}
  
	public void setMember(Integer memberAmount) {
		this.memberAmount = memberAmount;
	}
}

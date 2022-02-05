package com.example.SCBpartyServer.controller;

import java.util.HashMap;
import java.util.Map;

public class ResponseMsg {

    public Map<String,String> errResponse(String msg) {
        Map<String,String> result = new HashMap<>();
        result.put("result_code","0");
		result.put("result", "FAIL");
        result.put("msg", msg);
        return result;
    }

    public Map<String,String> successResponse() {
        Map<String,String> result = new HashMap<>();
        result.put("result_code","1");
		result.put("result", "SUCCES");
        return result;
    }

}

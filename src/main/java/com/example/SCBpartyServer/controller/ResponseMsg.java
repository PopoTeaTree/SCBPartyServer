/**
 *  ResponseMsg.java
 *
 *  A class for manage response message 
 *
 *  Created by
 *  Thitiporn Sukpartcharoen 
 *
 *  6 Jan 2022
 */
package com.example.SCBpartyServer.controller;

import java.util.HashMap;
import java.util.Map;

public class ResponseMsg {

    /**
     * Method for create error message
     * @param msg 	error message
     * @return error message
     */
    public Map<String,String> errResponse(String msg) {
        Map<String,String> result = new HashMap<>();
        result.put("result_code","0");
		result.put("result", "FAIL");
        result.put("msg", msg);
        return result;
    }

    /**
     * Method for create success message
     * @return success message
     */
    public Map<String,String> successResponse() {
        Map<String,String> result = new HashMap<>();
        result.put("result_code","1");
		result.put("result", "SUCCES");
        return result;
    }

}

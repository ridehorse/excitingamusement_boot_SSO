package com.exciting.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ChangeJson {
	
	public static JSONObject ToChangeJson(Object  data) {
		
		ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
		 String jsonStr;
		 JSONObject jsonObj = null;
		 
		 try {     
			 jsonStr = mapper.writeValueAsString(data);
		        
			 System.out.println(jsonStr);
		       
		     JSONParser jsonParser = new JSONParser();
			try {
				jsonObj = (JSONObject) jsonParser.parse(jsonStr);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		   
		 } catch (JsonProcessingException e) {
		   
			 e.printStackTrace();
		   
		 }
		 
		return jsonObj;
	}
	
}

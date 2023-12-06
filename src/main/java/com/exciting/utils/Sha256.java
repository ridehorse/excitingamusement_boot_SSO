package com.exciting.utils;


import java.security.MessageDigest;

import javax.mail.Authenticator;


public class Sha256 extends Authenticator {
	
	public static String getSHA256(String input) {
		StringBuffer result = new StringBuffer();
		System.out.println("getSAH256() 진입");
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] salt = "Hello This is salt.".getBytes();
			digest.reset();
			digest.update(salt);
			System.out.println("salt : "+salt);
			byte[] chars = digest.digest(input.getBytes("UTF-8"));
			for(int i = 0; i < chars.length; i++) {
				String hex = Integer.toHexString(0xff & chars[i]);
				if(hex.length() == 1) result.append("0");
				result.append(hex);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("getSAH256() 나감");
		return result.toString();
	}
}



package com.exciting.utils;

import java.io.File;

import org.springframework.stereotype.Component;

public class FileUtils {
	
	//폴더가 없을시 생성
	public static void createDirectory(String dirPath) {
		
	    File directory = new File(dirPath);

	    if (!directory.exists()) {
	        try {
	            directory.mkdirs(); 
	        } catch (Exception e) {
	            
	        }
	    }
	}
	
	
}

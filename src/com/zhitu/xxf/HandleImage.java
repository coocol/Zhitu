package com.zhitu.xxf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class HandleImage {

	
	public byte[] convertImageToByteArray(String filepath){
		byte[] array = null;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(filepath));
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int numReadBytes = 0;
			while((numReadBytes=inputStream.read(buffer))!=-1){
				outputStream.write(buffer,0, numReadBytes);
			}
			array = outputStream.toByteArray();
			outputStream.close();
			inputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return array;
	}
	
	public boolean convertByteArayToImage(byte[] array,String path){
		if(array.length<3 || array.equals("")){
			return false;
		}
		try{
			FileOutputStream outputStream = new FileOutputStream(new File(path));
			outputStream.write(array,0,array.length);
			outputStream.close();
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
		
	}
}

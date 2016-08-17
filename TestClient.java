package com.test;

public class TestClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String fileArray[] = {"C:\\TEST\\x12-1.txt","C:\\TEST\\x12-2.txt"};
		 ((AppendFiles) new AppendFiles()).readFile(fileArray);
		 
	}

}

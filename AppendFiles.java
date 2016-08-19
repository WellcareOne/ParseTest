package com.test;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;


public class AppendFiles {
	private  int numberOfLines=0;
	private int numberOfNumerics=0;
	private Date minDate;
	private Date maxDate;
	public  void readFile(String[] args) {
		BufferedReader br = null;
		BufferedWriter out = null;
		
		try{
			for (String textfile : args) {

			    br = new BufferedReader(new FileReader(textfile));
			    FileWriter filestream = new FileWriter("C:\\TEST\\x12-3.txt",true);
			    out = new BufferedWriter(filestream);
			      String strLine=null;
			       while ((strLine = br.readLine()) != null) {
			    	   numberOfNumerics = numberOfNumerics + strLine.replaceAll("\\D", "").length();
			    	  String newStr = strLine.replace('9', '5').toUpperCase();
			    	  numberOfLines++;
			    	  out.write(newStr);
			    	  out.newLine();
			     }
			      
			}
			
			out.write("No. of Line---" + numberOfLines);
			out.newLine();
			out.write("No. of Numerics" + numberOfNumerics);
		           
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if (br != null)br.close();
				if (out != null)out.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}			
		}
	}
}


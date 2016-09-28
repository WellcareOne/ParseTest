package com.wellcare.ponikvar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ParseFiles {

	public static void main(String[] args) {
		FileParser fp = new FileParser();
		try(BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("X12-3"))));
			BufferedReader br1 = new BufferedReader(new InputStreamReader(fp.getClass().getResourceAsStream("X12-1"), "UTF-8"));
			BufferedReader br2 = new BufferedReader(new InputStreamReader(fp.getClass().getResourceAsStream("X12-2"), "UTF-8"))) {
			fp.parseFiles(out, br1, br2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

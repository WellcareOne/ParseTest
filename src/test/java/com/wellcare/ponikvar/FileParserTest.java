package com.wellcare.ponikvar;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.Before;
import org.junit.Test;

public class FileParserTest {
	
	FileParser fp;
	
	String helper(String source, long lines, long numerics, String min, String max) throws IOException {
		StringWriter expected = new StringWriter();
		BufferedWriter out = new BufferedWriter(expected);
		out.write(source);
		out.newLine();
		out.write(String.format("Number of Lines: %d", lines));
		out.newLine();
		out.write(String.format("Number of Numerics: %d", numerics));
		out.newLine();
		out.write(String.format("Min Date: %s", min == null ? "No Dates Found" : min));
		out.newLine();
		out.write(String.format("Max Date: %s", max == null ? "No Dates Found" : max));
		out.flush();
		return expected.toString();
	}
	
	@Before
	public void setUp() throws Exception {
		fp = new FileParser();
	}
	
	@Test
	public void should_count_the_number_of_lines() throws IOException {
		StringWriter sw = new StringWriter();
		BufferedWriter bw = new BufferedWriter(sw);
		StringReader sr = new StringReader("a\r\nb\r\nc");
		BufferedReader br = new BufferedReader(sr);
		
		fp.parseFiles(bw, br);
		
		assertEquals(helper("a\r\nb\r\nc", 3, 0, null, null), sw.toString());
	}

	@Test
	public void should_convert_5_to_9() throws IOException {
		StringWriter sw = new StringWriter();
		BufferedWriter bw = new BufferedWriter(sw);
		StringReader sr = new StringReader("5555555555");
		BufferedReader br = new BufferedReader(sr);
		
		fp.parseFiles(bw, br);
		
		assertEquals(helper("9999999999", 1, 10, null, null), sw.toString());
	}
	
	@Test
	public void should_convert_uppercase_to_lowercase() throws IOException {
		StringWriter sw = new StringWriter();
		BufferedWriter bw = new BufferedWriter(sw);
		StringReader sr = new StringReader("ABCDEFG");
		BufferedReader br = new BufferedReader(sr);
		
		fp.parseFiles(bw, br);
		
		assertEquals(helper("abcdefg", 1, 0, null, null), sw.toString());
	}
	
	@Test
	public void should_find_min_max_dates() throws IOException {
		StringWriter sw = new StringWriter();
		BufferedWriter bw = new BufferedWriter(sw);
		StringReader sr = new StringReader("2010-01-012011-01-012012-01-012013-01-01");
		BufferedReader br = new BufferedReader(sr);
		
		fp.parseFiles(bw, br);
		
		assertEquals(helper("2010-01-012011-01-012012-01-012013-01-01", 1, 32, "01-01-10", "01-01-13"), sw.toString());
	}
	
	@Test
	public void should_combine_files_in_order() throws IOException {
		StringWriter sw = new StringWriter();
		BufferedWriter bw = new BufferedWriter(sw);
		StringReader sr1 = new StringReader("abc");
		BufferedReader br1 = new BufferedReader(sr1);
		StringReader sr2 = new StringReader("def");
		BufferedReader br2 = new BufferedReader(sr2);
		StringReader sr3 = new StringReader("ghi");
		BufferedReader br3 = new BufferedReader(sr3);
		
		fp.parseFiles(bw, br1, br2, br3);
		
		assertEquals(helper("abc\r\ndef\r\nghi", 3, 0, null, null), sw.toString());
	}

}

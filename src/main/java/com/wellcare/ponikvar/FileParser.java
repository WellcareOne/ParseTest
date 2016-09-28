package com.wellcare.ponikvar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser {

	private static final Pattern DATE_PATTERN = Pattern.compile("(\\d{4}-\\d{2}-\\d{2})");
	private static final DateFormat DATE_FORMAT_IN = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateFormat DATE_FORMAT_OUT = new SimpleDateFormat("MM-dd-yy");

	public void parseFiles(BufferedWriter out, BufferedReader... files) throws IOException {

		// Tracking stats
		long lineCount = 0;
		long numericCount = 0;
		List<Long> dates = new ArrayList<Long>();

		for (BufferedReader file : files) {

			String line = null;
			StringBuilder sb = null;
			while ((line = file.readLine()) != null) {
				lineCount += 1;
				sb = new StringBuilder();
				for (int i = 0; i < line.length(); i++) {
					char c = line.charAt(i);
					if (Character.isDigit(c)) {
						numericCount += 1;
						if (c == '5') {
							c = '9';
						}
					} else if (Character.isUpperCase(c)) {
						c = Character.toLowerCase(c);
					}
					sb.append(c);
				}
				line = sb.toString();

				// Now check for dates
				Matcher matcher = DATE_PATTERN.matcher(line);
				while (matcher.find()) {
					try {
						Date date = DATE_FORMAT_IN.parse(matcher.group());
						dates.add(date.getTime());
					} catch (ParseException e) {
						// TODO could keep a count of invalid date formats
						// for reporting/troubleshooting
					}
				}
				out.write(line);
				out.newLine();
			}
		}

		String minDate = dates.size() > 0 ? DATE_FORMAT_OUT.format(new Date(Collections.min(dates))) : "No Dates Found";
		String maxDate = dates.size() > 0 ? DATE_FORMAT_OUT.format(new Date(Collections.max(dates))) : "No Dates Found";

		// Write out the statistics
		out.write(String.format("Number of Lines: %d", lineCount));
		out.newLine();
		out.write(String.format("Number of Numerics: %d", numericCount));
		out.newLine();
		out.write(String.format("Min Date: %s", minDate));
		out.newLine();
		out.write(String.format("Max Date: %s", maxDate));
		out.flush();
	}
}

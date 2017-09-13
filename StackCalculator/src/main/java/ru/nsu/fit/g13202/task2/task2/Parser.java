package ru.nsu.fit.g13202.task2.task2;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Parser {

	public Parser(InputStream reader) {

		this.reader = reader;
		endOfStream = false;
		stringBuilder = new StringBuilder();
		

	}

	private String getWord() throws IOException {

		if (endOfStream) {
			return null;
		}

		stringBuilder.setLength(0);

		while (true) {
			i = reader.read();

			if (i == -1) {
				endOfStream = true;
				break;
			} else {
				ch = (char) i;
			}

			if (ch == '\r') {
				endOfLine = true;
				break;
			}

			if (!Character.isLetterOrDigit(ch) && !(ch == '.') && !(ch == '+')
					&& !(ch == '-') && !(ch == '*') && !(ch == '/')
					&& !(ch == '#')) {
				if (stringBuilder.length() != 0) {
					break;
				}
			} else {
				stringBuilder.append(ch);
			}
		}

		if (stringBuilder.length() == 0) {
			return null;
		}

		return stringBuilder.toString();

	}

	public List<String> getLine() throws IOException {
		List<String> line = new ArrayList<String>(10);

		if (endOfStream) {
			return null;
		}

		endOfLine = false;
		

		while (!endOfLine && !endOfStream) {
			word = getWord();
			if (word != null) {
				line.add(word);
			}
		}

		if(line.isEmpty()) {
			return null;
		}	
		
		return line;

	}

	private InputStream reader;
	private boolean endOfStream;
	private StringBuilder stringBuilder;
	private int i;
	private char ch;
	private boolean endOfLine;
	//private List<String> line;
	private String word;

}

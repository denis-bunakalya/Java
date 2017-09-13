package ru.nsu.fit.g13202.task2.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringBufferInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.FileHandler;

import org.junit.Test;

import ru.nsu.fit.g13202.task2.commands.*;
import ru.nsu.fit.g13202.task2.model.Command;
import ru.nsu.fit.g13202.task2.model.Context;
import ru.nsu.fit.g13202.task2.task2.Factory;
import ru.nsu.fit.g13202.task2.task2.Parser;

public class Tests {

	@Test
	public void test1() throws Exception {
		
		Parser parser = new Parser(new StringBufferInputStream("ONE TWO THREE"));
		List<String> line = parser.getLine();
		
		assertEquals("ONE", line.get(0));
		assertEquals("TWO", line.get(1));
		assertEquals("THREE", line.get(2));
		
	}
	
	@Test
	public void test2() throws Exception {
		
		Parser parser = new Parser(new StringBufferInputStream("01234.56789 #comments + - * / "));		
		List<String> line = parser.getLine();
		
		assertEquals("01234.56789", line.get(0));
		assertEquals("#comments", line.get(1));		
		assertEquals("+", line.get(2));
		assertEquals("-", line.get(3));
		assertEquals("*", line.get(4));
		assertEquals("/", line.get(5));
		
	}
	
	@Test
	public void test3() throws Exception {
		
		Parser parser = new Parser(new StringBufferInputStream("ONE   TWO   \r   THREE   FOUR  \r   "));		
		List<String> line = parser.getLine();
		
		assertEquals("ONE", line.get(0));
		assertEquals("TWO", line.get(1));
		
		List<String> line2 = parser.getLine();
		assertEquals("THREE", line2.get(0));
		assertEquals("FOUR", line2.get(1));
		assertEquals(null, parser.getLine());	
		
	}

	@Test
	public void test4() throws Exception {
		
		Context context = new Context(System.out);
		Command command;
		LinkedList<String> arguments = new LinkedList<String>();
		
		command = new Define();		
		arguments.add("a");
		arguments.add("4");
		command.execute(arguments, context);
		arguments.clear();
		
		command = new Push();
		arguments.add("a");
		command.execute(arguments, context);
		arguments.clear();
		
		command = new Sqrt();
		command.execute(arguments, context);
		
		assertEquals(new Double(2), context.getStack().peek());
		
	}
	
	@Test
	public void test5() throws Exception {
		
		FileHandler fh = new FileHandler("test.xml");
		Factory factory = new Factory(fh);
		
	}
	
}

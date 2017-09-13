package ru.nsu.fit.g13202.task2.model;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Context {

	public Context(OutputStream writer) {
		
		stack = new Stack<Double>();
		namedParameters = new HashMap<String, Double>();
		this.writer = writer; 
				
	}
	
	public Stack<Double> getStack() {
		
		return stack;
		
	}
	
	public Map<String, Double> getNamedParameters() {
		
		return namedParameters;
		
	}
	
	public OutputStream getWriter() {
		
		return writer;
		
	}
	
	private Stack<Double> stack;
	private Map<String, Double> namedParameters;
	private OutputStream writer;
	
}

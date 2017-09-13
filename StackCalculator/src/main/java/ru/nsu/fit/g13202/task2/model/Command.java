package ru.nsu.fit.g13202.task2.model;

import java.util.LinkedList;
import java.util.List;

public interface Command {	
	
	public void execute(List<String> arguments, Context context) throws Exception;
	
}

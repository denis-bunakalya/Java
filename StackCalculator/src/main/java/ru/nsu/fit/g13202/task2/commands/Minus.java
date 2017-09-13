package ru.nsu.fit.g13202.task2.commands;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.nsu.fit.g13202.task2.model.Command;
import ru.nsu.fit.g13202.task2.model.Context;
import ru.nsu.fit.g13202.task2.model.ExceptionArgumentsWrongNumber;
import ru.nsu.fit.g13202.task2.model.ExceptionCalculator;
import ru.nsu.fit.g13202.task2.model.ExceptionStackEmpty;
import ru.nsu.fit.g13202.task2.model.ExceptionStackOnlyOneElement;

public class Minus implements Command {

	private Double first;
	private Double second;
	private static Logger LOG = Logger.getLogger(Minus.class.getName());
	
	public void execute(List<String> arguments, Context context)
			throws ExceptionCalculator {		
		
		if (arguments.size() != 0) {
			String s = "Error: wrong number of arguments, "
					+ "the command \"-\" must have no argumnets";
			LOG.log(Level.WARNING, s);
			throw new ExceptionArgumentsWrongNumber(s);
		}

		try {
			first = context.getStack().pop();
		} catch (EmptyStackException e) {
			String s = "Error: the stack is empty, "
					+ "for the command \"-\" the stack must have at least two elements";
			LOG.log(Level.WARNING, s);
			throw new ExceptionStackEmpty(s);
		}

		try {
			second = context.getStack().pop();
		} catch (EmptyStackException e) {
			context.getStack().push(first);
			String s = "Error: the stack has only one element, "
					+ "for the command \"-\" the stack must have at least two elements";
			LOG.log(Level.WARNING, s);
			throw new ExceptionStackOnlyOneElement(s);
		}

		first = first - second;
		context.getStack().push(first);

	}	

}

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

public class Print implements Command {
	
	private Double topElement;
	private static Logger LOG = Logger.getLogger(Print.class.getName());

	public void execute(List<String> arguments, Context context)
			throws Exception {

		if (arguments.size() != 0) {
			String s = "Error: wrong number of arguments, "
					+ "the command \"PRINT\" must have no argumnets";
			LOG.log(Level.WARNING, s);
			throw new ExceptionArgumentsWrongNumber(s);
		}

		try {
			topElement = context.getStack().peek();
		} catch (EmptyStackException e) {
			String s = "Error: the stack is empty, "
					+ "for the command \"PRINT\" the stack must have at least one element";
			LOG.log(Level.WARNING, s);
			throw new ExceptionStackEmpty(s);
		}

		context.getWriter().write((topElement.toString() + "\n").getBytes());

	}

	

}

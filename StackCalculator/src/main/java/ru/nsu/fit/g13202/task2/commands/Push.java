package ru.nsu.fit.g13202.task2.commands;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.nsu.fit.g13202.task2.model.Command;
import ru.nsu.fit.g13202.task2.model.Context;
import ru.nsu.fit.g13202.task2.model.ExceptionArgumentsWrongFirst;
import ru.nsu.fit.g13202.task2.model.ExceptionArgumentsWrongNumber;
import ru.nsu.fit.g13202.task2.model.ExceptionCalculator;

public class Push implements Command {	
	 
	private Double argument;
	private static Logger LOG = Logger.getLogger(Push.class.getName());

	public void execute(List<String> arguments, Context context)
			throws ExceptionCalculator {

		if (arguments.size() != 1) {
			String s = "Error: wrong number of arguments, "
					+ "the command \"PUSH\" must have one argumnet";
			LOG.log(Level.WARNING, s);
			throw new ExceptionArgumentsWrongNumber(s);
		}

		try {
			argument = new Double(arguments.get(0));
		} catch (NumberFormatException e) {
			argument = context.getNamedParameters().get(arguments.get(0));
		}

		if (argument == null) {
			String s = "Error: wrong argument, the argument of the command \"PUSH\" "
					+ "must be either a number or a named parameter";
			LOG.log(Level.WARNING, s);
			throw new ExceptionArgumentsWrongFirst(s);
		}

		context.getStack().push(argument);

	}	

}

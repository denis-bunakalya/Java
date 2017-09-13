package ru.nsu.fit.g13202.task2.commands;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.nsu.fit.g13202.task2.model.Command;
import ru.nsu.fit.g13202.task2.model.Context;
import ru.nsu.fit.g13202.task2.model.ExceptionArgumentsWrongFirst;
import ru.nsu.fit.g13202.task2.model.ExceptionArgumentsWrongNumber;
import ru.nsu.fit.g13202.task2.model.ExceptionArgumentsWrongSecond;
import ru.nsu.fit.g13202.task2.model.ExceptionCalculator;

public class Define implements Command {

	private Double argument;
	private static Logger LOG = Logger.getLogger(Define.class.getName());

	public void execute(List<String> arguments, Context context)
			throws ExceptionCalculator {

		if (arguments.size() != 2) {
			String s = "Error: wrong number of arguments, "
					+ "the command \"DEFINE\" must have 2 argumnets: "
					+ "parameter name and value";
			LOG.log(Level.WARNING, s);
			throw new ExceptionArgumentsWrongNumber(s);
		}

		try {
			Double.parseDouble(arguments.get(0));

			String s = "Error: wrong first argument, "
					+ "the first argument of the command \"DEFINE\" can't be a number, "
					+ "it must be a name";
			LOG.log(Level.WARNING, s);
			throw new ExceptionArgumentsWrongFirst(s);
		} catch (NumberFormatException e) {
			try {
				argument = new Double(arguments.get(1));

				context.getNamedParameters().put(arguments.get(0), argument);
			} catch (NumberFormatException e1) {
				String s = "Error: wrong second argument, "
						+ "the second argument of the command \"DEFINE\" must be a number";
				LOG.log(Level.WARNING, s);
				throw new ExceptionArgumentsWrongSecond(s);
			}
		}

	}

}

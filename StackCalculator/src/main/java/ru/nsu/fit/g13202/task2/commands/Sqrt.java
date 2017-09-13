package ru.nsu.fit.g13202.task2.commands;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.nsu.fit.g13202.task2.model.Command;
import ru.nsu.fit.g13202.task2.model.Context;
import ru.nsu.fit.g13202.task2.model.ExceptionArgumentsWrongNumber;
import ru.nsu.fit.g13202.task2.model.ExceptionArithmeticSquareRootOfNegativeNumber;
import ru.nsu.fit.g13202.task2.model.ExceptionCalculator;
import ru.nsu.fit.g13202.task2.model.ExceptionStackEmpty;

public class Sqrt implements Command {

	private Double topElement;
	private static Logger LOG = Logger.getLogger(Sqrt.class.getName());
	
	public void execute(List<String> arguments, Context context)
			throws ExceptionCalculator {

		if (arguments.size() != 0) {
			String s = "Error: wrong number of arguments, "
					+ "the command \"SQRT\" must have no argumnets";
			LOG.log(Level.WARNING, s);
			throw new ExceptionArgumentsWrongNumber(s);
		}

		try {
			topElement = context.getStack().pop();
		} catch (EmptyStackException e) {
			String s = "Error: the stack is empty, "
					+ "for the command \"SQRT\" the stack must have at least one element";
			LOG.log(Level.WARNING, s);
			throw new ExceptionStackEmpty(s);
		}

		if (topElement < 0) {
			context.getStack().push(topElement);
			String s = "Error: square root of a negative number, "
					+ "for the command \"SQRT\" the first element in the stack "
					+ "must be greater than or equal to zero";
			LOG.log(Level.WARNING, s);
			throw new ExceptionArithmeticSquareRootOfNegativeNumber(s);
		}

		topElement = Math.sqrt(topElement);
		context.getStack().push(topElement);

	}

}

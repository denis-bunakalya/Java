package ru.nsu.fit.g13202.task2.task2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.nsu.fit.g13202.task2.commands.Define;
import ru.nsu.fit.g13202.task2.model.Command;
import ru.nsu.fit.g13202.task2.model.Context;
import ru.nsu.fit.g13202.task2.model.ExceptionCalculator;

public class Controller {

	private static Logger LOG = Logger.getLogger(Controller.class.getName());

	public Controller(InputStream reader, OutputStream writer) {

		this.reader = reader;
		this.writer = writer;

	}

	public void startProcess() {

		Context context = new Context(writer);
		List<String> line;

		try {
			// read the data here
			Parser parser = new Parser(reader);
			Command command = null;
			String commandName;
			Factory factory;
			try {
				factory = new Factory(fh);
			} catch (Exception e) {
				LOG.log(Level.WARNING, e.getMessage());
				return;
			}

			while (true) {
				line = parser.getLine();
				if (line == null) {
					break;
				}

				commandName = line.get(0);
				if (commandName.charAt(0) == '#') {
					continue;
				}
				line.remove(0);

				command = factory.create(commandName);
				if (command == null) {
					LOG.log(Level.WARNING,
							"Error: there is no such command - \""
									+ commandName + "\"");
					continue;
				}

				try {
					command.execute(line, context);
				} catch (ExceptionCalculator e) {
					// LOG.log(Level.WARNING, e.getMessage());
				} catch (Exception e) {
					LOG.log(Level.WARNING, e.getMessage());
				}
			}

		} catch (IOException e) {
			LOG.log(Level.SEVERE,
					"Error while reading file: " + e.getLocalizedMessage());
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace(System.err);
			} finally {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace(System.err);
				}

			}
		}

	}

	private InputStream reader;
	private OutputStream writer;
	private FileHandler fh;

}

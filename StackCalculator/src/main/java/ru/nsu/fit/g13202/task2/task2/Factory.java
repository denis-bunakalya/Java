package ru.nsu.fit.g13202.task2.task2;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.nsu.fit.g13202.task2.commands.Define;
import ru.nsu.fit.g13202.task2.model.Command;

public class Factory {

	private static Logger LOG = Logger.getLogger(Factory.class.getName());

	public Factory(FileHandler fh) throws Exception {

		hm = new HashMap<String, Class>();
		makeHashMap();

		this.fh = fh;
		loggers = new HashSet<Logger>();

	}

	private void makeHashMap() throws Exception {

		InputStream reader = null;
		try {
			reader = this.getClass().getResourceAsStream("/factory_config.txt");
		} catch (Exception e) {
		}

		if (reader == null) {
			throw new Exception("Error: factory_config.txt not found");
		}

		Parser parser = new Parser(reader);
		List<String> line;

		try {
			// read the data here
			while (true) {
				line = parser.getLine();
				if ((line == null) || (line.get(0) == null)) {
					break;
				}

				if (line.size() != 2) {
					throw new Exception(
							"Error: wrong format of the file \"factory_config.txt\", "
									+ "each line in the file \"factory_config.txt\" must have "
									+ "two elements - command name and class path");
				}

				try {
					hm.put(line.get(0), Class.forName(line.get(1)));
				} catch (ClassNotFoundException e) {
					throw new Exception(
							"Error: command class described in \"factory_config.txt\" not found, "
									+ "for the command \"" + line.get(0)
									+ "\" there is no  class "
									+ "at this path - " + line.get(1));
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
			}
		}

	}

	public Command create(String commandName) {
		try {
			commandClass = hm.get(commandName);
			if (commandClass == null) {
				return null;
			}

			return (Command) commandClass.newInstance();
		} catch (Exception e) {
			LOG.log(Level.WARNING, e.getClass() + " : " + e.getMessage());
		}
		return null;
	}

	private HashMap<String, Class> hm;
	private Class commandClass;
	private FileHandler fh;
	private HashSet<Logger> loggers;
	private Logger logger;

}

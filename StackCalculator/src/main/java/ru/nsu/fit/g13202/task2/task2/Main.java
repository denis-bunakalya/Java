package ru.nsu.fit.g13202.task2.task2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import ru.nsu.fit.g13202.task2.commands.Define;

public class Main {

	private static Logger LOG = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) {

		if (args.length > 2) {
			System.out
					.println("Wrong number of arguments. There should be one, two or no arguments: "
							+ "(input file name) (output file name)");
			return;
		}

		LogManager logManager = LogManager.getLogManager();
		try {
			logManager.readConfiguration(logManager.getClass()
					.getResourceAsStream("/logging.properties"));
		} catch (Exception e) {
			System.out
					.println("Error while opening logging configuration file: "
							+ e.getClass() + " " + e.getMessage());
			return;
		}

		InputStream reader = null;
		if (args.length > 0) {
			try {
				reader = new FileInputStream(args[0]);
			} catch (IOException e) {
				LOG.log(Level.SEVERE,
						"Error while reading file: " + e.getLocalizedMessage());
				return;
			}
		} else {
			reader = System.in;
		}

		OutputStream writer = null;
		if (args.length > 1) {
			try {
				writer = new FileOutputStream(args[1]);
			} catch (IOException e) {
				LOG.log(Level.SEVERE,
						"Error while writing file: " + e.getLocalizedMessage());
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace(System.err);
				}
				return;
			}
		} else {
			writer = System.out;
		}

		Controller controller = new Controller(reader, writer);
		controller.startProcess();

	}

}

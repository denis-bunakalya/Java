import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

public class Main {

	public static void main(String[] args) {

		if (args.length != 2) {
			System.out
					.println("Wrong number of arguments. There should be two arguments: "
							+ "input file name, output file name");
			return;
		}		

		Reader reader = null;
		try {
			reader = new InputStreamReader(new FileInputStream(args[0]));
		} catch (IOException e) {
			System.err.println("Error while reading file: "
					+ e.getLocalizedMessage());
			return;
		}

		Writer writer = null;
		try {
			writer = new OutputStreamWriter(new FileOutputStream(args[1]));
		} catch (IOException e) {
			System.err.println("Error while writing file: "
					+ e.getLocalizedMessage());
			try {
				reader.close();
			} catch (IOException e1) {
				e1.printStackTrace(System.err);
			}
			return;
		}

		Controller controller = new Controller(reader, writer);
		controller.startProcess();

	}

}

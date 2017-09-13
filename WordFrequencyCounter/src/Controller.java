import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class Controller {

	public Controller(Reader reader, Writer writer) {

		this.reader = reader;
		this.writer = writer;

	}

	public void startProcess() {

		StatStorage statStorage = new StatStorage();

		try {
			// read the data here
			Parser parser = new Parser(reader);
			String word;

			while (true) {
				word = parser.getWord();
				if (word == null) {
					break;
				}

				statStorage.saveWord(word);
			}
		} catch (IOException e) {
			System.err.println("Error while reading file: "
					+ e.getLocalizedMessage());
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace(System.err);
			}
		}

		try {
			// write the data here
			ReportGenerator reportGenerator = new ReportGenerator();
			reportGenerator.generateReport(statStorage.getMap(), writer);
		} catch (IOException e) {
			System.err.println("Error while writing file: "
					+ e.getLocalizedMessage());
		} finally {
			if (null != writer) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}

	private Reader reader;
	private Writer writer;

}

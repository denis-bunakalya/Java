import java.io.IOException;
import java.io.Reader;

public class Parser {

	public Parser(Reader reader) {

		this.reader = reader;
		endOfFile = false;
		sb = new StringBuilder();

	}

	public String getWord() throws IOException {
		if (endOfFile) {
			return null;
		}

		sb.setLength(0);

		while (true) {
			i = reader.read();

			if (i == -1) {
				endOfFile = true;
				break;
			} else {
				ch = (char) i;
			}

			if (!Character.isLetterOrDigit(ch)) {
				if (sb.length() != 0) {
					break;
				}
			} else {
				sb.append(ch);
			}
		}

		return sb.toString();

	}

	private Reader reader;
	private boolean endOfFile;
	private StringBuilder sb;
	private int i;
	private char ch;

}

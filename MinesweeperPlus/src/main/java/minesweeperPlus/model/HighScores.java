package minesweeperPlus.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class HighScores {

	private TreeMap<Integer, String> linearHighScores;
	private TreeMap<Integer, String> quadraticHighScores;

	public HighScores() {

		linearHighScores = null;
		quadraticHighScores = null;
	}

	public EnumNewRecord newRecord(int time, int fieldSize) {

		try {
			readHighScores();
		} catch (Exception e) {
		}
		EnumNewRecord result = EnumNewRecord.NO;

		if ((linearHighScores == null) || (linearHighScores.size() == 0)
				|| ((1000 - time) * fieldSize > linearHighScores.firstKey())) {
			result = EnumNewRecord.LINEAR;
		}
		if ((quadraticHighScores == null) || (quadraticHighScores.size() == 0)
				|| ((1000 - time) * fieldSize * fieldSize > quadraticHighScores.firstKey())) {

			if (result == EnumNewRecord.NO) {
				result = EnumNewRecord.QUADRATIC;
			} else {
				result = EnumNewRecord.BOTH;
			}
		}
		return result;
	}

	public Set<Entry<Integer, String>> getLinearHighScores() throws Exception {

		readHighScores();
		return linearHighScores.entrySet();
	}

	public Set<Entry<Integer, String>> getQuadraticHighScores() throws Exception {

		readHighScores();
		return quadraticHighScores.entrySet();
	}

	public void readHighScores() throws Exception {

		if ((linearHighScores != null) && (quadraticHighScores != null)) {
			return;
		}

		linearHighScores = new TreeMap<Integer, String>(Collections.reverseOrder());
		quadraticHighScores = new TreeMap<Integer, String>(Collections.reverseOrder());

		FileReader fileReader = null;
		try {
			fileReader = new FileReader("high_scores.msphs");
		} catch (Exception e) {
		}

		if (fileReader == null) {

			linearHighScores = null;
			quadraticHighScores = null;
			throw new Exception("There are no records yet. Be the first! \n");
		}

		String score;
		String name;
		String type;

		BufferedReader bufferedReader = new BufferedReader(fileReader);
		while (true) {
			try {
				score = bufferedReader.readLine();
				name = bufferedReader.readLine();
				type = bufferedReader.readLine();

			} catch (Exception e) {

				bufferedReader.close();
				fileReader.close();
				throw e;
			}

			if (score == null) {
				break;
			}

			try {
				if (name == null) {
					throw new Exception();
				}
				if ("linear".equals(type)) {
					linearHighScores.put(Integer.parseInt(score), name);

				} else if ("quadratic".equals(type)) {
					quadraticHighScores.put(Integer.parseInt(score), name);

				} else {
					throw new Exception();
				}
			} catch (Exception e) {

				bufferedReader.close();
				fileReader.close();
				throw new Exception("Wrong format of the file \"high_scores.msphs\" \n");
			}
		}

		bufferedReader.close();
		fileReader.close();
	}

	public void updateHighScores(String name, int time, int fieldSize, String type) throws Exception {

		if ((linearHighScores == null) || (quadraticHighScores == null)) {

			linearHighScores = new TreeMap<Integer, String>(Collections.reverseOrder());
			quadraticHighScores = new TreeMap<Integer, String>(Collections.reverseOrder());
		}
		int score = 0;

		if ("linear".equals(type)) {
			score = (1000 - time) * fieldSize;
			linearHighScores.put(score, name);

		} else if ("quadratic".equals(type)) {
			score = (1000 - time) * fieldSize * fieldSize;
			quadraticHighScores.put(score, name);

		} else {
			throw new Exception();
		}

		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter("high_scores.msphs", true);
		} catch (Exception e) {
		}

		if (fileWriter == null) {
			throw new Exception("Error while opening or creating high_scores.msphs");
		}

		try {
			fileWriter.write(score + "\r\n" + name + "\r\n" + type + "\r\n");
		} catch (Exception e) {

			throw new Exception("Error while writing to high_scores.msphs");
		} finally {
			fileWriter.close();
		}
	}

}

package minesweeperPlus.view;

import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JOptionPane;

import minesweeperPlus.model.Model;

public class HighScoresForm {

	public static void show(Model model) {

		StringBuilder result = new StringBuilder();

		Set<Entry<Integer, String>> linearHighScores = null;
		Set<Entry<Integer, String>> quadraticHighScores = null;
		try {
			linearHighScores = model.getLinearHighScores();
			quadraticHighScores = model.getQuadraticHighScores();

		} catch (Exception e) {
			result.append(e.getMessage() + "\n");
		}

		if (linearHighScores != null) {

			result.append("linear: \n");
			for (Entry<Integer, String> entry : linearHighScores) {
				result.append(entry.getKey() + " - " + entry.getValue() + "\n");
			}
			result.append("\n");
		}

		if (quadraticHighScores != null) {

			result.append("quadratic: \n");
			for (Entry<Integer, String> entry : quadraticHighScores) {
				result.append(entry.getKey() + " - " + entry.getValue() + "\n");
			}
		}
		result.append("\n");

		JOptionPane.showMessageDialog(null, result);
	}

}

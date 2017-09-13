package minesweeperPlus.view;

import javax.swing.JOptionPane;

import minesweeperPlus.model.EnumNewRecord;
import minesweeperPlus.model.Model;

public class NameEntryForm {

	public static void show(Model model) {

		String name = null;
		boolean wrongName = true;

		while (wrongName) {
			name = JOptionPane.showInputDialog("New record!!! Please enter your name: ");

			if (name == null) {
				return;
			}
			if (name.equals("")) {
				continue;
			}
			wrongName = false;

			for (char c : name.toCharArray()) {

				if (!Character.isLetterOrDigit(c)) {
					wrongName = true;
					break;
				}
			}
		}

		int typeNumber = 0;
		if (model.newRecord() == EnumNewRecord.BOTH) {

			typeNumber = JOptionPane.showOptionDialog(null, "Select the record type", "Select the record type",
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					new String[] { "linear", "quadratic" }, "linear");
		}

		String type = (typeNumber == 1) ? "quadratic" : "linear";

		try {
			model.updateHighScores(name, type);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), null, 0);
			return;
		}
		HighScoresForm.show(model);
	}

}

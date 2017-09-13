package minesweeperPlus.view;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import minesweeperPlus.model.Model;

public class SelectFileForm {

	private Model model;
	private JFileChooser fileChooser;

	public SelectFileForm(Model model) {

		this.model = model;
		fileChooser = new JFileChooser(new File("."));
	}

	public void showSaveDialog() {

		fileChooser.setSelectedFile(new File("save1.msplus"));
		int state = fileChooser.showSaveDialog(null);
		
		String fileName = getFileName(state);

		if (fileName == null) {
			return;
		}

		try {
			model.saveGame(fileName);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), null, 0);
		}
	}

	public void showOpenDialog() {

		fileChooser.setSelectedFile(null);
		int state = fileChooser.showOpenDialog(null);
		
		String fileName = getFileName(state);

		if (fileName == null) {
			return;
		}

		try {
			model.loadGame(fileName);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), null, 0);
		}
	}

	private String getFileName(int state) {

		String fileName = null;
		if (state == JFileChooser.APPROVE_OPTION) {

			File file = fileChooser.getSelectedFile();
			fileName = file.getName();
		}

		return fileName;
	}

}
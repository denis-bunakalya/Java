package minesweeperPlus.view;

import java.awt.GridBagLayout;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import minesweeperPlus.model.EnumNewRecord;
import minesweeperPlus.model.InterfaceTimeDisplay;
import minesweeperPlus.model.InterfaceView;
import minesweeperPlus.model.Model;

public class View implements InterfaceView, InterfaceTimeDisplay {

	private Model model;
	private JFrame mainWindow;

	private GameField gameField;
	private Map<String, ImageIcon> icons;

	private InfoPanel infoPanel;

	public View(Model model) {

		this.model = model;

		mainWindow = new JFrame("MinesweeperPlus");

		mainWindow.setResizable(false);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setLayout(new GridBagLayout());

		MenuPanel.makeMenuPanel(mainWindow, model);

		loadIcons();
		gameField = new GameField(mainWindow, this.model, icons);

		infoPanel = new InfoPanel(mainWindow, model);

		mainWindow.pack();
		mainWindow.setLocationRelativeTo(null);
	}

	public void run() {

		mainWindow.setVisible(true);
	}

	public void notifyModelChanged() {

		gameField.updateField();
		infoPanel.updateNumberOfFlagsLabel();
		infoPanel.updateGameStateLabel();

		if (model.newRecord() != EnumNewRecord.NO) {
			NameEntryForm.show(model);
		}
	}

	public void notifyTimerChanged() {

		infoPanel.updateTimeElapsedLabel();
	}

	public void notifyNewGame() {

		gameField.newGameField();
		gameField.updateField();

		infoPanel.updateNumberOfFlagsLabel();
		infoPanel.updateGameStateLabel();
		infoPanel.updateTimeElapsedLabel();

		mainWindow.pack();
		mainWindow.setLocationRelativeTo(null);
	}

	private void loadIcons() {

		icons = new HashMap<String, ImageIcon>();

		StringBuilder missingImages = new StringBuilder();

		loadImage("hidden", missingImages);
		loadImage("flag", missingImages);

		loadImage("mine", missingImages);
		loadImage("question", missingImages);

		for (int i = 0; i <= 8; i++) {
			loadImage(String.valueOf(i), missingImages);
		}

		if (missingImages.length() != 0) {

			JOptionPane.showMessageDialog(null, "The following files were not found: \n" + missingImages, null, 0);
		}
	}

	private void loadImage(String iconName, StringBuilder missingImages) {

		String filename = "/images/" + iconName + ".jpg";
		URL url;

		url = this.getClass().getResource(filename);

		if (url == null) {
			missingImages.append(filename + "\n");
		} else {
			icons.put(iconName, new ImageIcon(url));
		}
	}
}

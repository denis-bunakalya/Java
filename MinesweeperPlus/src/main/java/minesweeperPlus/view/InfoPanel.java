package minesweeperPlus.view;

import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import minesweeperPlus.model.EnumGameState;
import minesweeperPlus.model.Model;

public class InfoPanel {

	private JFrame mainWindow;
	private Model model;
	
	private JLabel gameStateLabel;
	private JLabel numberOfFlagsLabel;
	private JLabel timeElapsedLabel;

	public InfoPanel(JFrame mainWindow, Model model) {

		this.mainWindow = mainWindow;
		this.model = model;

		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;

		JPanel infoPanel = new JPanel();
		mainWindow.add(infoPanel, c);
		
		timeElapsedLabel = new JLabel();
		updateTimeElapsedLabel();
		infoPanel.add(timeElapsedLabel);
		
		numberOfFlagsLabel = new JLabel();
		updateNumberOfFlagsLabel();
		infoPanel.add(numberOfFlagsLabel);

		gameStateLabel = new JLabel();
		mainWindow.add(gameStateLabel);
	}
	
	public void updateTimeElapsedLabel() {

		timeElapsedLabel.setText("   Time elapsed = " + model.getTime()
				+ "   ");
	}
	
	public void updateNumberOfFlagsLabel() {

		numberOfFlagsLabel.setText("Number of flags = "
				+ model.getNumberOfFlags() + "   ");
	}

	public void updateGameStateLabel() {

		gameStateLabel.setText("");

		if (model.getGameState() == EnumGameState.DEFEAT) {
			gameStateLabel.setText("You have lost. Try again.");
			mainWindow.pack();

		} else if (model.getGameState() == EnumGameState.VICTORY) {
			gameStateLabel.setText("Congratulations! You have won!");
			mainWindow.pack();
		}
	}

}

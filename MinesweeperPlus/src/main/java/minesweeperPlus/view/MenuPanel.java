package minesweeperPlus.view;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import minesweeperPlus.model.Model;

public class MenuPanel {

	public static void makeMenuPanel(JFrame mainWindow, final Model model) {

		JPanel menuPanel = new JPanel();

		JButton newGameButton = new JButton("New Game");
		newGameButton.setFocusable(false);

		final NewGameForm newGameForm = new NewGameForm(mainWindow, model);

		newGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				newGameForm.show();
			}
		});
		menuPanel.add(newGameButton);
		
		JButton saveGameButton = new JButton("Save Game");
		saveGameButton.setFocusable(false);

		final SelectFileForm selectFileForm = new SelectFileForm(model);

		saveGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				selectFileForm.showSaveDialog();
			}
		});
		menuPanel.add(saveGameButton);

		JButton loadGameButton = new JButton("Load Game");
		loadGameButton.setFocusable(false);

		loadGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				selectFileForm.showOpenDialog();
			}
		});
		menuPanel.add(loadGameButton);

		JButton highScoresButton = new JButton("High Scores");
		highScoresButton.setFocusable(false);

		highScoresButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				HighScoresForm.show(model);
			}
		});
		menuPanel.add(highScoresButton);

		JButton aboutButton = new JButton("About");
		aboutButton.setFocusable(false);

		aboutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(null, model.about());
			}
		});
		menuPanel.add(aboutButton);

		JButton exitButton = new JButton("Exit");
		exitButton.setFocusable(false);

		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		menuPanel.add(exitButton);

		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;
		mainWindow.add(menuPanel, c);
	}

}

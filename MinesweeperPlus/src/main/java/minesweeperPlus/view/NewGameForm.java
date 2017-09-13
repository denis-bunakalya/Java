package minesweeperPlus.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import minesweeperPlus.model.Model;

public class NewGameForm {

	private JDialog newGameFrame;

	public NewGameForm(JFrame mainWindow, final Model model) {

		newGameFrame = new JDialog(mainWindow, "New Game", true);
		newGameFrame.setLayout(new GridBagLayout());
		newGameFrame.setResizable(false);

		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;

		newGameFrame.add(new JLabel("   Please enter the width, height and number of mines:   "), c);

		JPanel inputPanel = new JPanel(new GridLayout(0, 2));
		newGameFrame.add(inputPanel, c);

		inputPanel.add(new JLabel(" width (1-" + Model.MAX_WIDTH + "): "));

		final JTextField widthInputField = new JTextField();

		widthInputField.setText("9");
		inputPanel.add(widthInputField);

		inputPanel.add(new JLabel(" height (1-" + Model.MAX_HEIGHT + "): "));

		final JTextField heightInputField = new JTextField();

		heightInputField.setText("9");
		inputPanel.add(heightInputField);

		inputPanel.add(new JLabel(" number of mines: "));

		final JTextField numberOfMinesInputField = new JTextField();

		numberOfMinesInputField.setText("10");
		inputPanel.add(numberOfMinesInputField);

		JButton startNewGameButton = new JButton("Start New Game");
		startNewGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				int width = -1;
				int height = -1;
				int numberOfMines = -1;

				try {
					width = Integer.parseUnsignedInt(widthInputField.getText());
					height = Integer.parseUnsignedInt(heightInputField.getText());
					numberOfMines = Integer.parseUnsignedInt(numberOfMinesInputField.getText());
					
				} catch (Exception e) {
					System.err.println(e);
				}

				try {
					model.newGame(width, height, numberOfMines);
				} catch (Exception e) {
					
					JOptionPane.showMessageDialog(null, e.getMessage(), null, 0);
					System.err.println(e);
					return;
				}
				newGameFrame.setVisible(false);
			}
		});
		newGameFrame.add(startNewGameButton);

		newGameFrame.pack();
		newGameFrame.setLocationRelativeTo(null);
	}

	public void show() {

		newGameFrame.setVisible(true);
		newGameFrame.toFront();
		return;
	}

}

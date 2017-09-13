package minesweeperPlus.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import minesweeperPlus.model.EnumCellState;
import minesweeperPlus.model.EnumGameState;
import minesweeperPlus.model.Model;

public class GameField {

	private JPanel gameField;

	private int width;
	private int height;

	private JButton[][] fieldButtons;
	private Map<String, ImageIcon> icons;

	private Model model;

	public GameField(JFrame mainWindow, Model model, Map<String, ImageIcon> icons) {

		this.model = model;
		this.icons = icons;

		gameField = new JPanel();

		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;
		mainWindow.add(gameField, c);
	}

	public void newGameField() {

		width = model.getWidth();
		height = model.getHeight();

		gameField.removeAll();
		gameField.setLayout(new GridLayout(height, width));

		fieldButtons = new JButton[height][width];

		for (int i = height - 1; i >= 0; i--) {
			for (int j = 0; j < width; j++) {

				fieldButtons[i][j] = new JButton();

				fieldButtons[i][j].setPreferredSize(new Dimension(32, 32));
				fieldButtons[i][j].setFont(new Font(null, 1, 20));
				fieldButtons[i][j].setMargin(new Insets(0, 0, 0, 0));

				fieldButtons[i][j].addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent event) {

						JButton thisButton = (JButton) event.getSource();
						int x = thisButton.getX() / thisButton.getWidth();
						int y = thisButton.getY() / thisButton.getHeight();

						if (SwingUtilities.isLeftMouseButton(event)) {
							model.open(x, height - 1 - y);
						} else {
							model.flag(x, height - 1 - y);
						}
					}
				});
				gameField.add(fieldButtons[i][j]);
			}
		}
	}

	public void updateField() {

		String s = null;
		Icon icon = null;

		for (int i = height - 1; i >= 0; i--) {
			for (int j = 0; j < width; j++) {
				icon = null;

				if ((model.getGameState() == EnumGameState.DEFEAT) && (model.isMine(j, i))) {
					s = "*";
					icon = icons.get("mine");

				} else if (model.getCellState(j, i) == EnumCellState.HIDDEN) {
					s = "x";
					icon = icons.get("hidden");

				} else if (model.getCellState(j, i) == EnumCellState.FLAGED) {
					s = "F";
					icon = icons.get("flag");

				} else if (model.getCellState(j, i) == EnumCellState.QUESTION) {
					s = "?";
					icon = icons.get("question");

				} else if (model.getNumberOfMinesAround(j, i) == 0) {
					s = " ";
					icon = icons.get("0");

				} else {
					s = String.valueOf(model.getNumberOfMinesAround(j, i));
					icon = icons.get(String.valueOf(model.getNumberOfMinesAround(j, i)));
				}

				if (icon == null) {
					fieldButtons[i][j].setText(s);

				} else {
					fieldButtons[i][j].setIcon(icon);
					fieldButtons[i][j].setText("");
				}
			}
		}
	}
}

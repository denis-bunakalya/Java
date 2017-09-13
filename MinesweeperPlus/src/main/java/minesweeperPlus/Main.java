package minesweeperPlus;

import minesweeperPlus.model.Model;
import minesweeperPlus.view.View;

public class Main {

	public static void main(String[] args) throws Exception {

		Model model = new Model();
		View view = new View(model);

		model.subscribeView(view);
		model.subscribeTimeDisplay(view);

		model.newGame(9, 9, 10);
		view.run();
	}
}

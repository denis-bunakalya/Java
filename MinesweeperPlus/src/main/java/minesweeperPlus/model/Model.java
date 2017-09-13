package minesweeperPlus.model;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

public class Model {

	public static final int MAX_WIDTH = 20;
	public static final int MAX_HEIGHT = 20;

	private Field field;
	private EnumGameState gameState;
	private MinesweeperTimer minesweeperTimer;

	private Set<InterfaceView> subscribers;
	private int numberOfFlags;

	private HighScores highScores;
	private EnumNewRecord newRecord;

	public Model() {

		field = new Field();
		minesweeperTimer = new MinesweeperTimer();

		subscribers = new HashSet<InterfaceView>();
		highScores = new HighScores();
	}

	public void newGame(int width, int height, int numberOfMines) throws Exception {

		if ((width <= 0) || (width > MAX_WIDTH)) {
			throw new Exception("Wrong width. It should be a number from 1 to " + MAX_WIDTH + ".\n");
		}

		if ((height <= 0) || (height > MAX_HEIGHT)) {
			throw new Exception("Wrong height. It should be a number from 1 to " + MAX_HEIGHT + ".\n");
		}

		if ((numberOfMines < 0) || (numberOfMines > width * height)) {
			throw new Exception("Wrong number of mines. It should be a number from 0 to width*height.\n");
		}

		field.newField(width, height, numberOfMines);
		numberOfFlags = numberOfMines;
		gameState = EnumGameState.PLAYING;

		newRecord = EnumNewRecord.NO;
		minesweeperTimer.resetAndStart();
		notifyNewGame();
	}

	private void notifyNewGame() {

		for (InterfaceView view : subscribers) {
			view.notifyNewGame();
		}
	}

	public void open(int x, int y) {

		if ((field.getCellState(x, y) == EnumCellState.OPENED) || (gameState != EnumGameState.PLAYING)) {
			return;
		}

		field.open(x, y);

		if (field.isMine(x, y)) {
			minesweeperTimer.stop();
			gameState = EnumGameState.DEFEAT;

			notifySubscribers();
			return;
		}

		if (field.isVictory()) {
			minesweeperTimer.stop();
			gameState = EnumGameState.VICTORY;

			newRecord = highScores.newRecord(getTime(), field.getHeight() * field.getWidth());
		}

		notifySubscribers();
		newRecord = EnumNewRecord.NO;
	}

	public void flag(int x, int y) {

		if ((gameState != EnumGameState.PLAYING) || (field.getCellState(x, y) == EnumCellState.OPENED)) {
			return;
		}

		numberOfFlags += field.flag(x, y);
		notifySubscribers();
	}

	public int getWidth() {

		return field.getWidth();
	}

	public int getHeight() {

		return field.getHeight();
	}

	public boolean isMine(int x, int y) {

		return field.isMine(x, y);
	}

	public EnumCellState getCellState(int x, int y) {

		return field.getCellState(x, y);
	}

	public int getNumberOfMinesAround(int x, int y) {

		return field.getNumberOfMinesAround(x, y);
	}

	public int getTime() {

		return minesweeperTimer.getTime();
	}

	public EnumGameState getGameState() {
		return gameState;
	}

	public int getNumberOfFlags() {

		return numberOfFlags;
	}

	public Set<Entry<Integer, String>> getLinearHighScores() throws Exception {

		return highScores.getLinearHighScores();
	}

	public Set<Entry<Integer, String>> getQuadraticHighScores() throws Exception {

		return highScores.getQuadraticHighScores();
	}

	public void updateHighScores(String name, String type) throws Exception {

		if (newRecord == EnumNewRecord.BOTH) {
			highScores.updateHighScores(name, getTime(), field.getHeight() * field.getWidth(), type);
		} else {
			highScores.updateHighScores(name, getTime(), field.getHeight() * field.getWidth(),
					newRecord.toString().toLowerCase());
		}
	}

	public EnumNewRecord newRecord() {

		return newRecord;
	}

	private void notifySubscribers() {

		for (InterfaceView view : subscribers) {
			view.notifyModelChanged();
		}
	}

	public void subscribeView(InterfaceView view) {

		subscribers.add(view);
	}

	public void unsubscribeView(InterfaceView view) {

		subscribers.remove(view);
	}

	public void subscribeTimeDisplay(InterfaceTimeDisplay timeDisplay) {

		minesweeperTimer.subscribeTimeDisplay(timeDisplay);
	}

	public void unsubscribeTimeDisplay(InterfaceTimeDisplay timeDisplay) {

		minesweeperTimer.unsubscribeTimeDisplay(timeDisplay);
	}

	public String about() {

		return "MinesweeperPlus v1.0";
	}

	public void saveGame(String fileName) throws Exception {

		ModelState modelState = new ModelState();

		modelState.setNumberOfFlags(numberOfFlags);
		modelState.setGameState(gameState);
		modelState.setTime(minesweeperTimer.getTime());

		SaverLoader.saveGame(fileName, modelState, field);
	}

	public void loadGame(String fileName) throws Exception {

		ModelState modelState = SaverLoader.loadGame(fileName, field);
		minesweeperTimer.stop();

		numberOfFlags = modelState.getNumberOfFlags();
		gameState = modelState.getGameState();
		minesweeperTimer.setTime(modelState.getTime());

		newRecord = EnumNewRecord.NO;
		notifyNewGame();

		if (gameState == EnumGameState.PLAYING) {
			minesweeperTimer.start();
		}
	}
}

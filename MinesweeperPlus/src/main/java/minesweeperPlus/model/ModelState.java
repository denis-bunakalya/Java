package minesweeperPlus.model;

public class ModelState {

	private int numberOfFlags;
	private EnumGameState gameState;
	private int time;

	public int getNumberOfFlags() {

		return numberOfFlags;
	}

	public EnumGameState getGameState() {

		return gameState;
	}

	public int getTime() {

		return time;
	}

	public void setNumberOfFlags(int numberOfFlags) {

		this.numberOfFlags = numberOfFlags;
	}

	public void setGameState(EnumGameState gameState) {

		this.gameState = gameState;
	}

	public void setTime(int time) {

		this.time = time;
	}

}

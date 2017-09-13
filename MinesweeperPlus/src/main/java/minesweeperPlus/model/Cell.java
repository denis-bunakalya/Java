package minesweeperPlus.model;

public class Cell {

	private EnumCellState state;
	private int numberOfMinesAround;

	public Cell() {

		state = EnumCellState.HIDDEN;
		numberOfMinesAround = 0;
	}
	
	public Cell(EnumCellState state, int numberOfMinesAround) {

		this.state = state;
		this.numberOfMinesAround = numberOfMinesAround;
	}

	public void open() {

		state = EnumCellState.OPENED;
	}

	public void setMine() {

		numberOfMinesAround = -1;
	}

	public int flag() {

		if (state == EnumCellState.HIDDEN) {
			state = EnumCellState.FLAGED;
			return -1;
		} else if (state == EnumCellState.FLAGED) {
			state = EnumCellState.QUESTION;
			return 1;
		} else if (state == EnumCellState.QUESTION) {
			state = EnumCellState.HIDDEN;
		}
		return 0;
	}

	public void incrementNumberOfMinesAround() {

		if (numberOfMinesAround != -1) {
			numberOfMinesAround++;
		}
	}

	public EnumCellState getCellState() {

		return state;
	}

	public int getNumberOfMinesAround() {

		return numberOfMinesAround;
	}

	public boolean isMine() {

		return (numberOfMinesAround == -1);
	}
}

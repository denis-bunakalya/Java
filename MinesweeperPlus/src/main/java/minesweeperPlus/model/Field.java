package minesweeperPlus.model;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Field {

	private Cell[][] field;
	
	private int width;
	private int height;
	private int numberOfMines;

	private int numberOfOpenedCells;
	private Set<Cell> openedZeroCells;
	
	public Field() {
		
		openedZeroCells = new HashSet<Cell>();
	}
	
	public void newField(int width, int height, int numberOfMines) {

		field = new Cell[width][height];
		
		this.width = width;
		this.height = height;
		this.numberOfMines = numberOfMines;
		
		openedZeroCells.clear();
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				field[i][j] = new Cell();
			}
		}
		
		List<Integer> allPlaces = new ArrayList<Integer>();
		for (int i = 0; i < width * height; i++) {
			allPlaces.add(i);
		}

		int currentPlace;
		int x;
		int y;
		Random random = new Random();

		for (int i = 0; i < numberOfMines; i++) {

			currentPlace = allPlaces.remove(random.nextInt(allPlaces.size()));
			x = currentPlace / height;
			y = currentPlace % height;

			field[x][y].setMine();
			incrementNumberOfMinesForNeighbours(x, y);
		}
		numberOfOpenedCells = 0;
	}	
	
	private void incrementNumberOfMinesForNeighbours(int x, int y) {

		int left = x - 1;
		int right = x + 1;
		int up = y + 1;
		int down = y - 1;

		if (left >= 0) {
			field[left][y].incrementNumberOfMinesAround();
			if (down >= 0) {
				field[left][down].incrementNumberOfMinesAround();
			}
			if (up < height) {
				field[left][up].incrementNumberOfMinesAround();
			}
		}

		if (right < width) {
			field[right][y].incrementNumberOfMinesAround();
			if (down >= 0) {
				field[right][down].incrementNumberOfMinesAround();
			}
			if (up < height) {
				field[right][up].incrementNumberOfMinesAround();
			}
		}

		if (down >= 0) {
			field[x][down].incrementNumberOfMinesAround();
		}

		if (up < height) {
			field[x][up].incrementNumberOfMinesAround();
		}
	}

	public void open(int x, int y) {

		field[x][y].open();
		numberOfOpenedCells++;
		
		if (field[x][y].getNumberOfMinesAround() == 0) {
			openNeighboursOfZeroCell(x, y);
		}
	}
	
	private void openNeighboursOfZeroCell(int x, int y) {

		openedZeroCells.add(field[x][y]);
		
		if (field[x][y].getCellState() != EnumCellState.OPENED) {
			numberOfOpenedCells++;
		}
		field[x][y].open();

		int newX, newY;

		if (field[x][y].getNumberOfMinesAround() == 0) {
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					newX = x + i;
					newY = y + j;

					if ((newX >= 0) && (newX < width) && (newY >= 0)
							&& (newY < height)
							&& (!openedZeroCells.contains(field[newX][newY]))) {
						openNeighboursOfZeroCell(newX, newY);
					}
				}
			}
		}
	}

	public int flag(int x, int y) {

		return field[x][y].flag();
	}

	public int getWidth() {

		return width;
	}

	public int getHeight() {

		return height;
	}

	public int getNumberOfMinesAround(int x, int y) {

		return field[x][y].getNumberOfMinesAround();
	}	
	
	public EnumCellState getCellState(int x, int y) {

		return field[x][y].getCellState();
	}
	
	public boolean isMine(int x, int y) {

		return field[x][y].isMine();
	}
	
	public boolean isVictory() {

		return width * height - numberOfOpenedCells == numberOfMines;
	}
	
	public void save(FileWriter fileWriter) throws Exception {

		fileWriter.write(width + "\r\n");
		fileWriter.write(height + "\r\n");

		fileWriter.write(numberOfOpenedCells + "\r\n");

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				fileWriter.write(field[i][j].getNumberOfMinesAround() + "\r\n");
				fileWriter.write(field[i][j].getCellState() + "\r\n");
			}
		}
	}

	public void load(BufferedReader bufferedReader) throws Exception {

		int width = Integer.parseInt(bufferedReader.readLine());
		int height = Integer.parseInt(bufferedReader.readLine());
		int numberOfOpenedCells = Integer.parseInt(bufferedReader.readLine());

		Cell[][] field = new Cell[width][height];
		int numberOfMines = 0;
		Set<Cell> openedZeroCells = new HashSet<Cell>();

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {

				int numberOfMinesAround = Integer.parseInt(bufferedReader
						.readLine());

				EnumCellState cellState = null;

				String read = bufferedReader.readLine();
				if ("FLAGED".equals(read)) {
					cellState = EnumCellState.FLAGED;
				}
				else if ("HIDDEN".equals(read)) {
					cellState = EnumCellState.HIDDEN;
				}
				else if ("OPENED".equals(read)) {
					cellState = EnumCellState.OPENED;
				}
				else if ("QUESTION".equals(read)) {
					cellState = EnumCellState.QUESTION;
				}

				Cell cell = new Cell(cellState, numberOfMinesAround);

				if (numberOfMinesAround == -1) {
					numberOfMines++;
				}

				if ((cellState == EnumCellState.OPENED)
						&& (numberOfMinesAround == 0)) {
					openedZeroCells.add(cell);
				}

				field[i][j] = cell;
			}
		}

		this.width = width;
		this.height = height;
		this.numberOfOpenedCells = numberOfOpenedCells;

		this.field = field;
		this.numberOfMines = numberOfMines;
		this.openedZeroCells = openedZeroCells;
	}
}

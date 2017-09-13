package minesweeperPlus.model;
import static org.junit.Assert.*;

import org.junit.Test;

import minesweeperPlus.model.Cell;
import minesweeperPlus.model.EnumCellState;

public class CellTest {

	@Test
	public void createTest() {
		
		Cell cell = new Cell();
		
		assertEquals(EnumCellState.HIDDEN, cell.getCellState());
		assertEquals(0, cell.getNumberOfMinesAround());
		assertFalse(cell.isMine());
	}
	
	@Test
	public void openTest() {
		
		Cell cell = new Cell();
		cell.open();
		
		assertEquals(EnumCellState.OPENED, cell.getCellState());
		assertEquals(0, cell.getNumberOfMinesAround());
		assertFalse(cell.isMine());
	}
	
	@Test
	public void setMineTest() {
		
		Cell cell = new Cell();
		cell.setMine();
		
		assertEquals(EnumCellState.HIDDEN, cell.getCellState());
		assertEquals(-1, cell.getNumberOfMinesAround());
		assertTrue(cell.isMine());
	}
	
	@Test
	public void flagTest() {
		
		Cell cell = new Cell();
		assertEquals(-1, cell.flag());
		
		assertEquals(EnumCellState.FLAGED, cell.getCellState());
		assertEquals(0, cell.getNumberOfMinesAround());
		assertFalse(cell.isMine());
		
		assertEquals(1, cell.flag());
		
		assertEquals(EnumCellState.QUESTION, cell.getCellState());
		assertEquals(0, cell.getNumberOfMinesAround());
		assertFalse(cell.isMine());
		
		assertEquals(0, cell.flag());
		
		assertEquals(EnumCellState.HIDDEN, cell.getCellState());
		assertEquals(0, cell.getNumberOfMinesAround());
		assertFalse(cell.isMine());
	}
	
	@Test
	public void incrementTest() {
		
		Cell cell = new Cell();
		cell.incrementNumberOfMinesAround();
		
		assertEquals(EnumCellState.HIDDEN, cell.getCellState());
		assertEquals(1, cell.getNumberOfMinesAround());
		assertFalse(cell.isMine());
		
		cell.setMine();
		cell.incrementNumberOfMinesAround();
		
		assertEquals(EnumCellState.HIDDEN, cell.getCellState());
		assertEquals(-1, cell.getNumberOfMinesAround());
		assertTrue(cell.isMine());
	}
}

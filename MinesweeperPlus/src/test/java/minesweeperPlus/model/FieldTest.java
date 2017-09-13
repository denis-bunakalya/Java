package minesweeperPlus.model;
import static org.junit.Assert.*;

import org.junit.Test;

import minesweeperPlus.model.EnumCellState;
import minesweeperPlus.model.Field;

public class FieldTest {

	@Test
	public void createTest() {

		Field field = new Field();
		field.newField(1, 1, 1);

		assertEquals(1, field.getWidth());
		assertEquals(1, field.getHeight());

		assertEquals(-1, field.getNumberOfMinesAround(0, 0));
		assertEquals(EnumCellState.HIDDEN, field.getCellState(0, 0));
		assertTrue(field.isMine(0, 0));
	}

	@Test
	public void openTest() {

		Field field = new Field();
		field.newField(1, 1, 1);
		field.open(0, 0);

		assertEquals(1, field.getWidth());
		assertEquals(1, field.getHeight());

		assertEquals(-1, field.getNumberOfMinesAround(0, 0));
		assertEquals(EnumCellState.OPENED, field.getCellState(0, 0));
		assertTrue(field.isMine(0, 0));
	}

	@Test
	public void flagTest() {

		Field field = new Field();
		field.newField(1, 1, 1);
		assertEquals(-1, field.flag(0, 0));		

		assertEquals(1, field.getWidth());
		assertEquals(1, field.getHeight());

		assertEquals(-1, field.getNumberOfMinesAround(0, 0));
		assertEquals(EnumCellState.FLAGED, field.getCellState(0, 0));
		assertTrue(field.isMine(0, 0));
	}

	@Test
	public void openNeighboursOfZeroCellTest() {

		Field field = new Field();
		field.newField(3, 3, 0);
		field.open(1, 1);

		for (int i = 0; i <= 2; i++) {
			for (int j = 0; j <= 2; j++) {
				assertEquals(EnumCellState.OPENED, field.getCellState(i, j));
			}
		}
	}
	
	@Test
	public void isVictoryTest() {

		Field field = new Field();
		field.newField(1, 1, 0);
		assertFalse(field.isVictory());
		
		field.open(0, 0);
		assertTrue(field.isVictory());
		
		field = new Field();
		field.newField(1, 1, 1);
		assertTrue(field.isVictory());
	}
}

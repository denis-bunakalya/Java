package minesweeperPlus.model;
import static org.junit.Assert.*;

import org.junit.Test;

import minesweeperPlus.model.EnumCellState;
import minesweeperPlus.model.EnumGameState;
import minesweeperPlus.model.Model;

public class ModelTest {

	@Test
	public void newGameTest() {
		
		Model model = new Model();
		
		try {
			model.newGame(0, 1, 1);
			fail("An exception should be thrown");
			
		} catch (Exception e) {
			assertEquals("Wrong width. It should be a number from 1 to " + Model.MAX_WIDTH + ".\n", e.getMessage());
		}
		
		try {
			model.newGame(Model.MAX_WIDTH + 1, 1, 1);
			fail("An exception should be thrown");
			
		} catch (Exception e) {
			assertEquals("Wrong width. It should be a number from 1 to " + Model.MAX_WIDTH + ".\n", e.getMessage());
		}	
		
		try {
			model.newGame(1, 0, 1);
			fail("An exception should be thrown");
			
		} catch (Exception e) {
			assertEquals("Wrong height. It should be a number from 1 to " + Model.MAX_HEIGHT + ".\n", e.getMessage());
		}
		
		try {
			model.newGame(1, Model.MAX_HEIGHT + 1, 1);
			fail("An exception should be thrown");
			
		} catch (Exception e) {
			assertEquals("Wrong height. It should be a number from 1 to " + Model.MAX_HEIGHT + ".\n", e.getMessage());
		}
		
		try {
			model.newGame(1, 1, -1);
			fail("An exception should be thrown");
			
		} catch (Exception e) {
			assertEquals("Wrong number of mines. It should be a number from 0 to width*height.\n", e.getMessage());
		}
		
		try {
			model.newGame(1, 1, Model.MAX_WIDTH * Model.MAX_HEIGHT + 1);
			fail("An exception should be thrown");
			
		} catch (Exception e) {
			assertEquals("Wrong number of mines. It should be a number from 0 to width*height.\n", e.getMessage());
		}
		
		try {
		model.newGame(1, 1, 1);
		} catch (Exception e) {
			fail("No exception should be thrown");
		}
		
		assertEquals(1, model.getWidth());
		assertEquals(1, model.getHeight());
		
		assertTrue(model.isMine(0, 0));
		assertEquals(EnumCellState.HIDDEN, model.getCellState(0, 0));
		assertEquals(-1, model.getNumberOfMinesAround(0, 0));		
	}

	@Test
	public void openTest() {

		Model model = new Model();
		try {
			model.newGame(1, 1, 0);
		} catch (Exception e) {
			fail("No exception should be thrown");
		}
		model.open(0, 0);
		assertEquals(EnumCellState.OPENED, model.getCellState(0, 0));
	}
	
	@Test
	public void flagTest() {

		Model model = new Model();
		try {
			model.newGame(1, 1, 1);
		} catch (Exception e) {
			fail("No exception should be thrown");
		}
		assertEquals(1, model.getNumberOfFlags());
		
		model.flag(0, 0);
		assertEquals(EnumCellState.FLAGED, model.getCellState(0, 0));
		assertEquals(0, model.getNumberOfFlags());
		
		model.flag(0, 0);
		assertEquals(EnumCellState.QUESTION, model.getCellState(0, 0));
		assertEquals(1, model.getNumberOfFlags());
		
		model.flag(0, 0);
		assertEquals(EnumCellState.HIDDEN, model.getCellState(0, 0));
		assertEquals(1, model.getNumberOfFlags());
	}
	
	@Test
	public void gameStateTest() {

		Model model = new Model();
		try {
			model.newGame(1, 1, 0);
		} catch (Exception e) {
			fail("No exception should be thrown");
		}
		assertEquals(EnumGameState.PLAYING, model.getGameState());
		
		model.open(0, 0);		
		assertEquals(EnumGameState.VICTORY, model.getGameState());
		
		model = new Model();
		try {
			model.newGame(1, 1, 1);
		} catch (Exception e) {
			fail("No exception should be thrown");
		}
		assertEquals(EnumGameState.PLAYING, model.getGameState());
		
		model.open(0, 0);		
		assertEquals(EnumGameState.DEFEAT, model.getGameState());
	}
	
	@Test
	public void aboutTest() {

		Model model = new Model();
		assertEquals("MinesweeperPlus v1.0", model.about());
	}
}

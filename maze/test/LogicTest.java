package test;

import java.util.ArrayList;

import junit.framework.TestCase;
import logic.Logic;


public class LogicTest extends TestCase {
	
	private Logic startGame() {
		// Stopped Dragon
		return new Logic(1);
	}
	
	private ArrayList<Character> moveToSword() {
		ArrayList<Character> commands = new ArrayList<Character>();
		commands.add('d');
		commands.add('d');
		commands.add('d');
		commands.add('s');
		commands.add('s');
		commands.add('s');
		commands.add('s');
		commands.add('a');
		commands.add('a');
		commands.add('a');
		commands.add('s');
		commands.add('s');
		commands.add('s');
		
		return commands;
	}
	
	public void testHeroOpenCell() {
		Logic game = startGame();
		ArrayList<Character> commands = new ArrayList<Character>();
		commands.add('d');
		
		int heroX = game.getHero().getX();
		int heroY = game.getHero().getY();
		
		for(Character command : commands)
			game.play(command);

		int newHeroX = game.getHero().getX();
		int newHeroY = game.getHero().getY();

		assertEquals(heroX, newHeroX);
		assertEquals(heroY+1, newHeroY);
	}
	
	public void testHeroWallCell() {
		Logic game = startGame();
		ArrayList<Character> commands = new ArrayList<Character>();
		commands.add('w');
		
		int heroX = game.getHero().getX();
		int heroY = game.getHero().getY();
		
		for(Character command : commands)
			game.play(command);

		int newHeroX = game.getHero().getX();
		int newHeroY = game.getHero().getY();

		assertEquals(heroX, newHeroX);
		assertEquals(heroY, newHeroY);
	}
	
	public void testHeroPickSword() {
		Logic game = startGame();
		ArrayList<Character> commands = moveToSword();
		
		for(Character command : commands)
			game.play(command);

		boolean isArmed = game.getHero().isArmed();

		assertTrue(isArmed);
	}

	public void testHeroDie() {
		Logic game = startGame();
		ArrayList<Character> commands = new ArrayList<Character>();
		commands.add('s');
		
		for(Character command : commands)
			game.play(command);

		assertTrue(game.gameEnded());
		assertEquals(game.gameOverCode(), 2);
		assertFalse(game.getHero().isActive());
	}

	public void testDragonDie() {
		Logic game = startGame();
		ArrayList<Character> commands = moveToSword();
		commands.add('w');
		commands.add('w');
		commands.add('w');
		commands.add('w');
		
		for(Character command : commands)
			game.play(command);
		
		
		assertTrue(game.getDragons().get(0).isDead());
	}

	public void testWin() {
		Logic game = startGame();
		ArrayList<Character> commands = moveToSword();
		commands.add('w');
		commands.add('w');
		commands.add('w');
		commands.add('w');
		commands.add('s');
		commands.add('d');
		commands.add('d');
		commands.add('d');
		commands.add('d');
		commands.add('d');
		commands.add('s');
		commands.add('s');
		commands.add('s');
		commands.add('d');
		commands.add('d');
		commands.add('w');
		commands.add('w');
		commands.add('w');
		commands.add('d');
		
		for(Character command : commands)
			game.play(command);
		
		assertTrue(game.gameEnded());
		assertEquals(game.gameOverCode(), 1);
		
		assertTrue(game.getHero().isActive());
		assertTrue(game.getDragons().get(0).isDead());
	}

	public void testExitNoSword() {
		Logic game = startGame();
		ArrayList<Character> commands = new ArrayList<Character>();
		commands.add('d');
		commands.add('d');
		commands.add('d');
		commands.add('d');
		commands.add('d');
		commands.add('d');
		commands.add('d');
		commands.add('s');
		commands.add('s');
		commands.add('s');
		commands.add('s');
		commands.add('d');
		
		for(Character command : commands)
			game.play(command);
		
		assertFalse(game.gameEnded());
		assertEquals(game.gameOverCode(), 0);
		assertFalse(game.getHero().isArmed());
		assertTrue(game.getHero().isActive());
		assertFalse(game.getDragons().get(0).isDead());
	}
}
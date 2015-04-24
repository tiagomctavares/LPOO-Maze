package cli;
import java.util.Scanner;

import global.Helper;
import logic.Dragon;
import logic.Hero;
import logic.Logic;
import logic.Maze;
import logic.Sword;

public class cli {
	
	private static Logic game;
	
	public static void main(String[] args) {
		boolean defaultMaze = true;
		
		game = new Logic(defaultMaze);
		
		Scanner keyboard = new Scanner(System.in);
		
		while (!game.gameEnded()) {
			display(game.maze.map);
			char movement = keyboard.next().charAt(0);
			game.play(movement);
		}
		
		if(game.hasWin())
			System.out.println("You won!");
		else
			System.out.println("Dragon says: great meal!");
		
		
		keyboard.close();
	}
	
	public static void display(char[][] maze) {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				if(game.dragon.samePosition(i, j))
					System.out.print(game.dragon.getSymbol());
				else if(game.hero.samePosition(i, j))
					System.out.print(game.hero.getSymbol());
				else if(game.sword.samePosition(i, j))
					System.out.print(game.sword.getSymbol());
				else
					System.out.print(maze[i][j]);
				
				System.out.print(" ");
			}
			System.out.println();
		}
	}
}

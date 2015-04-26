package cli;
import java.util.Scanner;

import global.Helper;
import logic.Dart;
import logic.Dragon;
import logic.Hero;
import logic.Logic;
import logic.Maze;
import logic.Sword;

public class cli {
	
	private static Logic game;
	private static boolean debug = true; 
	private static boolean debugRandom = false; 
	
	public static void main(String[] args) {
		
		Scanner keyboard = new Scanner(System.in);

		displayOptions(keyboard);
		
		while (!game.gameEnded()) {
			display(game.maze.map);
			char movement = keyboard.next().charAt(0);
			game.play(movement);
		}

		display(game.maze.map);
		
		if(game.gameOverCode() == 1)
			System.out.println("How is this possible?! You cheated! No one can beat my Dragons!");
		else if(game.gameOverCode() == 2)
			System.out.println("What a great meal for my pet Dragons!");
		else if(game.gameOverCode() == 3)
			System.out.println("Extra crispy Hero! Royal feast for my Dragons!");
		
		
		keyboard.close();
	}
	
	private static void displayOptions(Scanner keyboard) {
		if(debug) {
			if( !debugRandom)
				game = new Logic(true, 10, 1, 3);
			else
				game = new Logic(false, 10, 2, 2);
				
			return;
		}
		

		while(true) {

				System.out.println("Amazing Maze");
				System.out.println("Select Option:");
				System.out.println("   1- Default Maze");
				System.out.println("   2- Random Maze");
				
				char option = keyboard.next().charAt(0);
				
				if(option == '1') {
					int dragonOptions = dragonMovementOptions(keyboard);
					game = new Logic(true, 10, dragonOptions, 1);
					break;
				}else if(option == '2') {
					while(true) {
						System.out.println("Amazing Maze");
						System.out.print("Size of maze (min 10)? ");
						int mazeSize = keyboard.nextInt();

						if(mazeSize >= 10) {
							int dragonOptions = dragonMovementOptions(keyboard);
							int dragonNumber = dragonNumber(keyboard);
							game = new Logic(false, mazeSize, dragonOptions, dragonNumber);
							break;
						}
					}
					break;
				}
		}	
	}

	private static int dragonMovementOptions(Scanner keyboard) {
		while(true) {
			System.out.println("Amazing Maze");
			System.out.println("Dragon Movement: ");
			System.out.println("   1- Stopped");
			System.out.println("   2- Random Movement");
			System.out.println("   3- Random Movement Sleep Option");
			
			int dragonMovement = keyboard.nextInt();
			if(dragonMovement >= 1 && dragonMovement <= 3) {
				return dragonMovement;
			}
		}
	}

	private static int dragonNumber(Scanner keyboard) {
		while(true) {
			System.out.println("Amazing Maze");
			System.out.print("Number of dragons (max 4)? ");
			
			int numberDragons = keyboard.nextInt();
			if(numberDragons >= 1 && numberDragons <= 4) {
				return numberDragons;
			}
		}
	}

	public static void display(char[][] maze) {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				boolean characterFound = false;
				
				for (Dragon dragon : game.dragons) {
					if(dragon.samePosition(i, j)) {
						System.out.print(dragon.getSymbol());
						characterFound = true;
						break;
					}
				}
				
				if(!characterFound) {
					for (Dart dart : game.darts) {
						if(dart.samePosition(i, j)) {
							System.out.print(dart.getSymbol());
							characterFound = true;
							break;
						}
					}
				}
				
				if(!characterFound) {
					
					if(game.hero.samePosition(i, j) && (game.gameOverCode() == 0 || game.gameOverCode() == 1))
						System.out.print(game.hero.getSymbol());
					else if(game.shield.samePosition(i, j) && game.shield.isActive())
						System.out.print(game.shield.getSymbol());
					else if(game.sword.samePosition(i, j) && game.sword.isActive())
						System.out.print(game.sword.getSymbol());
					else
						System.out.print(maze[i][j]);
				}
				System.out.print(" ");
			}
			System.out.println();
		}
		
		if(!game.hero.hasShield())
			System.out.println("Without the shield you are nothing agains my Dragons! (P -> shield)");
		else if(game.hero.hasShield() && game.hero.isArmed())
			System.out.println("Please mercy on my Dragons! I promise to let you go for today!");
		else
			System.out.println("The Dragons can't burn you! But you will be eaten raw!");
		
		System.out.println(game.hero.getDarts()+" darts in your backpack (f, g, h, t to launch them)");
	}
}

package cli;
import java.util.Scanner;

import logic.Logic;

public class cli {
	
	private static Logic game;
	private static boolean debug = false; 
	private static boolean debugRandom = false; 
	
	public static void main(String[] args) {
		
		Scanner keyboard = new Scanner(System.in);

		displayOptions(keyboard);
		
		while (!game.gameEnded()) {
			display(game.getMap());
			char movement = keyboard.next().charAt(0);
			game.play(movement);
		}

		display(game.getMap());
		
		System.out.println(game.getGameEndedMessage());
		
		keyboard.close();
	}
	
	private static void displayOptions(Scanner keyboard) {
		if(debug) {
			if( !debugRandom)
				game = new Logic(1);
			else
				game = new Logic(10, 2, 2);
				
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
					game = new Logic(dragonOptions);
					break;
				}else if(option == '2') {
					while(true) {
						System.out.println("Amazing Maze");
						System.out.print("Size of maze (min 10)? ");
						int mazeSize = keyboard.nextInt();

						if(mazeSize >= 10) {
							int dragonOptions = dragonMovementOptions(keyboard);
							int dragonNumber = dragonNumber(keyboard);
							game = new Logic(mazeSize, dragonOptions, dragonNumber);
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
			System.out.print("Number of dragons (max 9)? ");
			
			int numberDragons = keyboard.nextInt();
			if(numberDragons >= 1 && numberDragons <= 9) {
				return numberDragons;
			}
		}
	}

	public static void display(char[][] map) {
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				System.out.print(map[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
		
		System.out.println(game.getGameMessage());
		System.out.println(game.heroDarts()+" darts in your backpack (f, g, h, t to launch them)");
	}
}

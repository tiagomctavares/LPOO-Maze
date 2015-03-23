package maze;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import maze.cli;
import maze.mazeGenerator;

//BIT 0 -> Wall
//BIT 1 -> Hero
//BIT 2 -> Exit 
//BIT 3 -> Sword
//BIT 4 -> Armed
//BIT 5 -> Dragon

public class Logic {
	private static boolean dragonAlive = true;
	private static int empty = 0;
	private static int wall = BIT(0);
	private static int hero = BIT(1);
	private static int exit = BIT(2);
	private static int sword = BIT(3);
	private static int armed = BIT(4);
	private static int dragon = BIT(5);
	private static int[][] init_map;
	private static Symbol shero;

	public static void main(String[] args) {
		boolean defaultMaze = true; 
		mazeGenerator maze = new mazeGenerator();
		
		if(defaultMaze) {
			init_map = maze.generateMap(!defaultMaze, 10);
			shero = new Hero(1, 1);
			init_map[1][1] = hero;
			init_map[3][1] = dragon;
			init_map[8][1] = sword;
		} else {
			init_map = maze.generateMap(defaultMaze, 10); 
		}
		
		Scanner keyboard = new Scanner(System.in);
		int[] hero_loc = findEntity("hero", init_map);
		int[] dragon_loc = findEntity("dragon", init_map);
		
		while (true) {
			cli.display(init_map);
			char movement = keyboard.next().charAt(0);
			move(movement, hero_loc);
			if (dragonAlive)
				moveDragon(dragon_loc);
			checkDragon(hero_loc);
		}
	}

	private static int BIT(int n) {
		int shifted;
		shifted = 1 << n;
		return shifted;
	}

	private static void checkDragon(int[] hero_loc) {
		String col_detect;
		int dy, dx;
		for (int i = 0; i < 4; i++) {
			dy = dx = 0;
			switch (i) {
			case 0: //left
				dx--;
				break;
			case 1: //right
				dx++;
				break;
			case 2: //up
				dy--;
				break;
			case 3: //down
				dy++;
				break;
			}
			hero_loc[0] += dy;
			hero_loc[1] += dx;
			col_detect = collision(hero_loc);
			if (col_detect == "dragon") {
				if (init_map[hero_loc[0] - dy][hero_loc[1] - dx] == BIT(4)) {
					init_map[hero_loc[0]][hero_loc[1]] = empty;
					dragonAlive = false;
				} else loseGame();
			}
			hero_loc[0] -= dy;
			hero_loc[1] -= dx;
		}
	}

	private static int[] findEntity(String entity, int[][] maze) {
		int[] loc = new int[2];
		search:
			for (int i = 0; i < maze.length; i++) {
				for (int j = 0; j < maze[i].length; j++) {
					switch (entity) {
					case "hero":
						if ((maze[i][j] & hero) == BIT(1)) {
							loc[0] = i;
							loc[1] = j;
							break search;
						}
					case "dragon":
						if ((maze[i][j] & dragon) == BIT(5)) {
							loc[0] = i;
							loc[1] = j;
							break search;
						}
					}

				}
			}
		return loc;
	}

	private static String collision(int[] hero_loc) {
		if ((init_map[hero_loc[0]][hero_loc[1]] & wall) == BIT(0))
			return "wall";
		else if ((init_map[hero_loc[0]][hero_loc[1]] & dragon) == BIT(5))
			return "dragon";
		else if ((init_map[hero_loc[0]][hero_loc[1]] & sword) == BIT(3))
			return "sword";
		else if ((init_map[hero_loc[0]][hero_loc[1]] & exit) == BIT(2))
			return "exit";
		else return "empty";
	}

	private static void loseGame() {
		cli.display(init_map);
		System.out.println("Dragon says: great meal!");
		System.exit(0);
	}

	private static void winGame() {
		cli.display(init_map);
		System.out.println("You won!");
		System.exit(0);
	}

	private static void moveDragon(int[] dragon_loc) {
		String col_detect;
		int dy, dx;
		dy = dx = 0;
		int movement = randInt(0, 4);
		switch (movement) {
		case 0: //left
			dx--;
			break;
		case 1: //right
			dx++;
			break;
		case 2: //up
			dy--;
			break;
		case 3: //down
			dy++;
			break;
		case 4: //hold
			break;
		}
		dragon_loc[0] += dy;
		dragon_loc[1] += dx;
		col_detect = collision(dragon_loc);
		if (col_detect == "sword") {
			init_map[dragon_loc[0]][dragon_loc[1]] |= BIT(5);
			init_map[dragon_loc[0] - dy][dragon_loc[1] - dx] = empty;
		} 
		else if (col_detect == "empty") {
			init_map[dragon_loc[0]][dragon_loc[1]] |= BIT(5);
			init_map[dragon_loc[0] - dy][dragon_loc[1] - dx] ^= BIT(5);
		} else {
			dragon_loc[0] -= dy;
			dragon_loc[1] -= dx;
		}
	}

	private static void move(char movement, int[] hero_loc) {
		String col_detect;
		int dy, dx;
		dy = dx = 0;
		switch (movement) {
		case 'a': //left
			dx--;
			break;
		case 'd': //right
			dx++;
			break;
		case 'w': //up
			dy--;
			break;
		case 's': //down
			dy++;
			break;
		}
		hero_loc[0] += dy;
		hero_loc[1] += dx;
		col_detect = collision(hero_loc);
		if (col_detect == "wall") {
			hero_loc[0] -= dy;
			hero_loc[1] -= dx;
		}
		else if (col_detect == "sword") {
			init_map[hero_loc[0]][hero_loc[1]] = BIT(4);
			init_map[hero_loc[0] - dy][hero_loc[1] - dx] = empty;
		}
		else if (col_detect == "empty") {
			init_map[hero_loc[0]][hero_loc[1]] = init_map[hero_loc[0] - dy][hero_loc[1] - dx]; //Copy previous state
			init_map[hero_loc[0] - dy][hero_loc[1] - dx] = empty;
		}
		else if (col_detect == "exit") {
			if (init_map[hero_loc[0] - dy][hero_loc[1] - dx] == BIT(4))
				winGame();
			else {
				hero_loc[0] -= dy;
				hero_loc[1] -= dx;
			}
		}
	}
	
	private static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
}

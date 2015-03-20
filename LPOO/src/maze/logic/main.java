package maze.logic;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import maze.cli.cli;

//BIT 0 -> Wall
//BIT 1 -> Hero
//BIT 2 -> Exit 
//BIT 3 -> Sword
//BIT 4 -> Armed
//BIT 5 -> Dragon

public class main {
	private static boolean dragonAlive = true;
	private static int empty = 0;
	private static int wall = BIT(0);
	private static int hero = BIT(1);
	private static int exit = BIT(2);
	private static int sword = BIT(3);
	private static int armed = BIT(4);
	private static int dragon = BIT(5);
	private static int[][] init_map =
		{{wall, wall, wall, wall, wall, wall, wall, wall, wall, wall},
		{wall, hero, empty, empty, empty, empty, empty, empty, empty, wall},
		{wall, empty, wall, wall, empty, wall, empty, wall, empty, wall},
		{wall, dragon, wall, wall, empty, wall, empty, wall, empty, wall},
		{wall, empty, wall, wall, empty, wall, empty, wall, empty, wall},
		{wall, empty, empty, empty, empty, empty, empty, wall, empty, exit},
		{wall, empty, wall, wall, empty, wall, empty, wall, empty, wall},
		{wall, empty, wall, wall, empty, wall, empty, wall, empty, wall},
		{wall, sword, wall, wall, empty, empty, empty, empty, empty, wall},
		{wall, wall, wall, wall, wall, wall, wall, wall, wall, wall}};

	public static void main(String[] args) {
		//init_map = generateMaze(10); NEEDS ENTITY PLACEMENT BEFORE WORKING (DRAGON, WEAPON, etc)
		int[] hero_loc = findEntity("hero", init_map);
		int[] dragon_loc = findEntity("dragon", init_map);
		Scanner keyboard = new Scanner(System.in);
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




	///////////////////////////// MAP GENERATOR /////////////////////////////////////
	private  int[][] generateMaze(int mazeSize) {
		int[][] maze = new int[mazeSize][mazeSize];


		//Start with a grid full of walls.
		for(int i=0; i < mazeSize; i++) {
			Arrays.fill(maze[i], wall);
		}

		//EXIT
		int [] firstWall = placeExit(maze, mazeSize);

		maze = generatePath(maze, mazeSize, firstWall[0], firstWall[1], 0);

		return maze;
	}

	private  int[] placeExit(int[][] maze, int mazeSize) {
		int corner = randInt(0, 3);
		int position = randInt(1, mazeSize-2);
		int[] wallToRemove = new int [2];
		corner = 3;

		// This is for the top and bottom corner
		if(corner < 2) {
			// bottom corner
			if(corner == 1) {
				corner = mazeSize-1;
				// Set the first wall to be removed
				wallToRemove[0] = corner-1;
				wallToRemove[1] = position;
			} else {
				// Top Corner
				corner = 0;

				wallToRemove[0] = 1;
				wallToRemove[1] = position;
			}

			// Place exit
			maze[corner][position] = exit;

		} else {
			// right corner
			if(corner == 3) {
				corner = mazeSize-1;


				// Set the first wall to be removed
				wallToRemove[0] = position;
				wallToRemove[1] = corner-1;
			} else {
				corner = 0;

				// Set the first wall to be removed
				wallToRemove[0] = position;
				wallToRemove[1] = corner+1;
			}
			// Place exit
			maze[position][corner] = exit;
		}

		return wallToRemove;
	}

	private  int[][] generatePath(int[][] maze, int mazeSize, int line, int col, int open_space) {
		maze[line][col] = empty;
		open_space++;

		int[] vec = {0, 1, 2, 3};
		vec = sort_arr(vec);

		if(!canMakePath(maze, mazeSize))
		{
			return maze;
		}

		for(int i = 0; i<vec.length; i++) {
			if(canGenratePath(maze, mazeSize, line, col+1) && vec[i] == 0) //right
				maze = generatePath(maze, mazeSize, line, col+1, open_space);

			if(canGenratePath(maze, mazeSize, line+1, col) && vec[i] == 1) //down
				maze = generatePath(maze, mazeSize, line+1, col, open_space);

			if(canGenratePath(maze, mazeSize, line, col-1) && vec[i] == 2) //left
				maze = generatePath(maze, mazeSize, line, col-1, open_space);

			if(canGenratePath(maze, mazeSize, line-1, col) && vec[i] == 3) //up
				maze = generatePath(maze, mazeSize, line-1, col, open_space);
		}

		return maze;
	}

	private  boolean canGenratePath(int[][] maze, int mazeSize, int lin, int col) {
		if(lin == 0 || col == 0 || lin == mazeSize-1 || col == mazeSize-1 || maze[lin][col] == ' ') // LIMITS AND OCUPIED
			return false;
		if(maze[lin-1][col] == empty && maze[lin-1][col+1] == empty && maze[lin][col+1] == empty)//CHECK 2x2
			return false;
		if(maze[lin][col+1] == empty && maze[lin+1][col+1] == empty && maze[lin+1][col] == empty)//CHECK 2x2
			return false;
		if(maze[lin+1][col] == empty && maze[lin+1][col-1] == empty && maze[lin][col-1] == empty)//CHECK 2x2
			return false;
		if(maze[lin-1][col] == empty && maze[lin-1][col-1] == empty && maze[lin][col-1] == empty)//CHECK 2x2
			return false;
		if(maze[lin-1][col+1] == empty && maze[lin-1][col] == wall && maze[lin][col+1] == wall)//CHECK DIAGONAL
			return false;
		if(maze[lin+1][col+1] == empty && maze[lin+1][col] == wall && maze[lin][col+1] == wall)//CHECK DIAGONAL
			return false;
		if(maze[lin+1][col-1] == empty && maze[lin+1][col] == wall && maze[lin][col-1] == wall)//CHECK DIAGONAL
			return false;
		if(maze[lin-1][col-1] == empty && maze[lin-1][col] == wall && maze[lin][col-1] == wall)//CHECK DIAGONAL
			return false;

		return true;
	}


	private  int[] sort_arr(int[] array) {
		for (int i = array.length; i > 1; i--) {
			int temp = array[i - 1];
			int randIx = (int) (Math.random() * i);
			array[i - 1] = array[randIx];
			array[randIx] = temp;
		}
		return array;
	}

	private  boolean canMakePath(int[][] maze, int mazeSize) {
		for(int i = 0; i<mazeSize-2; i++) { 		//Example-> [7*7] tests untils [3x3]
			for(int j = 0; j<mazeSize-2; j++) { 	//Example-> [7*7] tests untils [3x3]
				if(maze[i+1][j]==wall && maze[i+2][j]==wall && maze[i][j+1]==wall && maze[i+1][j+1]==wall && maze[i+2][j+1]==wall && maze[i][j+2]==wall && maze[i+1][j+2]==wall && maze[i+2][j+2]==wall) {
					return true; //can
				}
			}
		}

		return false; //cant!
	}

	private static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
}

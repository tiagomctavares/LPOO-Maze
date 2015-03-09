package LPOO.src;

import java.util.Arrays;
import java.util.Scanner;
import java.lang.String;
import java.util.Random;

//BIT 0 -> Wall
//BIT 1 -> Hero
//BIT 2 -> Exit 
//BIT 3 -> Sword
//BIT 4 -> Armed
//BIT 5 -> Dragon

public class main {
	private static int empty = 0;
	private static int wall = BIT(0);
	private static int hero = BIT(1);
	private static int exit = BIT(2);
	private static int sword = BIT(3);
	private static int armed = BIT(4);
	private static int dragon = BIT(5);
	private static int[][] init_map = {{wall, wall, wall, wall, wall, wall, wall, wall, wall, wall},
									   {wall, hero, empty, empty, empty, empty, empty, empty, empty, wall},
									   {wall, empty, wall, wall, empty, wall, empty, wall, empty, wall},
									   {wall, empty, wall, wall, empty, wall, empty, wall, empty, wall},
									   {wall, empty, wall, wall, empty, wall, empty, wall, empty, wall},
									   {wall, empty, empty, empty, empty, empty, empty, wall, empty, exit},
									   {wall, empty, wall, wall, empty, wall, empty, wall, empty, wall},
									   {wall, empty, wall, wall, empty, wall, empty, wall, empty, wall},
									   {wall, sword, wall, wall, empty, empty, empty, empty, empty, wall},
									   {wall, wall, wall, wall, wall, wall, wall, wall, wall, wall}};
	
	public static void main(String[] args) {
		// Se random gera aleatoriamente
		init_map = generateMaze(10);
		int[] hero_loc = findHero(init_map);
		Scanner keyboard = new Scanner(System.in);
		for (int n = 0; n < 28; n++) {
			display(init_map);
			char movement = keyboard.next().charAt(0);
			move(movement, hero_loc);
		}
	}
	
	private static int BIT(int n) {
		int shifted;
		shifted = 1 << n;
		return shifted;
	}
	
	private static int[] findHero(int[][] maze) {
		int[] hero_loc = new int[2];
		search:
			for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				if ((maze[i][j] & hero) == BIT(1)) {
					hero_loc[0] = i;
					hero_loc[1] = j;
					break search;
				}
			}
		}
		return hero_loc;
	}
	
	private static String collision(int[] hero_loc) {
		if ((init_map[hero_loc[0]][hero_loc[1]] & wall) == BIT(0))
			return "wall";
		else if ((init_map[hero_loc[0]][hero_loc[1]] & sword) == BIT(3))
			return "sword";
		else if ((init_map[hero_loc[0]][hero_loc[1]] & exit) == (BIT(2) ^ BIT(4)))
			return "exit";
		else return "empty";
	}
	
	private static void winGame() {
		System.out.println("You won!");
	}
	
	private static void move(char movement, int[] hero_loc) {
		String col_detect;
		switch(movement) {
		case 'a': //left
			hero_loc[1] -= 1;
			col_detect = collision(hero_loc);
			if (col_detect == "wall") {
				hero_loc[1] +=1;
			}
			else if (col_detect == "sword") {
				init_map[hero_loc[0]][hero_loc[1]] = BIT(4);
				init_map[hero_loc[0]][hero_loc[1] + 1] = empty;
			}
			else if (col_detect == "empty") {
				init_map[hero_loc[0]][hero_loc[1]] = init_map[hero_loc[0]][hero_loc[1] + 1];
				init_map[hero_loc[0]][hero_loc[1] + 1] = empty;
			}
			else if (col_detect == "exit") {
				winGame();
			}
			break;
		case 'd': //right
			hero_loc[1] += 1;
			col_detect = collision(hero_loc);
			if (col_detect == "wall") {
				hero_loc[1] -=1;
			}
			else if (col_detect == "sword") {
				init_map[hero_loc[0]][hero_loc[1]] = BIT(4);
				init_map[hero_loc[0]][hero_loc[1] - 1] = empty;
			}
			else if (col_detect == "empty") {
				init_map[hero_loc[0]][hero_loc[1]] = init_map[hero_loc[0]][hero_loc[1] - 1];
				init_map[hero_loc[0]][hero_loc[1] - 1] = empty;
			}
			else if (col_detect == "exit") {
				winGame();
			}
			break;
		case 'w': //up
			hero_loc[0] -= 1;
			col_detect = collision(hero_loc);
			if (col_detect == "wall") {
				hero_loc[0] +=1;
			}
			else if (col_detect == "sword") {
				init_map[hero_loc[0]][hero_loc[1]] = BIT(4);
				init_map[hero_loc[0] + 1][hero_loc[1]] = empty;
			}
			else if (col_detect == "empty") {
				init_map[hero_loc[0]][hero_loc[1]] = init_map[hero_loc[0] + 1][hero_loc[1]];
				init_map[hero_loc[0] + 1][hero_loc[1]] = empty;
			}
			else if (col_detect == "exit") {
				winGame();
			}
			break;
		case 's': //down
			hero_loc[0] += 1;
			col_detect = collision(hero_loc);
			if (col_detect == "wall") {
				hero_loc[0] -=1;
			}
			else if (col_detect == "sword") {
				init_map[hero_loc[0]][hero_loc[1]] = BIT(4);
				init_map[hero_loc[0] - 1][hero_loc[1]] = empty;
			}
			else if (col_detect == "empty") {
				init_map[hero_loc[0]][hero_loc[1]] = init_map[hero_loc[0] - 1][hero_loc[1]];
				init_map[hero_loc[0] - 1][hero_loc[1]] = empty;
			}
			else if (col_detect == "exit") {
				winGame();
			}
			break;
		default:
				break;
		}
	}
	
	private static void display(int[][] maze) {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				if ((maze[i][j] & wall) == BIT(0))
					System.out.print("X");
				else if ((maze[i][j] & hero) == BIT(1))
					System.out.print("H");
				else if ((maze[i][j] & exit) == BIT(2))
					System.out.print("S");
				else if ((maze[i][j] & sword) == BIT(3))
					System.out.print("E");
				else if ((maze[i][j] & armed) == BIT(4))
					System.out.print("A");
				else System.out.print(" ");
				
				// 1 MORE SPACE
				System.out.print(" ");
			}
			System.out.println();
		}
	}
	
	
	///////////////////////////// MAP GENERATOR /////////////////////////////////////
	private static int[][] generateMaze(int mazeSize) {
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
	
	private static int[] placeExit(int[][] maze, int mazeSize) {
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
	
	private static int[][] generatePath(int[][] maze, int mazeSize, int line, int col, int open_space) {
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
	
	private static boolean canGenratePath(int[][] maze, int mazeSize, int lin, int col) {
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
	

	private static int[] sort_arr(int[] array) {
		for (int i = array.length; i > 1; i--) {
			int temp = array[i - 1];
            int randIx = (int) (Math.random() * i);
            array[i - 1] = array[randIx];
            array[randIx] = temp;
		}
		return array;
	}
	
	private static boolean canMakePath(int[][] maze, int mazeSize) {
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

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
}

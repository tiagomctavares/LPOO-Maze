package logic;

import java.util.Arrays;
import java.util.Random;
import global.Helper;

public class Maze {

	private char empty = ' ';
	public char wall;
	public char exit;
	public char[][] map;
	
	public Maze() {
		this.wall = 'X';
		System.out.println("INIT");
		this.exit = 'E';
	}

	public void generateMap(boolean random, int number) {
	
		if (random)
			map = generateRandomMaze(number);
		else
			map = defaultMaze();

	}

	private char[][] defaultMaze() {
		char[][] maze = {
				{ wall, wall, wall, wall, wall, wall, wall, wall, wall, wall },
				{ wall, empty, empty, empty, empty, empty, empty, empty, empty,
						wall },
				{ wall, empty, wall, wall, empty, wall, empty, wall, empty,
						wall },
				{ wall, empty, wall, wall, empty, wall, empty, wall, empty,
						wall },
				{ wall, empty, wall, wall, empty, wall, empty, wall, empty,
						wall },
				{ wall, empty, empty, empty, empty, empty, empty, wall, empty,
						exit },
				{ wall, empty, wall, wall, empty, wall, empty, wall, empty,
						wall },
				{ wall, empty, wall, wall, empty, wall, empty, wall, empty,
						wall },
				{ wall, empty, wall, wall, empty, empty, empty, empty, empty,
						wall },
				{ wall, wall, wall, wall, wall, wall, wall, wall, wall, wall } };
		return maze;
	}

	// /////////////////////////// MAP GENERATOR // /////////////////////////////////////
	private char[][] generateRandomMaze(int mazeSize) {
		char[][] maze = new char[mazeSize][mazeSize];

		// Start with a grid full of walls.
		for (int i = 0; i < mazeSize; i++) {
			Arrays.fill(maze[i], wall);
		}

		// EXIT
		int[] firstWall = placeExit(maze, mazeSize);

		maze = generatePath(maze, mazeSize, firstWall[0], firstWall[1], 0);
		/*maze = placeSymbol(maze, mazeSize, dragon);
		maze = placeSymbol(maze, mazeSize, hero);
		maze = placeSymbol(maze, mazeSize, sword);*/

		return maze;
	}

	private int[][] placeSymbol(int[][] maze, int mazeSize, int symbol) {
		int numberEmpty = getTotalEmpty(maze, mazeSize);
		int placePosition = Helper.randInt(0, numberEmpty);
		return placeSymbolMaze(maze, mazeSize, placePosition, symbol);
	}

	private int[][] placeSymbolMaze(int[][] maze, int mazeSize, int placePosition, int symbol) {
		int counter = 0;
		for(int i=0; i<mazeSize; i++)
			for(int j=0; j<mazeSize; j++) {
				if(maze[i][j] == empty)
					counter++;
				if(counter == placePosition) {
					maze[i][j] = symbol;
					return maze;
				}
			}
		
		return maze;
	}

	private int getTotalEmpty(int[][] maze, int mazeSize) {
		int counter = 0;
		
		for(int i=0; i<mazeSize; i++)
			for(int j=0; j<mazeSize; j++)
				if(maze[i][j] == empty)
					counter++;
		return counter;
	}

	private int[] placeExit(char[][] maze, int mazeSize) {
		int corner = Helper.randInt(0, 3);
		int position = Helper.randInt(1, mazeSize - 2);
		int[] wallToRemove = new int[2];
		corner = 3;

		// This is for the top and bottom corner
		if (corner < 2) {
			// bottom corner
			if (corner == 1) {
				corner = mazeSize - 1;
				// Set the first wall to be removed
				wallToRemove[0] = corner - 1;
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
			if (corner == 3) {
				corner = mazeSize - 1;

				// Set the first wall to be removed
				wallToRemove[0] = position;
				wallToRemove[1] = corner - 1;
			} else {
				corner = 0;

				// Set the first wall to be removed
				wallToRemove[0] = position;
				wallToRemove[1] = corner + 1;
			}
			// Place exit
			maze[position][corner] = exit;
		}

		return wallToRemove;
	}

	private char[][] generatePath(char[][] maze, int mazeSize, int line, int col,
			int open_space) {
		maze[line][col] = empty;
		open_space++;

		int[] vec = { 0, 1, 2, 3 };
		vec = Helper.sort_arr(vec);

		if (!canMakePath(maze, mazeSize)) {
			return maze;
		}

		for (int i = 0; i < vec.length; i++) {
			if (canGenratePath(maze, mazeSize, line, col + 1) && vec[i] == 0) // right
				maze = generatePath(maze, mazeSize, line, col + 1, open_space);

			if (canGenratePath(maze, mazeSize, line + 1, col) && vec[i] == 1) // down
				maze = generatePath(maze, mazeSize, line + 1, col, open_space);

			if (canGenratePath(maze, mazeSize, line, col - 1) && vec[i] == 2) // left
				maze = generatePath(maze, mazeSize, line, col - 1, open_space);

			if (canGenratePath(maze, mazeSize, line - 1, col) && vec[i] == 3) // up
				maze = generatePath(maze, mazeSize, line - 1, col, open_space);
		}

		return maze;
	}

	private boolean canGenratePath(char[][] maze, int mazeSize, int lin, int col) {
		if (lin == 0 || col == 0 || lin == mazeSize - 1 || col == mazeSize - 1
				|| maze[lin][col] == ' ') // LIMITS AND OCUPIED
			return false;
		if (maze[lin - 1][col] == empty && maze[lin - 1][col + 1] == empty
				&& maze[lin][col + 1] == empty)// CHECK 2x2
			return false;
		if (maze[lin][col + 1] == empty && maze[lin + 1][col + 1] == empty
				&& maze[lin + 1][col] == empty)// CHECK 2x2
			return false;
		if (maze[lin + 1][col] == empty && maze[lin + 1][col - 1] == empty
				&& maze[lin][col - 1] == empty)// CHECK 2x2
			return false;
		if (maze[lin - 1][col] == empty && maze[lin - 1][col - 1] == empty
				&& maze[lin][col - 1] == empty)// CHECK 2x2
			return false;
		if (maze[lin - 1][col + 1] == empty && maze[lin - 1][col] == wall
				&& maze[lin][col + 1] == wall)// CHECK DIAGONAL
			return false;
		if (maze[lin + 1][col + 1] == empty && maze[lin + 1][col] == wall
				&& maze[lin][col + 1] == wall)// CHECK DIAGONAL
			return false;
		if (maze[lin + 1][col - 1] == empty && maze[lin + 1][col] == wall
				&& maze[lin][col - 1] == wall)// CHECK DIAGONAL
			return false;
		if (maze[lin - 1][col - 1] == empty && maze[lin - 1][col] == wall
				&& maze[lin][col - 1] == wall)// CHECK DIAGONAL
			return false;

		return true;
	}

	private boolean canMakePath(char[][] maze, int mazeSize) {
		for (int i = 0; i < mazeSize - 2; i++) { // Example-> [7*7] tests untils
													// [3x3]
			for (int j = 0; j < mazeSize - 2; j++) { // Example-> [7*7] tests
														// untils [3x3]
				if (maze[i + 1][j] == wall && maze[i + 2][j] == wall
						&& maze[i][j + 1] == wall && maze[i + 1][j + 1] == wall
						&& maze[i + 2][j + 1] == wall && maze[i][j + 2] == wall
						&& maze[i + 1][j + 2] == wall
						&& maze[i + 2][j + 2] == wall) {
					return true; // can
				}
			}
		}

		return false; // cant!
	}

}

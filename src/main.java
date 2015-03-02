import java.util.Arrays;
import java.util.Scanner;

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
									   {wall, empty, wall, wall, empty, empty, empty, empty, empty, wall},
									   {wall, wall, wall, wall, wall, wall, wall, wall, wall, wall}};
	
	public static void main(String[] args) {
		int[] hero_loc = findHero(init_map);
		Scanner keyboard = new Scanner(System.in);
		for (int n = 0; n < 10; n++) {
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
	
	private static void move(char movement, int[] hero_loc) {
		switch(movement) {
		case 'a': //left
			if ((init_map[hero_loc[0]][hero_loc[1] - 1] & wall) == 0) {
				init_map[hero_loc[0]][hero_loc[1] - 1] = init_map[hero_loc[0]][hero_loc[1]];
				init_map[hero_loc[0]][hero_loc[1]] = empty;
				hero_loc[1] -= 1;
			}
			break;
		case 'd': //right
			if ((init_map[hero_loc[0]][hero_loc[1] + 1] & wall) == 0) {
				init_map[hero_loc[0]][hero_loc[1] + 1] = init_map[hero_loc[0]][hero_loc[1]];
				init_map[hero_loc[0]][hero_loc[1]] = empty;
				hero_loc[1] += 1;
			}
			break;
		case 'w': //up
			if ((init_map[hero_loc[0] - 1][hero_loc[1]] & wall) == 0) {
				init_map[hero_loc[0] - 1][hero_loc[1]] = init_map[hero_loc[0]][hero_loc[1]];
				init_map[hero_loc[0]][hero_loc[1]] = empty;
				hero_loc[0] -= 1;
			}
			break;
		case 's': //down
			if ((init_map[hero_loc[0] + 1][hero_loc[1]] & wall) == 0) {
				init_map[hero_loc[0] + 1][hero_loc[1]] = init_map[hero_loc[0]][hero_loc[1]];
				init_map[hero_loc[0]][hero_loc[1]] = empty;
				hero_loc[0] += 1;
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
				else System.out.print(" ");
			}
			System.out.println();
		}
	}
}
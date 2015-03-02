import java.util.Arrays;

//BIT 0 -> Wall
//BIT 1 -> Hero
//BIT 2 -> Exit 
//BIT 3 -> Sword
//BIT 4 -> Armed
//BIT 5 -> Dragon
//BIT 6 -> Dragon & Sword

public class main {
	private static int empty = 0;
	private static int wall = BIT(0);
	private static int hero = BIT(1);
	private static int exit = BIT(2);
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
		display(init_map);
	}
	
	private static int BIT(int n) {
		int shifted;
		shifted = 1 << n;
		return shifted;
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
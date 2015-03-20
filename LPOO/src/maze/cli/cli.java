package maze.cli;

public class cli {
	private static int empty = 0;
	private static int wall = BIT(0);
	private static int hero = BIT(1);
	private static int exit = BIT(2);
	private static int sword = BIT(3);
	private static int armed = BIT(4);
	private static int dragon = BIT(5);
	
	public static void display(int[][] maze) {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				if ((maze[i][j] & wall) == BIT(0))
					System.out.print("X");
				else if ((maze[i][j] & (dragon | sword)) == (BIT(3) | BIT(5)))
						System.out.print("F");
				else if ((maze[i][j] & dragon) == BIT(5))
					System.out.print("D");
				else if ((maze[i][j] & hero) == BIT(1))
					System.out.print("H");
				else if ((maze[i][j] & exit) == BIT(2))
					System.out.print("S");
				else if ((maze[i][j] & sword) == BIT(3))
					System.out.print("E");
				else if ((maze[i][j] & armed) == BIT(4))
					System.out.print("A");
				else System.out.print(" ");

				//Evens the display so it looks like a square maze
				System.out.print(" ");
			}
			System.out.println();
		}
	}
	
	private static int BIT(int n) {
		int shifted;
		shifted = 1 << n;
		return shifted;
	}
}

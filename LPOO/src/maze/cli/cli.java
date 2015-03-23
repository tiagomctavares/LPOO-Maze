package maze;

public class cli {
	public cli() {

	}
	
	private static int empty = 0;
	private static int wall = Helper.BIT(0);
	private static int hero = Helper.BIT(1);
	private static int exit = Helper.BIT(2);
	private static int sword = Helper.BIT(3);
	private static int armed = Helper.BIT(4);
	private static int dragon = Helper.BIT(5);
	
	public static void display(int[][] maze) {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				if ((maze[i][j] & wall) == Helper.BIT(0))
					System.out.print("X");
				else if ((maze[i][j] & (dragon | sword)) == (Helper.BIT(3) | Helper.BIT(5)))
						System.out.print("F");
				else if ((maze[i][j] & dragon) == Helper.BIT(5))
					System.out.print("D");
				/*else if ((maze[i][j] & hero) == Helper.BIT(1))
					System.out.print("H");*/
				else if(Logic.hero.getX() == i && Logic.hero.getY() == j)
					if(Logic.hero.isArmed())
						System.out.print("A");
					else
						System.out.print("H");
				else if (Logic.sword.isActive() && Logic.sword.getX() == i && Logic.sword.getY() == j)
					System.out.print("E");
				//else if ((maze[i][j] & armed) == Helper.BIT(4))
					//System.out.print("A");
				else if ((maze[i][j] & exit) == Helper.BIT(2))
					System.out.print("S");
				else System.out.print(" ");

				//Evens the display so it looks like a square maze
				System.out.print(" ");
			}
			System.out.println();
		}
		/*System.out.println("SWORD ACTIVE? "+Logic.sword.isActive());
		System.out.println("HERO ARMED? "+Logic.hero.isArmed());
		System.out.println("HERO: "+Logic.hero.getX()+"-"+Logic.hero.getY()+"  Sword: "+Logic.sword.getX()+"-"+Logic.sword.getY());
		System.out.println("HERO: "+Logic.hero.getX()+"-"+Logic.hero.getY()+"  Sword: "+Logic.sword.getX()+"-"+Logic.sword.getY());
		System.out.println(Logic.hero.equals(Logic.sword));*/
	}
}

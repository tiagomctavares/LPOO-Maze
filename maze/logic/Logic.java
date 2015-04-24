package logic;
import java.util.Random;
import java.util.Scanner;
import cli.cli;

//BIT 0 -> Wall
//BIT 1 -> Hero
//BIT 2 -> Exit 
//BIT 3 -> Sword
//BIT 4 -> Armed
//BIT 5 -> Dragon

public class Logic {
	public Maze maze;
	public Dragon dragon;
	public Hero hero;
	public Sword sword;
	// 1-> Win 2-> Loose  
	private int gameOver;
	
	public Logic(boolean defaultMaze) {
		maze = new Maze();
		gameOver = 0;
		
		if(defaultMaze) {
			maze.generateMap(!defaultMaze, 10);
			hero = new Hero(1, 1);
			sword = new Sword(8, 1);
			dragon = new Dragon(3, 1);
		} else {
			maze.generateMap(defaultMaze, 10); 
		}
	}
	
	public void play(char movement) {
		moveHero(movement);
		
		if (!dragon.isDead()) {
			moveDragon();
		}
		
		checkDragon();
	}

	

	private void checkDragon() {
		int[] hero_loc = hero.getPosition();
		
		String col_detect;
		int dy, dx;
		for (int i = 0; i < 5; i++) {
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
				if (hero.isArmed()) {
					dragon.die();
				} else loseGame();
			}
			hero_loc[0] -= dy;
			hero_loc[1] -= dx;
			
			hero.move(hero_loc);
		}
	}

	private String dragonCollision(int[] dragon_loc) {
		if (maze.map[dragon_loc[0]][dragon_loc[1]] == maze.wall)
			return "wall";
		else if (dragon_loc[0] == sword.getX() && dragon_loc[1] == sword.getY())
			return "sword";
		else if (maze.map[dragon_loc[0]][dragon_loc[1]] == maze.exit)
			return "exit";
		
		else return "empty";
	}

	private String collision(int[] hero_loc) {
		
		if (maze.map[hero_loc[0]][hero_loc[1]] == maze.wall)
			return "wall";
		else if (hero_loc[0] == dragon.getX() && hero_loc[1] == dragon.getY())
			return "dragon";
		else if (hero.samePosition(sword.getPosition()))
			return "sword";
		else if (maze.map[hero_loc[0]][hero_loc[1]] == maze.exit)
			return "exit";
		else return "empty";
	}
	
	public boolean gameEnded() {
		if(gameOver == 0)
			return false;
		return true;
	}
	
	public boolean hasWin() {
		if(gameOver == 2)
			return false;
		else
			return true;
	}

	private void loseGame() {
		cli.display(maze.map);
		gameOver = 2;
	}

	private void winGame() {
		cli.display(maze.map);
		gameOver = 1;
	}

	private void moveDragon() {
		int[] dragon_loc = dragon.getPosition();
		int movement = Helper.randInt(0, 4);
		String col_detect;
		int dy, dx;
		dy = dx = 0;
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
		
		col_detect = dragonCollision(dragon_loc);
		
		if (col_detect == "sword" || col_detect == "empty") {
			dragon.move(dragon_loc[0], dragon_loc[1]);
		} else {
			dragon_loc[0] -= dy;
			dragon_loc[1] -= dx;
		}
	}

	private void moveHero(char movement) {
		int hero_loc[] = hero.getPosition();
		
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
			hero.setArmed();
			sword.setActive(false);
		}
		/*else if (col_detect == "empty") {
			init_map[hero_loc[0]][hero_loc[1]] = init_map[hero_loc[0] - dy][hero_loc[1] - dx]; //Copy previous state
			init_map[hero_loc[0] - dy][hero_loc[1] - dx] = empty;
		}*/
		else if (col_detect == "exit") {
			if (hero.isArmed())
				winGame();
			else {
				hero_loc[0] -= dy;
				hero_loc[1] -= dx;
			}
		}
		
		hero.move(hero_loc);
	}
}

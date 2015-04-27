package logic;
import java.util.ArrayList;

//BIT 0 -> Wall
//BIT 1 -> Hero
//BIT 2 -> Exit 
//BIT 3 -> Sword
//BIT 4 -> Armed
//BIT 5 -> Dragon

/**
 * Represents a Game Logic.
 * 
 * @author Tiago Tavares
 * 
 */
public class Logic {
	// Elements
	private Maze maze;
	private ArrayList<Dragon> dragons;
	private Hero hero;
	private Sword sword;
	private ArrayList<Dart> darts;
	private Shield shield;
	private Dragon dragonFire;
	
	// symbols
	/**
	 * Hero char representation
	 */
	public static char heroSymbol = 'H';
	/**
	 * Hero with Sword char representation
	 */
	public static char heroSwordSymbol = 'A';
	/**
	 * Hero with Shield char representation
	 */
	public static char heroShieldSymbol = 'O';
	/**
	 * Hero with Shield and Sword char representation
	 */
	public static char heroSwordShieldSymbol = 'I';

	/**
	 * Dragon char representation
	 */
	public static char dragonSymbol = 'D';
	/**
	 * Dragon sleeping char representation
	 */
	public static char dragonSleepSymbol = 'd';
	/**
	 * Dragon over Sword char representation
	 */
	public static char dragonSwordSymbol = 'F';
	/**
	 * Dragon sleeping over Sword char representation
	 */
	public static char dragonSleepSwordSymbol = 'f';
	/**
	 * Dragon over Shield char representation
	 */
	public static char dragonShieldSymbol = 'G';
	/**
	 * Dragon sleeping over Shield char representation
	 */
	public static char dragonSleepShieldSymbol = 'g';
	/**
	 * Dragon over Dart char representation
	 */
	public static char dragonDartSymbol = 'J';
	/**
	 * Dragon sleeping over Dart char representation
	 */
	public static char dragonSleepDartSymbol = 'j';

	/**
	 * Sword char representation
	 */
	public static char swordSymbol = 'E';
	/**
	 * Shield char representation
	 */
	public static char shieldSymbol = 'P';

	/**
	 * Dart char representation
	 */
	public static char dartSymbol = 'T';

	/**
	 * Wall char representation
	 */
	public static char wallSymbol = 'X';

	// MOVEMENT
	public static char MOVE_UP = 'w';
	public static char MOVE_LEFT = 'a';
	public static char MOVE_RIGHT = 'd';
	public static char MOVE_DOWN = 's';
	
	public static char SHOOT_UP = 't';
	public static char SHOOT_LEFT = 'f';
	public static char SHOOT_RIGHT = 'h';
	public static char SHOOT_DOWN = 'g';
	
	// States
	// 1-> Win 2-> lose eaten 3-> lose Burned
	private int gameOver;
	// 1-> Stopped, 2->Random, 3->Random+Sleep
	private int dragonOptions;
	private int dragonNumber;
	private int dartNumber;
	private GameMessages messages;
	
	private void init() {
		maze = new Maze();
		gameOver = 0;
		dragons = new ArrayList<Dragon>();
		darts = new ArrayList<Dart>();
		messages = new GameMessages();
	}
	
	/**
	 * Constructs and initializes game Logic for default maze
	 * 
	 * @param dragonOptions
	 *			the Dragon movement option for default Maze
	 */	
	public Logic(int dragonOptions) {
		init();
		this.dragonOptions = dragonOptions;
		this.dartNumber = dragonNumber = 1;
		maze.generateMap(true, 10);

		hero = new Hero(1, 1);
		sword = new Sword(8, 1);
		shield = new Shield(5, 3);
		dragons.add(new Dragon(3, 1));
		darts.add(new Dart(1, 2));
	}

	/**
	 * Constructs and initializes game Logic for random maze
	 * 
	 * @param mapSize
	 * 			the maze Width and Height
	 * @param dragonOptions
	 *			the Dragon movement option
	 * @param dragonNumber
	 * 			the number of Dragons to spawn
	 */
	public Logic(int mapSize, int dragonOptions, int dragonNumber) {
		init();
		this.dragonOptions = dragonOptions;
		this.dartNumber = this.dragonNumber = dragonNumber;
		
		maze.generateMap(false, mapSize);
		
		placeCharacters();
		refreshSymbols();
	}


	/**
	 * Returns the map represented by an array of chars
	 * 
	 * @return <code>char[][]</code> representation of the map
	 */
	public char[][] getMap() {
		char[][] map = Helper.deepCopy(maze.map);
		
		for (int i = 0; i < maze.mapSize; i++) {
			for (int j = 0; j < maze.mapSize; j++) {
				boolean characterFound = false;
				
				for (Dragon dragon : dragons) {
					if(dragon.samePosition(i, j)) {
						map[i][j] = dragon.getSymbol();
						characterFound = true;
						break;
					}
				}
				
				if(!characterFound) {
					for (Dart dart : darts) {
						if(dart.samePosition(i, j)) {
							map[i][j] = dart.getSymbol();
							characterFound = true;
							break;
						}
					}
				}
				
				if(!characterFound) {
					
					if(hero.samePosition(i, j) && (gameOverCode() != 2))
						map[i][j] = hero.getSymbol();
					else if(shield.samePosition(i, j) && shield.isActive())
						map[i][j] = shield.getSymbol();
					else if(sword.samePosition(i, j) && sword.isActive())
						map[i][j] = sword.getSymbol();
				}
			}
		}
		
		
		return map;
	}
	
	private void refreshSymbols() {

		for (Dragon dragon : dragons) {

			// Check Dragon and Darts (Update Dragon symbol)
			for (Dart dart : darts) {

				if(dart.isActive() && !dragon.isDead()) {
					if(dragon.samePosition(dart.getPosition()))
						if(dragon.isSleeping())
							dragon.setSymbol(dragonSleepDartSymbol);
						else
							dragon.setSymbol(dragonDartSymbol);
				}
			}
			
			// Check Dragon and Shield (Update Dragon symbol)
			if(shield.isActive() && !dragon.isDead()) {
				if(dragon.samePosition(shield.getPosition()))
					if(dragon.isSleeping())
						dragon.setSymbol(dragonSleepShieldSymbol);
					else
						dragon.setSymbol(dragonShieldSymbol);
			}
			
			// Check Dragon and Sword (Update Dragon symbol)
			if(sword.isActive() && !dragon.isDead()) {
				if(dragon.samePosition(sword.getPosition()))
					if(dragon.isSleeping())
						dragon.setSymbol(dragonSleepSwordSymbol);
					else
						dragon.setSymbol(dragonSwordSymbol);
			}
		}
		
	}

	private void placeCharacters() {
		int openSpaces = maze.getTotalEmpty();
		int[] position;
		//Place Dragon
		
		for(int j = 0; j<dragonNumber; j++) {
			position = maze.placeCharacter(Helper.randInt(1, openSpaces));
			dragons.add(new Dragon(position[0], position[1]));
			openSpaces--;
		}
		
		//Place Sword
		position = maze.placeCharacter(Helper.randInt(1, openSpaces));
		sword = new Sword(position[0], position[1]);
		openSpaces--;
		
		//Place Shield
		position = maze.placeCharacter(Helper.randInt(1, openSpaces));
		shield = new Shield(position[0], position[1]);
		openSpaces--;
		
		// Place darts
		for(int j = 0; j<dartNumber; j++) {
			position = maze.placeCharacter(Helper.randInt(1, openSpaces));
			darts.add(new Dart(position[0], position[1]));
			openSpaces--;
		}
		
		// Place Hero (Attention Dragons)
		while(true) {
			position = maze.placeCharacter(Helper.randInt(1, openSpaces));
			
			int dragonsOk = 0;
			for (Dragon dragon : dragons) {
				if( !dragonEncounter(dragon, position[0], position[1]))
					dragonsOk++;
			}
			
			if( dragonsOk == dragonNumber) {
				hero = new Hero(position[0], position[1]);
				break;
			} else {
				maze.map[position[0]][position[1]] = maze.empty;
			}
		}
		
		maze.removeOcupied();
		
	}


	/**
	 * Order from the interface to play a turn
	 * 
	 * @param movement
	 * 			the movement action character
	 * 
	 */
	public void play(char movement) {
		moveHero(movement);
		useDart(movement);

		for (Dragon dragon : dragons) {
			
			if (!dragon.isDead() && dragonOptions != 1) {
				dragon.awake();
				moveDragon(dragon);
			}
			
		}
		
		refreshCharacters();
	}
	
	/**
	 * Returns if the game has ended
	 * 
	 * @return <code>true</code> if the game has ended.
	 */
	public boolean gameEnded() {
		if(gameOver == 0)
			return false;
		return true;
	}
	
	/**
	 * Returns the code of end game
	 * 
	 * @return this game over state.
	 */
	public int gameOverCode() {
		return gameOver;
	}

	private void refreshCharacters() {
		int[] heroLocation = hero.getPosition();
		
		for (Dragon dragon : dragons) {
			if( !dragon.isDead()) {
				// Set dragon Symbol to default
				dragon.setSymbol(dragonSymbol);
				
				// Check if dragon is Sleeping
				if(dragon.isSleeping()) {
					dragon.setSymbol(dragonSleepSymbol);
				}
				
				// Check for Dragons Collision
				boolean dragonEncounter = dragonEncounter(dragon, heroLocation[0], heroLocation[1]);
				
				if(dragonEncounter) {
					if(hero.isArmed())
						dragon.die();
					else if(!dragon.isSleeping())
						this.loseGame(2);
				}
				
				if( !gameEnded())
				{
					// Dragons Breath
					if( !hero.hasShield()) {
						boolean dragonFireHitHero = false;
						if( !dragon.isDead() && !dragon.isSleeping())
							dragonFireHitHero = dragonFireHitHero(dragon);
					
						if(dragonFireHitHero) {
							loseGame(3);
							dragonFire = dragon;
						}
					}
				}
			}
		}
		
		refreshSymbols();
	}

	private boolean dragonEncounter(Dragon dragon, int heroX, int heroY) {
		boolean dragonEncounter = false;
		
		if(dragon.samePosition(heroX, heroY))
			dragonEncounter = true;
		else if(dragon.samePosition(heroX+1, heroY))
			dragonEncounter = true;
		else if(dragon.samePosition(heroX-1, heroY))
			dragonEncounter = true;
		else if(dragon.samePosition(heroX, heroY+1))
			dragonEncounter = true;
		else if(dragon.samePosition(heroX, heroY-1))
			dragonEncounter = true;
		
		return dragonEncounter;
	}

	private boolean dragonFireHitHero(Dragon dragon) {
		int x = dragon.getX();
		int y = dragon.getY();
		
		if(hero.samePosition(x, y))
			return true;


		if(dragonFireHitHeroIteration(dragon, x, y, 0, 1, 0))
			return true;
		if(dragonFireHitHeroIteration(dragon, x, y, 0, -1, 0))
			return true;
		if(dragonFireHitHeroIteration(dragon, x, y, 1, 0, 0))
			return true;
		if(dragonFireHitHeroIteration(dragon, x, y, -1, 0, 0))
			return true;
		
		return false;
	}

	private boolean dragonFireHitHeroIteration(Dragon dragon, int x, int y, int incrementX, int incrementY, int iteration) {
		if(iteration > 3)
			return false;
		
		if(maze.map[x][y] == maze.wall)
			return false;
		
		if(hero.samePosition(x, y))
			return true;
		
		return dragonFireHitHeroIteration(dragon, x+incrementX, y+incrementY, incrementX, incrementY, iteration+1);
	}

	private boolean canMoveHeroPosition(int x, int y) {
		
		if (maze.map[x][y] == maze.wall)
			return false;
		else if (sword.samePosition(x, y)) {
			hero.setArmed();
			sword.setActive(false);
		}else if (shield.samePosition(x, y)) {
			hero.grabShield();
			shield.setActive(false);
		}else if (maze.map[x][y] == maze.exit && hero.isArmed()) {
			winGame();
			return true;
		}
		
		if(maze.map[x][y] == maze.exit) {
			return false;
		}

		for (Dart dart : darts) {
			if(dart.samePosition(x, y) && dart.isActive()) {
				dart.GrabDart();
				hero.setDart();
			}
		}
		
		return true;
	}

	private void loseGame(int code) {
		if(code == 2)
			hero.setActive(false);
		gameOver = code;
	}

	private void winGame() {
		gameOver = 1;
	}

	private void moveDragon(Dragon dragon) {
		int[] dragonLocation = dragon.getPosition();
		ArrayList<Integer> movement = allowedMoveDragon(dragon);
		int randomMove = Helper.randInt(0, movement.size()-1);
		
		if(movement.get(randomMove) == 0)
			dragonLocation[0]--;
		else if((movement.get(randomMove) == 1))
			dragonLocation[0]++;
		else if((movement.get(randomMove) == 2))
			dragonLocation[1]--;
		else if((movement.get(randomMove) == 3))
			dragonLocation[1]++;
		else if((movement.get(randomMove) == 4)) {
			dragon.sleep();
			return;
		}
		
		dragon.move(dragonLocation[0], dragonLocation[1]);
	}

	private ArrayList<Integer> allowedMoveDragon(Dragon dragon) {
		ArrayList<Integer> moves = new ArrayList<>();

		// Up
		if(dragonCanMove(dragon, dragon.getX()-1, dragon.getY())) moves.add(0);
		
		// Down
		if(dragonCanMove(dragon, dragon.getX()+1, dragon.getY())) moves.add(1);

		// Left
		if(dragonCanMove(dragon, dragon.getX(), dragon.getY()-1)) moves.add(2);

		// Right
		if(dragonCanMove(dragon, dragon.getX(), dragon.getY()+1)) moves.add(3);
		
		// Sleep
		if(dragonOptions == 3)
			moves.add(4);
		
		return moves;
	}

	private boolean dragonCanMove(Dragon dragon, int i, int y) {
		
		if(maze.map[i][y] == maze.wall || maze.map[i][y] == maze.exit || dragon.samePosition(hero.getPosition()))
			return false;
		
		return true;
	}

	private void moveHero(char movement) {
		int heroLocation[] = hero.getPosition();
		
		if(movement == MOVE_LEFT)
			heroLocation[1]--;
		else if(movement == MOVE_RIGHT)
			heroLocation[1]++;
		else if(movement == MOVE_UP)
			heroLocation[0]--;
		else if(movement == MOVE_DOWN)
			heroLocation[0]++;		
	
		if(canMoveHeroPosition(heroLocation[0], heroLocation[1]))
			hero.move(heroLocation);
	}
	
	private void useDart(char movement) {
		if(hero.hasDart()) {
			int heroLocation[] = hero.getPosition();
			int directionX = 0;
			int directionY = 0;
	
			// left
			if(movement == SHOOT_LEFT)
				directionY = -1;
			// right
			else if(movement == SHOOT_RIGHT)
				directionY = 1;
			// up
			else if(movement == SHOOT_UP)
				directionX = -1;
			// down
			else if(movement == SHOOT_DOWN)
				directionX = 1;
			
			if(directionX != 0 || directionY != 0) {
				hero.useDart();
				dartIteration(directionX, directionY, heroLocation[0]+directionX, heroLocation[1]+directionY);
			}
		}
	}

	private void dartIteration(int directionX, int directionY, int i, int j) {
		if(maze.map[i][j] == maze.wall)
			return ;
		
		for (Dragon dragon : dragons) {
			if( !dragon.isDead() && dragon.samePosition(i, j)) {
				dragon.die();
				return ;
			}
		}

		dartIteration(directionX, directionY, i+directionX, j+directionY);
	}
	
	/**
	 * Returns the number of darts in hero backpack
	 * 
	 * @return hero number of darts
	 */
	public int heroDarts() {
		return hero.getDarts();
	}

	/**
	 * Returns the message to show the user at the end of the game
	 * 
	 * @return the message to be shown
	 */
	public String getGameEndedMessage() {
		// Win
		if(gameOverCode() == 1)
			return messages.getEndMessage(0);
		// Lost
		if(gameOverCode() == 2)
			return messages.getEndMessage(1);
		// Lost burned
		if(gameOverCode() == 3)
			return messages.getEndMessage(2);
		
		return "";
	}

	/**
	 * Returns the message to show the user at the end of the turn
	 * 
	 * @return the message to be shown
	 */
	public String getGameMessage() {

		
		if(!hero.hasShield())
			return messages.getPlayMessage(0);
		else if(hero.hasShield() && hero.isArmed())
			return messages.getPlayMessage(1);
		else
			return messages.getPlayMessage(2);
		
	}

	/**
	 * Returns an arrayList of the Dragons used in UnitTest
	 * 
	 * @return array list with the dragons in the map
	 */
	public ArrayList<Dragon> getDragons() {
		return dragons;
	}

	/**
	 * Return the Hero in the map used in UnitTest
	 * 
	 * @return Hero present in the map
	 */
	public Hero getHero() {
		return hero;
	}

	/**
	 * Return the Hero in the map used in UnitTest
	 * 
	 * @return Sword present in the map
	 */
	public Sword getSword() {
		return sword;
	}
	
	/**
	 * Returns an arrayList of the Darts used in UnitTest
	 * 
	 * @return array list with the darts in the map
	 */
	public ArrayList<Dart> getDarts() {
		return darts;
	}

	/**
	 * Return the Shield in the map used in UnitTest
	 * 
	 * @return Shield present in the map
	 */
	public Shield getShield() {
		return shield;
	}

	/**
	 * Returns the Dragon that killed the Hero
	 * 
	 * @return Dragon that killed hero
	 */
	public Dragon getDragonFire() {
		return dragonFire;
	}

	/**
	 * Finds the positions that the dragon launched fire to kill the Hero
	 * 
	 * @return arrayList with x, y position of the fire
	 */
	public ArrayList<Integer[]> getDragonBreath() {
		ArrayList<Integer[]> positions = new ArrayList<Integer[]>();
		
		if(gameOver == 3) {
			Dragon dragon = getDragonFire();

			int x = dragon.getX();
			int y = dragon.getY();
			if(hero.getX() != x) {
				if(hero.getX() < x) {
					for(int i = 1; i<=3; i++)
						if(maze.map[x-i][y] != wallSymbol)
							positions.add(new Integer[]{ x - i, y});
						else
							break;
				} else {
					for(int i = 1; i<=3; i++)
						if(maze.map[dragon.getX()+i][y] != wallSymbol)
							positions.add(new Integer[]{ x + i, y});
						else
							break;					
				}
			} else {
				if(hero.getY() < dragon.getY()) {
					for(int i = 1; i<=3; i++)
						if(maze.map[x][y-i] != wallSymbol)
							positions.add(new Integer[]{ x , y - i});
						else
							break;
				} else {
					for(int i = 1; i<=3; i++)
						if(maze.map[x][y+i] != wallSymbol)
							positions.add(new Integer[]{ x , y + i});
						else
							break;					
				}
			}
		}
		
		return positions;		
	}
}


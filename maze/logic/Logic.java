package logic;
import java.util.ArrayList;

//BIT 0 -> Wall
//BIT 1 -> Hero
//BIT 2 -> Exit 
//BIT 3 -> Sword
//BIT 4 -> Armed
//BIT 5 -> Dragon

public class Logic {
	// Elements
	private Maze maze;
	private ArrayList<Dragon> dragons;
	private Hero hero;
	private Sword sword;
	private ArrayList<Dart> darts;
	private Shield shield;
	
	// symbols
	private char dragonCharacterSymbol;
	private char dragonSleepCharacterSymbol;
	private char dragonSleepSymbol;
	private char dragonSymbol;
	
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
		
		dragonCharacterSymbol = 'F';
		dragonSleepSymbol = 'd';
		dragonSymbol = 'D';
		dragonSleepCharacterSymbol = 'f';
	}
	
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
	
	public Logic(int mapSize, int dragonOptions, int dragonNumber) {
		init();
		this.dragonOptions = dragonOptions;
		this.dartNumber = this.dragonNumber = dragonNumber;
		
		maze.generateMap(false, mapSize);
		
		placeCharacters();
		refreshSymbols();
	}

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
					
					if(hero.samePosition(i, j) && (gameOverCode() == 0 || gameOverCode() == 1))
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

				if(sword.isActive() && !dragon.isDead()) {
					if(dragon.samePosition(dart.getPosition()))
						if(dragon.isSleeping())
							dragon.setSymbol(dragonSleepCharacterSymbol);
						else
							dragon.setSymbol(dragonCharacterSymbol);
				}
			}
			
			// Check Dragon and Shield (Update Dragon symbol)
			if(shield.isActive() && !dragon.isDead()) {
				if(dragon.samePosition(shield.getPosition()))
					if(dragon.isSleeping())
						dragon.setSymbol(dragonSleepCharacterSymbol);
					else
						dragon.setSymbol(dragonCharacterSymbol);
			}
			
			// Check Dragon and Sword (Update Dragon symbol)
			if(sword.isActive() && !dragon.isDead()) {
				if(dragon.samePosition(sword.getPosition()))
					if(dragon.isSleeping())
						dragon.setSymbol(dragonSleepCharacterSymbol);
					else
						dragon.setSymbol(dragonCharacterSymbol);
			}
		}
		
	}

	private void placeCharacters() {
		int openSpaces = maze.getTotalEmpty();
		int[] position;
		//Place Dragon
		
		for(int j = 0; j<dragonNumber; j++) {
			position = maze.placeCharacter(Helper.randInt(0, openSpaces-1));
			dragons.add(new Dragon(position[0], position[1]));
			openSpaces--;
		}
		
		//Place Sword
		position = maze.placeCharacter(Helper.randInt(0, openSpaces-1));
		sword = new Sword(position[0], position[1]);
		openSpaces--;
		
		//Place Shield
		position = maze.placeCharacter(Helper.randInt(0, openSpaces-1));
		shield = new Shield(position[0], position[1]);
		openSpaces--;
		
		// Place darts
		for(int j = 0; j<dartNumber; j++) {
			position = maze.placeCharacter(Helper.randInt(0, openSpaces-1));
			darts.add(new Dart(position[0], position[1]));
			openSpaces--;
		}
		
		// Place Hero (Attention Dragons)
		while(true) {
			position = maze.placeCharacter(Helper.randInt(0, openSpaces-1));
			
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
	
	public boolean gameEnded() {
		if(gameOver == 0)
			return false;
		return true;
	}
	
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
					
						if(dragonFireHitHero)
							loseGame(3);
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


		if(dragonFireHitHeroIteration(dragon, x, y+1, 0, 1, 1))
			return true;
		if(dragonFireHitHeroIteration(dragon, x, y-1, 0, -1, 1))
			return true;
		if(dragonFireHitHeroIteration(dragon, x+1, y, 1, 0, 1))
			return true;
		if(dragonFireHitHeroIteration(dragon, x-1, y, -1, 0, 1))
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
		
		return dragonFireHitHeroIteration(dragon, x+incrementX, y+incrementY, incrementX, incrementY, iteration++);
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
		
		if(movement == 'a')
			heroLocation[1]--;
		else if(movement == 'd')
			heroLocation[1]++;
		else if(movement == 'w')
			heroLocation[0]--;
		else if(movement == 's')
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
			if(movement == 'f')
				directionY = -1;
			// right
			else if(movement == 'h')
				directionY = 1;
			// up
			else if(movement == 't')
				directionX = -1;
			// down
			else if(movement == 'g')
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

	public int heroDarts() {
		return hero.getDarts();
	}

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

	public String getGameMessage() {

		
		if(!hero.hasShield())
			return messages.getPlayMessage(0);
		else if(hero.hasShield() && hero.isArmed())
			return messages.getPlayMessage(1);
		else
			return messages.getPlayMessage(2);
		
	}

	public Maze getMaze() {
		return maze;
	}

	public ArrayList<Dragon> getDragons() {
		return dragons;
	}

	public Hero getHero() {
		return hero;
	}

	public Sword getSword() {
		return sword;
	}

	public ArrayList<Dart> getDarts() {
		return darts;
	}

	public Shield getShield() {
		return shield;
	}

	public char getDragonCharacterSymbol() {
		return dragonCharacterSymbol;
	}

	public char getDragonSleepCharacterSymbol() {
		return dragonSleepCharacterSymbol;
	}

	public char getDragonSleepSymbol() {
		return dragonSleepSymbol;
	}

	public char getDragonSymbol() {
		return dragonSymbol;
	}
}


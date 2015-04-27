package logic;

import java.util.Arrays;

/**
 * Abstract Class, represents a Character/Symbol (Extended by all Characters)
 * 
 * @author Tiago Tavares
 * 
 */
public abstract class Symbol {
	private char symbol;
	private int[] position = {0, 0};
	private boolean active;
	
	public Symbol(char symbol, int x, int y) {
		this.symbol = symbol;
		setPosition(x, y);
		active = true;
	}

	public int[] getPosition() {
		return Arrays.copyOf(position, position.length);
	}

	public int getX() {
		return position[0];
	}

	public int getY() {
		return position[1];
	}
	
	public char getSymbol() {
		return symbol;
	}
	
	public void setSymbol(char newSymbol) {
		symbol = newSymbol;
	}
	
	protected void setPosition(int x, int y) {
		position[0] = x;
		position[1] = y;
		
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean samePosition(int x, int y) {
		return (this.getX() == x && this.getY() == y && active);
	}
	
	public boolean samePosition(int[] position) {
		return samePosition(position[0], position[1]);
	}
}
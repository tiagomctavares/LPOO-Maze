package logic;

public abstract class Symbol {
	private int symbol;
	private int[] position = {0, 0};
	private boolean active;
	
	public Symbol(int symbol, int x, int y) {
		this.symbol = symbol;
		setPosition(x, y);
		active = true;
	}

	public int[] getPosition() {
		return position;
	}

	public int getX() {
		return position[0];
	}

	public int getY() {
		return position[1];
	}
	
	public int getSymbol() {
		return symbol;
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

	
	public boolean samePosition(int[] position) {
		if(this.getX() == position[0] && this.getY() == position[1])
			return true;
		return false;
	}
}
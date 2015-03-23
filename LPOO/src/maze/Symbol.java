package maze;

public abstract class Symbol {
	private int symbol;
	private int[] position = {0, 0};
	
	public Symbol(int symbol, int x, int y) {
		this.symbol = symbol;
		setPosition(x, y);
	}

	public int[] getPosition() {
		return position;
	}
	
	public int getSymbol() {
		return symbol;
	}

	protected void setPosition(int x, int y) {
		position[0] = x;
		position[1] = y;
		
	}
}
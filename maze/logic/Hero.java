package logic;

public class Hero extends Symbol {
	private boolean armed = false;

	public Hero(int x, int y) {
		super('H', x, y);
	}

	public boolean move(int x, int y) {
		super.setPosition(x, y);
		return true;
	}

	public boolean move(int[] position) {
		return this.move(position[0], position[1]);
	}

	public boolean isArmed() {
		return armed;
	}

	public void setArmed() {
		this.armed = true;
		super.setSymbol('A');
	}
	
	public char getSymbol() {
		return super.getSymbol();
	}
	
	

}
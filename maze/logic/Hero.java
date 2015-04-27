package logic;

/**
 * Represents a Hero Character
 * 
 * @author Tiago Tavares
 * 
 */
public class Hero extends Symbol {
	private boolean armed = false;
	private int dart = 0;
	private boolean shield;

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
		if(this.shield)
			super.setSymbol('I');
		else			
			super.setSymbol('A');
	}
	
	public char getSymbol() {
		return super.getSymbol();
	}

	public void setDart() {
		this.dart++;
	}
	
	public void useDart() {
		if(dart>0)
			this.dart--;
	}
	
	public boolean hasDart() {
		return dart!=0;
	}

	public int getDarts() {
		return dart;
	}

	public void grabShield() {
		this.shield = true;		
		if(this.isArmed())
			super.setSymbol('I');
		else
			super.setSymbol('O');
	}

	public boolean hasShield() {
		return shield;
	}
	
	

}
package logic;

/**
 * Represents a Shield Character
 * 
 * @author Tiago Tavares
 * 
 */
public class Shield extends Symbol {

	public Shield(int x, int y) {
		super('P', x, y);
	}
	
	public void GrabShield() {
		super.setActive(false);
	}
	
}
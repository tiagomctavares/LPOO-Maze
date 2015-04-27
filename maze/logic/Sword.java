package logic;

/**
 * Represents a Sword Character
 * 
 * @author Tiago Tavares
 * 
 */
public class Sword extends Symbol {

	public Sword(int x, int y) {
		super('E', x, y);
	}
	
	public void GrabSword() {
		super.setActive(false);
	}
	
}
package logic;

public class Sword extends Symbol {

	public Sword(int x, int y) {
		super('E', x, y);
	}
	
	public void GrabSword() {
		super.setActive(false);
	}
	
}
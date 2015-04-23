package logic;

public class Sword extends Symbol {

	public Sword(int x, int y) {
		super(Helper.BIT(3), x, y);
	}
	
	public void GrabSword() {
		super.setActive(false);
	}
	
}
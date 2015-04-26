package logic;

public class Dart extends Symbol {

	public Dart(int x, int y) {
		super('T', x, y);
	}
	
	public void GrabDart() {
		super.setActive(false);
	}
	
}

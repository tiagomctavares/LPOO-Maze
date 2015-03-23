package maze;

public class Hero extends Symbol {

	public Hero(int x, int y) {
		super(Helper.BIT(1), x, y);
	}
	
	public boolean move(int x, int y) {
		super.setPosition(x, y);
		return true;
	}
	
}
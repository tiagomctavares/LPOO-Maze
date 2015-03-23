package maze;

public class Dragon extends Symbol {

	public Dragon(int x, int y) {
		super(Helper.BIT(5), x, y);
	}
	
	public boolean move(int x, int y) {
		super.setPosition(x, y);
		return true;
	}
}
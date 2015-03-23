package maze;
import maze.Sword;

public class Dragon extends Symbol {

	public Dragon(int x, int y) {
		super(Helper.BIT(5), x, y);
	}
	
	public boolean move(int x, int y) {
		super.setPosition(x, y);
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		Sword sword;
		if(obj.getClass().toString() == "Sword") {
			sword = (Sword) obj;
			return (sword.isActive() && this.getX() == sword.getX() && this.getY() == sword.getY());
		}
		
		return false;		
	}
}
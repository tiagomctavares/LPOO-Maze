package logic;

public class Dragon extends Symbol {

	public Dragon(int x, int y) {
		super('D', x, y);
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
	
	public boolean isDead() {
		return !super.isActive();
	}
	
	public void die() {
		super.setActive(false);
	}
	
	
}
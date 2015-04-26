package logic;

import java.util.ArrayList;

public class GameMessages {
	ArrayList<String> playMessages;
	ArrayList<String> endMessages;
	
	public GameMessages() {
		playMessages = new ArrayList<String>();
		endMessages = new ArrayList<String>();

		// Game States
		// Win Game
		endMessages.add("How is this possible?! You cheated! No one can beat my Dragons!");
		// Lose Collision
		endMessages.add("What a great meal for my pet Dragons!");
		// Lose Burned
		endMessages.add("Extra crispy Hero! Royal feast for my Dragons!");
		
		
		// Default Message
		playMessages.add("Without the shield you are nothing agains my Dragons! (P -> shield)");
		// Hero has shield and sword
		playMessages.add("Please mercy on my Dragons! I promise to let you go for today!");
		// Hero has shield
		playMessages.add("The Dragons can't burn you! But you will be eaten raw!");
	}
	
	public String getPlayMessage(int index) {
		return playMessages.get(index);
	}
	
	public String getEndMessage(int index) {
		return endMessages.get(index);
	}
}

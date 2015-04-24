package logic;

import java.util.Random;

public class Helper {

	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}


	public static int[] sort_arr(int[] array) {
		for (int i = array.length; i > 1; i--) {
			int temp = array[i - 1];
			int randIx = (int) (Math.random() * i);
			array[i - 1] = array[randIx];
			array[randIx] = temp;
		}
		return array;
	}
}
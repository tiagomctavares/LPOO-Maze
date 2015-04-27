package logic;

import java.util.Arrays;
import java.util.Random;

/**
 * Helper class used for multiple operations
 * 
 * @author Tiago Tavares
 * 
 */
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
	
	public static char[][] deepCopy(char[][] original) {
	    if (original == null) {
	        return null;
	    }

	    final char[][] result = new char[original.length][];
	    for (int i = 0; i < original.length; i++) {
	        result[i] = Arrays.copyOf(original[i], original[i].length);
	        // For Java versions prior to Java 6 use the next:
	        // System.arraycopy(original[i], 0, result[i], 0, original[i].length);
	    }
	    
	    return result;
	}
}
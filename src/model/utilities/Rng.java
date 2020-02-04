package model.utilities;

import java.util.concurrent.ThreadLocalRandom;

/**
 * To generate all the random aspects of the game : dice, cards, countries assignment...
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public class Rng {
	
	/**
	 * The method is to get the random integer.
	 * @param min The minimum number of the index with int type.
	 * @param max The maximum number of the index with int type.
	 * @return Return a random integer within the range.
	 */
	public static int getRandomInt(int min, int max) 
	{
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
}

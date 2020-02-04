package classTests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.utilities.Rng;

/**
 * Test the random method
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public class testRandom {
	
	/**
	 * test the random can give right value
	 */
	@Test
	public void testRnd() {
		
		for(int i = 1; i < 500; i++) {
			int x = Rng.getRandomInt(1, 10);
			
			assertTrue(x >= 0);
			assertTrue(x <= 10);
		}
	}
}

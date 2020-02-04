package classTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.gameplay.Dices;


/**
 * Testing dice rolling
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 *
 */
public class testDices {
	Dices d;
	
	/**
	 * Testing dice rolling with a lot of armies in the countries
	 */
	@Test
	public void testMaxDices() {
		d = new Dices(10, 5);
		
		d.roll();
		
		assertEquals(d.getDefenderMaxDices(), 2);
		assertEquals(d.getAttackerMaxDices(), 3);
	}
	
	/**
	 * Testing dice rolling with few armies in the countries
	 */
	@Test
	public void testMaxDices2() {
		d = new Dices(2, 1);
		
		d.roll();
		
		assertEquals(d.getDefenderMaxDices(), 1);
		assertEquals(d.getAttackerMaxDices(), 1);
	}
	
	/**
	 * Testing loss calculations
	 */
	@Test
	public void testLossCalculation() {
		d = new Dices(2, 1);
		
		d.roll();
		
		assertTrue(d.getAttackerLoss() <= 2);
		assertTrue(d.getAttackerLoss() >= 0);
		assertTrue(d.getDefenderLoss() <= 1);
		assertTrue(d.getDefenderLoss() >= 0);
	}
}

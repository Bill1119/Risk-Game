package classTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import model.map.Country;

/**
 * test the country function
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 *
 */
public class testCountry {

	/**
	 * the Country c
	 */
	Country c;
	
	/**
	 * test country can get right value and string
	 */
	@Test
	public void testMethods() {
		Country.Counter = 0;
		c = new Country("France");
		c.setArmyNumber(10);
		assertEquals(c.getArmyNumber(), 10);
		
		assertEquals(c.getNumber(), 1);
		assertSame(c.getName(), "France");

		Country c2 = new Country("China");
		assertEquals(c2.getNumber(), 2);
		
		c.linkTo(c2);
		assertEquals(c.neighbors.size(), 1);
		
		assertEquals(c2.neighbors.size(), 0);
	}
}

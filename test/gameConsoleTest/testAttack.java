package gameConsoleTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.gameplay.Dices;
import model.gameplay.Phase;
import model.gameplay.Player;
import model.gameplay.strategy.Human;
import model.map.Continent;
import model.map.Country;
import model.map.Map;

/**
 * This class tests the attack phase
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 *
 */
public class testAttack {
	
	static Player p1, p2;
	static Country cty1, cty2, cty3, cty4;
	static Continent con1;
	static Map map;
	static Phase phase;
	/**
	 * Set up the test environment of the test class
	 */
	@BeforeClass
	public static void beforeClass() {
		map = new Map();
		p1 = new Player(1, 5, map, new Human());
		p2 = new Player(2, 5, map, new Human());
		
		cty1= new Country("London");
		cty2 = new Country("Beijing");
		cty3 = new Country("Paris");
		cty4 = new Country("A");
		
		con1 = new Continent("",7);
		
	}
	
	
	/*/**
	 * initiating 3 continents, 5 countries, country 1,4,5 is in continent 1, country 2 in continent 2
	 * country 3 in continent 3
	*/
	/**
	 * The test method for adding countries and continents
	 */
	@Before 
	public void before() 
	{
		con1.addCountry(cty1);
		con1.addCountry(cty2);
		con1.addCountry(cty3);
		con1.addCountry(cty4);
		map.continents.add(con1);
		
		map.countries.add(cty1);
		map.countries.add(cty2);
		map.countries.add(cty3);
		map.countries.add(cty4);
	}

	/**
	 * test the all out method
	 */
	@Test
	public void testValidAttackAllOutMode() {
		
		Phase phase  = new Phase();
		map.setPhase(phase);
		
		cty1.linkTo(cty2);
		cty2.linkTo(cty1);
		cty3.linkTo(cty2);
		cty2.linkTo(cty3);
		cty3.linkTo(cty4);
		cty4.linkTo(cty3);
		
		cty1.setArmyNumber(2);
		cty2.setArmyNumber(2);
		cty3.setArmyNumber(2);
		cty4.setArmyNumber(2);
		
		cty1.setPlayer(p1);
		cty2.setPlayer(p2);
		cty3.setPlayer(p1);
		cty4.setPlayer(p1);
		
		boolean conquered = p1.allOutAttack(cty1, cty2);
		// Attacker XOR Defender wins
		assertTrue(cty1.getArmyNumber() == 1 || cty2.getArmyNumber() == 0);
		assertFalse(cty1.getArmyNumber() == 1 && cty2.getArmyNumber() == 0);
		
		if(cty1.getArmyNumber() == 1) {	//Defender wins
			assertEquals(cty1.getPlayer(), p1);
			assertEquals(cty2.getPlayer(), p2);
		} else {						//Attacker wins : he conquers the country
			assertEquals(cty1.getPlayer(), p1);
			assertEquals(cty2.getPlayer(), p1);
			if(conquered) {
				Method method = null;
				// Set conquer method to public with reflection
				try {
					method = Player.class.getDeclaredMethod("conquer", new Class[] {Country.class});
				} catch(Exception e1) {
					e1.printStackTrace();
				}
				method.setAccessible(true);
				try {
					method.invoke(p1, cty2);
				} catch (Exception e) {
					e.printStackTrace();
				}
				p1.conquestMove(cty1, cty2, 2);
			}
			assertEquals(cty1.getPlayer(), p1);
			assertEquals(cty2.getPlayer(), p1);
			assertTrue(map.isOwned());
		}
	}
	
	/**
	 * test the checking of not connect country try to attack each other
	 */
	@Test
	public void testAttackNotConnected() {
		
		Phase phase  = new Phase();
		map.setPhase(phase);
		
		cty3.setArmyNumber(12);
		cty4.setArmyNumber(10);
		
		cty3.setPlayer(p1);
		cty4.setPlayer(p2);
		
		p1.allOutAttack(cty3, cty4);
		assertEquals(cty3.canAttack(), false);
		assertEquals(cty4.canBeAttackedBy(cty3), false);
	}
	
	/**
	 * test the ClassicMode is working
	 */
	@Test
	public void testValidAttackClassicMode() {
		
		Phase phase  = new Phase();
		map.setPhase(phase);
		
		cty1.setPlayer(p1);
		cty2.setPlayer(p2);
		cty1.linkTo(cty2);
		cty2.linkTo(cty3);
		
		cty1.setArmyNumber(12);
		cty2.setArmyNumber(10);
		Dices dices = new Dices(cty1.getArmyNumber(), cty2.getArmyNumber());
		dices.setDicesNumber(3, 2);//Set attacker dices to 3, defender dices to 2
		p1.classicAttack(cty1, cty2, dices);
		
		assertEquals(dices.getAttackerLoss() + dices.getDefenderLoss(), 2);
		assertEquals(cty1.getArmyNumber(), 12 - dices.getAttackerLoss());	//Checking 	army deduction
		assertEquals(cty2.getArmyNumber(), 10 - dices.getDefenderLoss());	//Checking 	army deduction

		
	}
}

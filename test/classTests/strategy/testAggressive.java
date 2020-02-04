package classTests.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.gameplay.Player;
import model.gameplay.strategy.Aggressive;
import model.map.Continent;
import model.map.Country;
import model.map.Map;
import model.gameplay.Phase;

/**
 * This class is for testing functions of the Aggressive AI
 * @author skyba
 *
 */
public class testAggressive {

	/**
	 * initial players p1 and p2
	 */
	static Player p1, p2;
	
	/**
	 * initial countries cty1, cty2, cty3, cty4;
	 */
	static Country cty1, cty2, cty3, cty4;
	
	/**
	 * initial Continent con1, con2
	 */
	static Continent con1, con2;
	
	/**
	 * initial map file
	 */
	static Map map;
	
	/**
	 * initial phase state
	 */
	static Phase phase;
	
	/**
	 * Set up the test environment of the test class
	 */
	@BeforeClass
	public static void beforeClass() {
		map = new Map();
		phase = new Phase();
		map.setPhase(phase);
		p1 = new Player(1, 5, map, new Aggressive());
		p2 = new Player(2, 5, map, new Aggressive());
		
		cty1 = new Country("London");
		cty2 = new Country("Beijing");
		cty3 = new Country("Paris");
		cty4 = new Country("A");
		
		con1 = new Continent("",7);
		con2 = new Continent("",3);
	}
	
	
	/**
	 * The test method for adding data to test the functions
	 */
	@Before 
	public void before() 
	{
		con1.addCountry(cty1);
		con1.addCountry(cty2);
		con1.addCountry(cty3);
		con2.addCountry(cty4);
		map.continents.add(con1);
		
		map.countries.add(cty1);
		map.countries.add(cty2);
		map.countries.add(cty3);
		map.countries.add(cty4);
	}
	
	/**
	 * method for testing reinforcing for countries
	 */
	@Test
	public void testReinforceStrongestCountry() 
	{
		cty1.linkTo(cty2);
		cty2.linkTo(cty1);
		cty3.linkTo(cty2);
		cty2.linkTo(cty3);
		cty3.linkTo(cty1);
		cty1.linkTo(cty3);
		
		cty1.setArmyNumber(2);
		cty2.setArmyNumber(2);
		cty3.setArmyNumber(5);
		
		cty1.setPlayer(p1);
		cty2.setPlayer(p2);
		cty3.setPlayer(p1);
		
		p1.ownedCountries.add(cty1);
		p2.ownedCountries.add(cty2);
		p1.ownedCountries.add(cty3);
		p1.setArmies(3);
		
		
		p1.reinforce();
		
		assertSame(8, cty3.getArmyNumber());
		assertSame(cty3, p1.getStrongestCountry());
	}
	
	/**
	 * testing Reinforcing the country that has enemy neighbor
	 */
	@Test
	public void testReinforceHasEnemyNeighborCountry()
	{
		cty1.linkTo(cty2);
		cty2.linkTo(cty1);
		cty3.linkTo(cty2);
		cty2.linkTo(cty3);
		cty3.linkTo(cty1);
		cty1.linkTo(cty3);
		cty1.linkTo(cty4);
		cty4.linkTo(cty1);
		cty1.setArmyNumber(2);
		cty2.setArmyNumber(2);
		cty3.setArmyNumber(2);
		cty4.setArmyNumber(2);
		
		cty1.setPlayer(p1);
		cty2.setPlayer(p2);
		cty3.setPlayer(p1);
		
		p1.ownedCountries.add(cty1);
		p1.ownedCountries.add(cty2);
		p1.ownedCountries.add(cty3);
		p2.ownedCountries.add(cty4);
		
		p1.setArmies(3);
		
		p1.reinforce();
		
		assertSame(5, cty1.getArmyNumber());
	}
	
	/**
	 * method for testing attacking of aggressive AI
	 */
	@Test
	public void testAttack() 
	{
		cty1.linkTo(cty2);
		cty2.linkTo(cty1);
		cty3.linkTo(cty2);
		cty2.linkTo(cty3);
		cty3.linkTo(cty1);
		cty1.linkTo(cty3);
		
		cty1.setArmyNumber(5);
		cty2.setArmyNumber(2);
		cty3.setArmyNumber(40);
		
		cty1.setPlayer(p1);
		cty2.setPlayer(p2);
		cty3.setPlayer(p1);
		
		p1.ownedCountries.add(cty1);
		p2.ownedCountries.add(cty2);
		p1.ownedCountries.add(cty3);
		
		p1.attack();
		
		assertEquals(5,cty1.getArmyNumber());
		assertNotNull(p1.allOutAttack(cty3, cty2));
		assertFalse(p1.allOutAttack(cty3, cty1));
	}
	
	/**
	 * the method for testing fortify method of aggressive AI
	 */
	@Test
	public void testFortify() 
	{
		cty1.linkTo(cty2);
		cty2.linkTo(cty1);
		cty3.linkTo(cty2);
		cty2.linkTo(cty3);
		cty3.linkTo(cty1);
		cty1.linkTo(cty3);
		
		cty1.setArmyNumber(15);
		cty2.setArmyNumber(2);
		cty3.setArmyNumber(40);
		
		cty1.setPlayer(p1);
		cty2.setPlayer(p2);
		cty3.setPlayer(p1);
		
		p1.ownedCountries.add(cty1);
		p2.ownedCountries.add(cty2);
		p1.ownedCountries.add(cty3);
		
		p1.fortify();
		
		assertEquals(54,cty3.getArmyNumber());
		assertEquals(1, cty1.getArmyNumber());
	}
}

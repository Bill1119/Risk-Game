package classTests.strategy;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import model.gameplay.Phase;
import model.gameplay.Player;
import model.gameplay.strategy.Benevolent;
import model.map.Continent;
import model.map.Country;
import model.map.Map;
import model.gameplay.Dices;

/**
 * The class for testing functions of benevolent AI
 * @author skyba
 *
 */
public class testBenevolent 
{
	/**
	 * initial Continents con1,con2
	 */
	Continent con1,con2;
	
	/**
	 * initial Countries cty1,cty2,cty3,cty4;
	 */
	Country cty1,cty2,cty3,cty4;
	
	/**
	 * initial players p1,p2
	 */
	Player p1,p2;
	
	/**
	 * initial map data
	 */
	Map map = new Map();
	
	/**
	 * initial dice for attacker and defender
	 */
	Dices dice = new Dices(0, 0);
	
	/**
	 * The test method for adding data to test the functions
	 */
	@Before
	public void Before() 
	{
		con1 = new Continent("",3);
		con2 = new Continent("",2);
		cty1 = new Country("");
		cty2 = new Country("");
		cty3 = new Country("");
		cty4 = new Country("");
		p1 = new Player(1, 5, map, new Benevolent());
		p2 = new Player(2, 3, map, new Benevolent());
		map.continents.add(con1);
		map.continents.add(con2);
		map.countries.add(cty1);
		map.countries.add(cty2);
		map.countries.add(cty3);
		map.countries.add(cty4);
		con1.addCountry(cty1);
		con2.addCountry(cty2);
		con1.addCountry(cty3);
		con2.addCountry(cty4);
		map.players.add(p1);
		map.players.add(p2);
	}
	
	/**
	 * the method for testing the reinforce of the benevolent AI
	 */
	@Test
	public void testReinforce() {
		
		p1.ownedCountries.add(cty1);
		cty1.setPlayer(p1);
		
		p1.ownedCountries.add(cty2);
		cty2.setPlayer(p1);
		
		p1.ownedCountries.add(cty3);
		cty3.setPlayer(p1);
		
		p2.ownedCountries.add(cty4);
		cty4.setPlayer(p2);
		
		cty1.linkTo(cty2);
		cty2.linkTo(cty1);
		
		cty1.linkTo(cty3);
		cty3.linkTo(cty1);
		
		cty4.linkTo(cty2);
		cty2.linkTo(cty4);
		
		cty1.setArmyNumber(1);
		cty2.setArmyNumber(2);
		cty3.setArmyNumber(3);
		cty4.setArmyNumber(4);
		
		Phase phase  = new Phase();
		map.setPhase(phase);
		
		assertEquals(1,cty1.getArmyNumber());
		assertEquals(2,cty2.getArmyNumber());
		assertEquals(3,cty3.getArmyNumber());
		assertEquals(4,cty4.getArmyNumber());
		
		p1.setArmies(3);
		p1.reinforce();
		
		assertEquals(3,cty1.getArmyNumber());
		assertEquals(3,cty2.getArmyNumber());
		assertEquals(3,cty3.getArmyNumber());
		assertEquals(4,cty4.getArmyNumber());
	}
	
	/**
	 * the method for testing attack method for benevolent AI
	 */
	@Test
	public void testAttack() {
		
		p1.ownedCountries.add(cty1);
		cty1.setPlayer(p1);
		
		p1.ownedCountries.add(cty2);
		cty2.setPlayer(p1);
		
		p1.ownedCountries.add(cty3);
		cty3.setPlayer(p1);
		
		p2.ownedCountries.add(cty4);
		cty4.setPlayer(p2);
		
		cty1.linkTo(cty2);
		cty2.linkTo(cty1);
		
		cty1.linkTo(cty3);
		cty3.linkTo(cty1);
		
		cty4.linkTo(cty2);
		cty2.linkTo(cty4);
		
		cty1.setArmyNumber(1);
		cty2.setArmyNumber(2);
		cty3.setArmyNumber(3);
		cty4.setArmyNumber(4);
		
		Phase phase  = new Phase();
		map.setPhase(phase);
		
		assertEquals(1,cty1.getArmyNumber());
		assertEquals(2,cty2.getArmyNumber());
		assertEquals(3,cty3.getArmyNumber());
		assertEquals(4,cty4.getArmyNumber());
		
		p1.attack();
		
		assertEquals(1,cty1.getArmyNumber());
		assertEquals(2,cty2.getArmyNumber());
		assertEquals(3,cty3.getArmyNumber());
		assertEquals(4,cty4.getArmyNumber());
	}
	
	/**
	 * the method for testing the fortify of the benevolent AI
	 */
	@Test
	public void testFortify() {
		
		p1.ownedCountries.add(cty1);
		cty1.setPlayer(p1);
		
		p1.ownedCountries.add(cty2);
		cty2.setPlayer(p1);
		
		p1.ownedCountries.add(cty3);
		cty3.setPlayer(p1);
		
		p2.ownedCountries.add(cty4);
		cty4.setPlayer(p2);
		
		cty1.linkTo(cty2);
		cty2.linkTo(cty1);
		
		cty1.linkTo(cty3);
		cty3.linkTo(cty1);
		
		cty4.linkTo(cty2);
		cty2.linkTo(cty4);
		
		cty1.setArmyNumber(1);
		cty2.setArmyNumber(6);
		cty3.setArmyNumber(3);
		cty4.setArmyNumber(4);
		
		Phase phase  = new Phase();
		map.setPhase(phase);
		
		assertEquals(1,cty1.getArmyNumber());
		assertEquals(6,cty2.getArmyNumber());
		assertEquals(3,cty3.getArmyNumber());
		assertEquals(4,cty4.getArmyNumber());
		
		p1.fortify();
		
		assertEquals(4,cty1.getArmyNumber());
		assertEquals(3,cty2.getArmyNumber());
		assertEquals(3,cty3.getArmyNumber());
		assertEquals(4,cty4.getArmyNumber());
	}
	
}

package classTests.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.gameplay.Phase;
import model.gameplay.Player;
import model.gameplay.strategy.Human;
import model.map.Continent;
import model.map.Country;
import model.map.Map;
import model.gameplay.Dices;

/**
 * The class for testing functions of Human class
 * @author skyba
 *
 */
public class testHuman {

	/**
	 * the initial data of Continents con1,con2
	 */
	Continent con1,con2;
	
	/**
	 * the initial data of Countries cty1,cty2,cty3,cty4
	 */
	Country cty1,cty2,cty3,cty4;
	
	/**
	 * the initial data of player p1, p2
	 */
	Player p1,p2;
	
	/**
	 * the initial data of map file
	 */
	Map map = new Map();
	
	/**
	 * the initial data of dice
	 */
	Dices dice = new Dices(0, 0);
	
	/**
	 * the method for inputing initial data for testing
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
		p1 = new Player(1, 5, map, new Human());
		p2 = new Player(2, 3, map, new Human());
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
	 * the method for testing reinforce function of Human class
	 */
	@Test
	public void testReinforce() {
		
		p1.ownedCountries.add(cty1);
		cty1.setPlayer(p1);
		
		p1.ownedCountries.add(cty3);
		cty3.setPlayer(p1);
		
		p1.ownedCountries.add(cty2);
		cty2.setPlayer(p1);
		
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
		
		map.addArmiesFromHand(cty1, 3);
		
		assertEquals(4,cty1.getArmyNumber());
		assertEquals(2,cty2.getArmyNumber());
		assertEquals(3,cty3.getArmyNumber());
		assertEquals(4,cty4.getArmyNumber());
		
	}
	
	/**
	 * the method for testing attack function of Human class
	 */
	@Test
	public void testAttack() 
	{	
		p1.ownedCountries.add(cty1);
		cty1.setPlayer(p1);
		
		p1.ownedCountries.add(cty3);
		cty3.setPlayer(p1);
		
		p1.ownedCountries.add(cty2);
		cty2.setPlayer(p1);
		
		p2.ownedCountries.add(cty4);
		cty4.setPlayer(p2);
		
		cty1.linkTo(cty2);
		cty2.linkTo(cty1);
		
		cty1.linkTo(cty3);
		cty3.linkTo(cty1);
		
		cty4.linkTo(cty2);
		cty2.linkTo(cty4);
		
		cty1.setArmyNumber(1);
		cty2.setArmyNumber(5);
		cty3.setArmyNumber(3);
		cty4.setArmyNumber(1);
		
		Phase phase  = new Phase();
		map.setPhase(phase);
		
		p1.allOutAttack(cty2, cty4);
		
		assertTrue(cty2.getArmyNumber() == 1 || cty4.getArmyNumber() == 0);
	}
	
	/**
	 * the method for testing the fortify function for human class
	 */
	@Test
	public void testFortify() 
	{	
		p1.ownedCountries.add(cty1);
		cty1.setPlayer(p1);
		
		p1.ownedCountries.add(cty3);
		cty3.setPlayer(p1);
		
		p1.ownedCountries.add(cty2);
		cty2.setPlayer(p1);
		
		p2.ownedCountries.add(cty4);
		cty4.setPlayer(p2);
		
		cty1.linkTo(cty2);
		cty2.linkTo(cty1);
		
		cty1.linkTo(cty3);
		cty3.linkTo(cty1);
		
		cty4.linkTo(cty2);
		cty2.linkTo(cty4);
		
		cty1.setArmyNumber(3);
		cty2.setArmyNumber(2);
		cty3.setArmyNumber(3);
		cty4.setArmyNumber(1);
		
		Phase phase  = new Phase();
		map.setPhase(phase);
		
		p1.fortificationMove(cty1, cty3, 2);
		assertEquals(5,cty3.getArmyNumber());
	}
}

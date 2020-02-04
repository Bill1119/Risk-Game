package classTests;

import static org.junit.Assert.*;
import org.junit.Before;

import org.junit.Test;

import model.gameplay.Player;
import model.gameplay.strategy.Human;
import model.map.Continent;
import model.map.Country;
import model.map.Map;
/**
 * test the Player class
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 *
 */
public class testPlayer {

	/**
	 * the Player data
	 */
	Player p;
	
	/**
	 * set the new value of player
	 */
	@Before()
	public void Before(){
		p = new Player(1, 2, new Map(), new Human());
		p.setArmies(6);
		p.setNumber(3);
	}
	
	/**
	 * test return value of getArmies
	 */
	@Test
	public void testGetArmies() {
		assertEquals(6,p.getArmies());
	}
	
	/**
	 * test get player ID number
	 */
	@Test
	public void testGetNumber() {
		assertEquals(3, p.getNumber());
	}
	
	/**
	 * test set armies
	 */
	@Test
	public void testSetArmies() {
		p.setArmies(9);
		assertEquals(9, p.getArmies());
	}
	
	/**
	 * test owns to check if the country belongs to the player
	 */
	@Test
	public void testOwns() {
		Map map = new Map();
		Player p2 = new Player(2, 3, new Map(), new Human());
		Country c1 = new Country();
		Country c2 = new Country();
		Continent con = new Continent("",2);
		con.addCountry(c1);
		con.addCountry(c2);
		map.continents.add(con);
		map.countries.add(c1);
		map.countries.add(c2);
		c1.setPlayer(p);
		p.ownedCountries.add(c1);
		c1.setPlayer(p2);
		p2.ownedCountries.add(c2);
		map.players.add(p);
		map.players.add(p2);
		assertTrue(p.owns(c1.getNumber()));
		assertFalse(p2.owns(c1.getNumber()));
	}
	
	//for the addArmiesToCountry , it is already tested in build 1
}

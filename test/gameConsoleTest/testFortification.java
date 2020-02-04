package gameConsoleTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.gameplay.Phase;
import model.gameplay.Player;
import model.gameplay.strategy.Human;
import model.map.Continent;
import model.map.Country;
import model.map.Map;

/**
 * test the connection function in map class
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 *
 */
public class testFortification {

	Continent con1;
	Country cty1,cty2,cty3,cty4;
	Player p1,p2;
	Map map = new Map();
	Phase phase = new Phase();
	/**
	 * initiate continents, countries, players,
	 * put countries into 2 continents
	 * 
	 */
	@Before
	public void before() {
		map.setPhase(phase);
		con1 = new Continent("",3);
		cty1 = new Country("");
		cty2 = new Country("");
		cty3 = new Country("");
		cty4 = new Country("");
		p1 = new Player(1,5, map, new Human());
		p2 = new Player(2,3, map, new Human());
		map.continents.add(con1);
		map.countries.add(cty1);
		map.countries.add(cty2);
		map.countries.add(cty3);
		map.countries.add(cty4);
		con1.addCountry(cty1);
		con1.addCountry(cty2);
		con1.addCountry(cty3);
		con1.addCountry(cty4);
		map.players.add(p1);
		map.players.add(p2);
		
		p1.ownedCountries.add(cty1);
		cty1.setPlayer(p1);
		p2.ownedCountries.add(cty3);
		cty3.setPlayer(p2);
		p1.ownedCountries.add(cty2);
		cty2.setPlayer(p1);
		p1.ownedCountries.add(cty4);
		cty4.setPlayer(p1);
		
		cty1.setArmyNumber(1);
		cty2.setArmyNumber(2);
		cty3.setArmyNumber(3);
		cty4.setArmyNumber(4);
		
		cty1.linkTo(cty2);
		cty2.linkTo(cty2);
		cty2.linkTo(cty3);
		cty3.linkTo(cty2);
		cty3.linkTo(cty4);
		cty4.linkTo(cty3);
	}
	/**
	 * test when origin and destination countries are connected
	 */
	@Test
	public void testConnected() {
		assertTrue(cty3.isConnectedTo(cty4));
		p1.fortificationMove(cty3, cty4, 2);
		assertEquals(cty3.getArmyNumber(), 1);
		assertEquals(cty4.getArmyNumber(), 6);
	}
	/**
	 * test when origin and destination countries are not connected
	 */
	@Test
	public void testNotConnected() {
		assertFalse(cty1.isConnectedTo(cty4));
	}
}

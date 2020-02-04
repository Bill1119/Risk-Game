package gameConsoleTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.gameplay.Card;
import model.gameplay.Player;
import model.gameplay.strategy.Human;
import model.map.Continent;
import model.map.Country;
import model.map.Map;

/**
 * the test for trade card
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 *
 */
public class testTrade {
	Map map = new Map();
	Continent con1 = new Continent("",2);
	Continent con2 = new Continent("",3);
	Continent con3 = new Continent("",4);
	Country cty1 = new Country("");
	Country cty2 = new Country("");
	Country cty3 = new Country("");
	Country cty4 = new Country("");
	Country cty5 = new Country("");
	Player p1 = new Player(1, 3, map, new Human());
	Player p2 = new Player(2, 3, map, new Human());
	
	/**
	 * test values
	 */
	@Before
	public void before() {
		con1.addCountry(cty1);
		con1.addCountry(cty2);
		con2.addCountry(cty3);
		con2.addCountry(cty4);
		con3.addCountry(cty5);
		
		map.continents.add(con1);
		map.continents.add(con2);
		map.continents.add(con3);
		map.countries.add(cty1);
		map.countries.add(cty2);
		map.countries.add(cty3);
		map.countries.add(cty4);
		map.countries.add(cty5);
		map.players.add(p1);
		map.players.add(p2);
		
	}
	
	/**
	 * test 3 same card trade
	 */
	@Test
	public void testTrade3Cavalry() {
		p1.setArmies(5);
		p1.cards.add(Card.cavalry);
		p1.cards.add(Card.cavalry);
		p1.cards.add(Card.cavalry);
		 
		assertTrue(p1.trade(2));// 2 = cavalry
		assertEquals(0, p1.cards.size());
	}
	
	/**
	 * test when the trade id wrong
	 */
	@Test
	public void testWrongTrade() {
		p1.setArmies(5);
		p1.cards.add(Card.cavalry);
		p1.cards.add(Card.cavalry);
		
		assertFalse(p1.trade(2));
	}
	
	/**
	 * test if the trade with the 3 card get wrong
	 */
	@Test
	public void testWrongTrade2() {
		p1.setArmies(5);
		p1.cards.add(Card.cavalry);
		p1.cards.add(Card.cavalry);
		p1.cards.add(Card.cavalry);
		
		assertFalse(p1.trade(1));
	}
}

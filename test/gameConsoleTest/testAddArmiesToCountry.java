package gameConsoleTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.gameplay.Player;
import model.gameplay.strategy.Human;
import model.map.Continent;
import model.map.Country;
import model.map.Map;

/**
 * test the method for reinforcing armies from one country to another
 * 
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public class testAddArmiesToCountry 
{
	Continent con1,con2;
	Country cty1,cty2,cty3,cty4;
	Player p1,p2;
	Map map = new Map();
	
	/**
	 * initiate continents, countries, players,
	 * put countries into 2 continents
	 * 
	 */
	@Before
	public void before() 
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
	 * test the result of number of armies in country and left armies for 2 users 
	 * after reinforced their already taken countries
	 */
	@Test
	public void testAddOnTakenCountry() 
	{
		p1.ownedCountries.add(cty1);
		cty1.setPlayer(p1);
		p1.ownedCountries.add(cty3);
		cty3.setPlayer(p1);
		p1.ownedCountries.add(cty2);
		cty2.setPlayer(p1);
		p2.ownedCountries.add(cty4);
		cty4.setPlayer(p2);
		
		cty1.setArmyNumber(1);
		cty2.setArmyNumber(2);
		cty3.setArmyNumber(3);
		cty4.setArmyNumber(4);
	
		/*reinforce 2 armies to country 1
		 * reinforce 2 armies to country 4*/
		map.addArmiesFromHand(1,2);
		map.addArmiesFromHand(4,2);
		
		assertEquals(3,cty1.getArmyNumber()); //test the army number for country 1 after added 2 armies
		assertEquals(6,cty4.getArmyNumber()); //test the army nummber for country 4 after added 2 armies
		assertEquals(3,p1.getArmies()); //test the number of armies left for p1 after reinforced country 1 with 2 armies
		assertEquals(1,p2.getArmies()); //test the number of armies left for p2 after reinforced country 4 with 2 armies
	}

}

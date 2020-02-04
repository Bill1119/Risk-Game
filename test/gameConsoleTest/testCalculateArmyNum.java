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
 * This class tests the calculation of army number test should be given to player in Map class
 * 
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public class testCalculateArmyNum 
{
	Map map = new Map();
	Player p1 = new Player(1, 5, map, new Human());
	Player p2 = new Player(2, 5, map, new Human());
	Country cty1= new Country("London");
	Country cty2 = new Country("Beijing");
	Country cty3 = new Country("Paris");
	Country cty4 = new Country("Montreal");
	Country cty5 = new Country("Toronto");
	Continent con1 = new Continent("",7);
	Continent con2 = new Continent("",4);
	Continent con3 = new Continent("",6);
	
	
	/*/**
	 * initiating 3 continents, 5 countries, country 1,4,5 is in continent 1, country 2 in continent 2
	 * country 3 in continent 3
	*/
	/**
	 * The test method for adding countries and continents
	 */
	@Before 
	public void before1() 
	{
		con1.addCountry(cty1);
		con2.addCountry(cty2);
		con3.addCountry(cty3);
		con1.addCountry(cty4);
		con1.addCountry(cty5);
		map.continents.add(con1);
		map.continents.add(con2);
		map.continents.add(con3);
		map.countries.add(cty1);
		map.countries.add(cty2);
		map.countries.add(cty3);
		map.countries.add(cty4);
		map.countries.add(cty5);
		cty1.setArmyNumber(5);
		cty2.setArmyNumber(1);
		cty3.setArmyNumber(4);
		cty4.setArmyNumber(2);
		cty5.setArmyNumber(3);
	}
	
	/**
	 * Test method for adding owner of players and count the army number
	 */
	@Test
	public void test1() {
		p1.ownedCountries.add(cty1);
		p1.ownedCountries.add(cty2);
		p2.ownedCountries.add(cty3);
		p2.ownedCountries.add(cty4);
		p1.ownedCountries.add(cty5);
		cty2.setPlayer(p1);
		cty1.setPlayer(p1);
		cty3.setPlayer(p2);
		cty4.setPlayer(p2);
		cty5.setPlayer(p1);
		
		assertEquals(5,map.calculateArmyNum(p1));
		assertEquals(6,map.calculateArmyNum(p2));
	}
	
	/**
	 * Second test method for adding owner of players and count the army number
	 */
	@Test
	public void test2() {
		p1.ownedCountries.add(cty1);
		p1.ownedCountries.add(cty2);
		p1.ownedCountries.add(cty3);
		p1.ownedCountries.add(cty4);
		p1.ownedCountries.add(cty5);
		cty2.setPlayer(p1);
		cty1.setPlayer(p1);
		cty3.setPlayer(p1);
		cty4.setPlayer(p1);
		cty5.setPlayer(p1);
		assertEquals(18,map.calculateArmyNum(p1));
	}
}

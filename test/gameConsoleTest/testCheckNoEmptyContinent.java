package gameConsoleTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import model.map.Continent;
import model.map.Country;
import model.map.Map;
import model.map.MapChecker;

/**
 * test the method of checking if the map contains empty continents that have no country in it
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 *
 */
public class testCheckNoEmptyContinent 
{
	Country cty1,cty2;
	Continent con1,con2;
	Map map = new Map();
	MapChecker checker;
	
	/**
	 * test when there is 1 continent with 2 countries and 1 continent with no country
	 */
	@Before
	public void before() 
	{
		con1 = new Continent("",6);
		con2 = new Continent("",3);
		
		map.continents.add(con1);
		map.continents.add(con2);
		checker = new MapChecker(map);
	}
	
	/**
	 * test check the continent with countries or not
	 */
	@Test 
	public void testNoCountry1()
	{
		cty1 = new Country("");
		cty2 = new Country("");
		con1.addCountry(cty1);
		con1.addCountry(cty2);
		map.countries.add(cty1);
		map.countries.add(cty2);
		assertFalse(checker.checkNoEmptyContinent());
	}
	
	/**
	 * test when all continents are without any country
	 */
	@Test 
	public void testNoCountry2()
	{
		assertFalse(checker.checkNoEmptyContinent());
	}
	
	/**
	 * test when all continents have at least 1 country
	 */
	@Test 
	public void testWithCountry2()
	{
		cty1 = new Country("");
		cty2 = new Country("");
		Country cty3 = new Country("");
		con1.addCountry(cty1);
		con1.addCountry(cty2);
		con2.addCountry(cty3);
		map.countries.add(cty1);
		map.countries.add(cty2);
		map.countries.add(cty3);
		assertTrue(checker.checkNoEmptyContinent());
	}
	

}

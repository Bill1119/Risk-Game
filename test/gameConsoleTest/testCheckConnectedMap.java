package gameConsoleTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.map.Continent;
import model.map.Country;
import model.map.Map;
import model.map.MapChecker;

/**
 * check if the map is an unconnected graph, by trying to reach each possible country from a specific country in the map
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public class testCheckConnectedMap 
{
	Map map = new Map();
	Country cty1,cty2,cty3,cty4;
	Continent con1,con2;
	MapChecker checker;
	
	/**
	 * The value setting for connected graph test
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
		
		map.continents.add(con1);
		map.continents.add(con2);
		map.countries.add(cty1);
		map.countries.add(cty2);
		map.countries.add(cty3);
		map.countries.add(cty4);
		
		con1.addCountry(cty1);
		con1.addCountry(cty3);
		con2.addCountry(cty2);		
		con2.addCountry(cty4);
		checker = new MapChecker(map);
	}
	
	/**
	 * test when country 1 and 2 are connected but country 3 and 4 do not
	 * have any neighbor
	 */
	@Test
	public void testNotConnected() 
	{
		cty1.linkTo(cty2);
		cty2.linkTo(cty1);
		assertFalse(checker.checkConnectedMap());
	}
	
	/**
	 * test following country connections :  1-2  3-4 
	 * the map is not connected, but countries are all connected
	 */
	@Test
	public void testNotConnectedMapWithConnectedCountries() 
	{
		cty1.linkTo(cty2);
		cty2.linkTo(cty1);
		
		cty3.linkTo(cty4);
		cty4.linkTo(cty3);
		assertFalse(checker.checkConnectedMap());
	}
	
	/**
	 * test when all three countries are connected
	 */
	@Test  
	public void testConnected() 
	{
		cty1.linkTo(cty2);
		cty2.linkTo(cty1);
		
		cty1.linkTo(cty3);
		cty3.linkTo(cty1);
		
		cty4.linkTo(cty3);
		cty3.linkTo(cty4);
		assertTrue(checker.checkConnectedMap());
	}

}

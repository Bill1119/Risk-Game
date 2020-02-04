package mapEditorTest;

import static org.junit.Assert.*;
import org.junit.Before;

import org.junit.Test;

import model.map.Continent;
import model.map.Country;
import model.map.Map;
import model.map.MapEditor;

/**
 * to test the method for deleting continent 
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 *
 */
public class testDeleteContinent {
	
	Continent con1 = new Continent("a",4);
	Continent con2 = new Continent("b",3);
	Country cty1 = new Country("");
	Country cty2 = new Country("");
	Country cty3 = new Country("");
	MapEditor me = new MapEditor();
	
	/**
	 * create 2 continents and 3 countries and create neighbor relations
	 */
	@Before
	public void Before() {
		me.map.continents.add(con1);
		me.map.continents.add(con2);
		me.map.countries.add(cty1);
		me.map.countries.add(cty2);
		me.map.countries.add(cty3);
		con1.addCountry(cty1);
		con1.addCountry(cty2);
		con2.addCountry(cty3);
		
		cty1.linkTo(cty3);
		cty3.linkTo(cty1);
		cty3.linkTo(cty2);
		cty2.linkTo(cty3);
	}
	
	/**
	 * test when no continent is deleted, check the continent number, country number,
	 * and check the total number of the neighbor of country 3
	 */
	@Test
	public void testNoDelete() {
		assertEquals(2,me.map.continents.size());
		assertEquals(3,me.map.countries.size());
		assertEquals(2,cty3.neighbors.size());
	}
	
	/**
	 * test the delete function of Continent
	 */
	@Test
	public void testDeleteContinent() {
		Continent c = me.getContinent(1);
		assertEquals(c, con1);
		assertTrue(me.deleteContinent(1));
		assertEquals(1,me.map.continents.size());
		assertEquals(1,me.map.countries.size());
		assertEquals(0,cty3.neighbors.size());
	}

}

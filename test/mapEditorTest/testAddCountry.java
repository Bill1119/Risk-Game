package mapEditorTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.map.Continent;
import model.map.Country;
import model.map.Map;
import model.map.MapEditor;
/**
 * test the method of adding country in the map editor
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public class testAddCountry {
	MapEditor mapEditor = new MapEditor();
	Continent con1 = new Continent("",1);
	Continent con2 = new Continent("",2);
	Country ctry1 = new Country();
	Country ctry2 = new Country();
	
	
	/**
	 * before
	 */
	@Before
	public void Before() {
		mapEditor.map.continents.add(con1);
		mapEditor.map.continents.add(con2);
		ctry1.setArmyNumber(5);
		ctry2.setArmyNumber(2);
		con1.addCountry(ctry1);
		con1.addCountry(ctry2);
	}
	@Test
	
	/**
	 * add no country
	 */
	public void test() {
		
		mapEditor.addContinent(con1);
		mapEditor.addCountry(ctry1);
		mapEditor.addCountry(ctry2);
		assertEquals(mapEditor.map.countries.size(), 2);
	}

}

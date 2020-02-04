package mapEditorTest;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

import model.map.Continent;
import model.map.Country;
import model.map.Map;
import model.map.MapEditor;

/**  
 * test the method of deleteing country in map editor
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public class testDeleteCountry {
	//Map map = new Map();
	MapEditor mapEditor = new MapEditor();
	Continent con1 = new Continent("",1);
	Continent con2 = new Continent("",2);
	Country cty1 = new Country("");
	Country cty2 = new Country("");
	Country cty3 = new Country("");
	Country cty4 = new Country("");
	/**
	 * create countries and create neighbor relations
	 */
	@Before
	public void Before() {
		con1.addCountry(cty1);
		con1.addCountry(cty2);
		con2.addCountry(cty3);
		con2.addCountry(cty4);
		cty1.linkTo(cty2);
		cty2.linkTo(cty1);
		cty2.linkTo(cty3);
		cty3.linkTo(cty2);
		cty1.linkTo(cty4);
		cty4.linkTo(cty1);
		cty3.linkTo(cty4);
		cty4.linkTo(cty3);
		
		mapEditor.map.continents.add(con1);
		mapEditor.map.continents.add(con2);
		mapEditor.map.countries.add(cty1);
		mapEditor.map.countries.add(cty2);
		mapEditor.map.countries.add(cty3);
		mapEditor.map.countries.add(cty4);
		
	}
	/**
	 * test deleting one country 
	 */
	@Test
	public void testDelete() {
		mapEditor.deleteCountry(cty2);
		
		assertFalse(mapEditor.map.countries.contains(cty2));
		
		for(Country ct : mapEditor.map.countries) {
			assertFalse(ct.neighbors.contains(cty2));
		}
	}
	
	/**
	 * test not deleting any country
	 */
	public void testNotDelete() {
		boolean isDeletedFromCon = false;
		for(Continent con : mapEditor.map.continents)   //check if any continent contains the country
		{
			for(Country cty : mapEditor.map.countries)
				if(cty == cty2) {
					isDeletedFromCon = true;
					break;
				}
			if(isDeletedFromCon) break;
		}
		assertFalse(isDeletedFromCon);
		
		boolean isNeighborDeleted = false; //check if any country is still neighbor with deleted country
		for(Country ct : mapEditor.map.countries) {
			for(Country c : ct.neighbors) {
				if(c == cty2) {
					isNeighborDeleted = true;
					break;
				}
			}
			if(isNeighborDeleted) break;
		}
		assertFalse(isNeighborDeleted);
	}
}

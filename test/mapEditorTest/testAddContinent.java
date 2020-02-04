package mapEditorTest;

import static org.junit.Assert.*;
import org.junit.Before;

import org.junit.Test;

import model.map.Continent;
import model.map.Map;
import model.map.MapEditor;

/**
 * Test the mothod for adding continent
 * 
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public class testAddContinent {
	MapEditor me = new MapEditor();
	
	/**
	 * the number of continent should be 1 after adding one continent
	 */
	@Test
	public void testAddContinent() {
		me.addContinent("Alaska",2);
		assertEquals(1,me.map.continents.size());
		
	}
	
	/**
	 * the number of continent should be 0 if no continent is added
	 */
	@Test
	public void testNoContinent() {
		assertEquals(0,me.map.continents.size());
		
	}

}

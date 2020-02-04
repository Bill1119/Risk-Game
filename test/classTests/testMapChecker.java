package classTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import org.junit.Test;

import model.map.Map;
import model.map.MapChecker;

public class testMapChecker {

	/**
	 * Testing if the map is good
	 */
	@Test
	public void testCorrectMap()
	{
		File file = new File("maps" + System.getProperty("file.separator") + "world.map");
		String mapFilePath = file.getAbsolutePath();

		Map map = new Map();
		map.setPlayerNumber(6);
		try {
			map.load(mapFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		MapChecker checker = new MapChecker(map);
		assertTrue(checker.check());
		assertTrue(checker.checkConnectedContinents());
		assertTrue(checker.checkConnectedMap());
		assertTrue(checker.checkContinentsAsConnectedSubgraphs());
		assertTrue(checker.checkNoEmptyContinent());
		assertTrue(checker.checkPlayableMap());
	}
	
	/**
	 * Testing an incorrect map
	 */
	public void testIncorrectMap()
	{
		File file = new File("maps" + System.getProperty("file.separator") + "Twin Volcano.map");
		String mapFilePath = file.getAbsolutePath();

		Map map = new Map();
		map.setPlayerNumber(6);
		try {
			map.load(mapFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		MapChecker checker = new MapChecker(map);

		assertTrue(checker.checkConnectedContinents());
		assertTrue(checker.checkConnectedMap());
		assertTrue(checker.checkNoEmptyContinent());
		assertTrue(checker.checkPlayableMap());
		assertFalse(checker.checkContinentsAsConnectedSubgraphs());
		assertFalse(checker.check());
	}
}

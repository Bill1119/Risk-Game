package classTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import model.utilities.StringAnalyzer;
/**
 * 
 * test the StringAnalyzer class
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public class testStringAnalyzer {

	/**
	 * check can get the correct one and ignore incorrect one
	 */
	@Test
	public void testCheckMapType() 
	{
		File file1 = new File("world.map");
		File file2 = new File("hi.gif"); 
		
		assertTrue(StringAnalyzer.checkMapType(file1));
		assertFalse(StringAnalyzer.checkMapType(file2));
	}
}
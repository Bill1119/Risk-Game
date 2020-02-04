package gameConsoleTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.LineNumberReader;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * 
 * The test class for testing the map syntax
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public class testMapFileSyntax 
{
	String mapFilePath;
	LineNumberReader in;
	 
	@Rule
	  public final ExpectedException exception = ExpectedException.none();

	/**
	 * Testing can or cannot find a map
	 * @throws FileNotFoundException reject an error
	 */
	@Test(expected = FileNotFoundException.class)
	public void testNotFoundMap() throws FileNotFoundException 
	{
		mapFilePath = "ok.map";

		in = new LineNumberReader(new FileReader(mapFilePath));
	}
	
	/**
	 * Testing whether can the program find out not suitable(missing) syntax
	 * @throws FileNotFoundException reject an error
	 */
	@Test(expected = FileNotFoundException.class)
	public void testMissingMapSection() throws FileNotFoundException
	{
		new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "maps");
		File file = new File("world.map");
		mapFilePath = file.getAbsolutePath();

		in = new LineNumberReader(new FileReader(mapFilePath));
	}
}

package testSuites.testModules;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import classTests.testContinent;
import classTests.testCountry;
import classTests.testMap;
import classTests.testMapEditor;

/**
 * This test suite tests the map module
 * @author Yann-PC
 *
 */
@RunWith(Suite.class)
@SuiteClasses({	
				testContinent.class,
				testCountry.class,
				testMap.class,
				testMapEditor.class})
public class TestSuiteMapModule {
}
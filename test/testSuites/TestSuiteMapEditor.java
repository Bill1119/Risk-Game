package testSuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import mapEditorTest.testAddContinent;
import mapEditorTest.testAddCountry;
import mapEditorTest.testDeleteContinent;
import mapEditorTest.testDeleteCountry;

/**
 * test suite of mapeditor
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 *
 */
@RunWith(Suite.class)
@SuiteClasses({	testAddContinent.class,
				testAddCountry.class,
				testDeleteContinent.class,
				testDeleteCountry.class})
public class TestSuiteMapEditor {
}

package classTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import mapEditorTest.testAddContinent;
import mapEditorTest.testAddCountry;
import mapEditorTest.testDeleteContinent;
import mapEditorTest.testDeleteCountry;
/**
 * mapeditor package test suit
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 *
 */
@RunWith(Suite.class)
@SuiteClasses({	testAddContinent.class,
				testAddCountry.class,
				testDeleteContinent.class,
				testDeleteCountry.class})
public class testMapEditor {
}
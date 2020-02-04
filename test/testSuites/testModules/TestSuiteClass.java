package testSuites.testModules;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import classTests.testContinent;
import classTests.testCountry;
import classTests.testDices;
import classTests.testFileHandler;
import classTests.testMap;
import classTests.testMapChecker;
import classTests.testMapEditor;
import classTests.testPhase;
import classTests.testPlayer;
import classTests.testRandom;
import classTests.testStringAnalyzer;


/**
 * This test suite tests all the implementation classes
 * @author Yann-PC
 *
 */
@RunWith(Suite.class)
@SuiteClasses({	TestSuiteStrategy.class,
				testContinent.class,
				testCountry.class,
				testDices.class,
				testFileHandler.class,
				testMap.class,
				testMapChecker.class,
				testMapEditor.class,
				testPhase.class,
				testPlayer.class,
				testRandom.class,
				testStringAnalyzer.class})
public class TestSuiteClass {
}
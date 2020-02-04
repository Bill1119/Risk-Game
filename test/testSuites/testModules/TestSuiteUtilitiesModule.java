package testSuites.testModules;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import classTests.testFileHandler;
import classTests.testRandom;
import classTests.testStringAnalyzer;


/**
 * This test suite tests the utilities module
 * @author Yann-PC
 *
 */
@RunWith(Suite.class)
@SuiteClasses({	
				testFileHandler.class,
				testRandom.class,
				testStringAnalyzer.class})
public class TestSuiteUtilitiesModule {
}
package testSuites.testModules;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import classTests.testRandom;
import classTests.strategy.testAggressive;
import classTests.strategy.testBenevolent;
import classTests.strategy.testCheater;
import classTests.strategy.testHuman;

/**
 * test suite of gameconsole
 *@author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 *
 */
@RunWith(Suite.class)
@SuiteClasses({	testAggressive.class,
				testBenevolent.class,
				testCheater.class,
				testHuman.class,
				testRandom.class})
public class TestSuiteStrategy {

}

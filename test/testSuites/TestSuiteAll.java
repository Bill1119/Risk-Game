package testSuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import testSuites.testModules.TestSuiteClass;
import testSuites.testModules.TestSuiteGameplayModule;
import testSuites.testModules.TestSuiteMapModule;
import testSuites.testModules.TestSuiteStrategy;
import testSuites.testModules.TestSuiteUtilitiesModule;

/**
 * the test suite of all tests
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 *
 */
@RunWith(Suite.class)
@SuiteClasses({	TestSuiteClass.class,
				TestSuiteGameplayModule.class,
				TestSuiteMapModule.class,
				TestSuiteStrategy.class,
				TestSuiteUtilitiesModule.class,
				TestSuiteGameConsole.class,
				TestSuiteMapEditor.class})
public class TestSuiteAll {
}
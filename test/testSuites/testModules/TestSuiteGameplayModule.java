package testSuites.testModules;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import classTests.testDices;
import classTests.testPhase;
import classTests.testPlayer;

/**
 * test suite of suite class
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
@RunWith(Suite.class)
@SuiteClasses({	
				testDices.class,
				testPhase.class,
				testPlayer.class})
public class TestSuiteGameplayModule {
}
package testSuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import gameConsoleTest.testAddArmiesToCountry;
import gameConsoleTest.testAttack;
import gameConsoleTest.testCalculateArmyNum;
import gameConsoleTest.testCanSendTroopsToAlly;
import gameConsoleTest.testCheckConnectedMap;
import gameConsoleTest.testCheckNoEmptyContinent;
import gameConsoleTest.testConnectedContinents;
import gameConsoleTest.testDistributeCountries;
import gameConsoleTest.testFortification;
import gameConsoleTest.testMapFileSyntax;
import gameConsoleTest.testMapValidation;
import gameConsoleTest.testTournament;
import gameConsoleTest.testTrade;

/**
 * test suite of gameconsole
 *@author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 *
 */
@RunWith(Suite.class)
@SuiteClasses({	testAddArmiesToCountry.class,
				testAttack.class,
				testCalculateArmyNum.class,
				testCanSendTroopsToAlly.class,
				testCheckConnectedMap.class,
				testCheckNoEmptyContinent.class,
				testConnectedContinents.class,
				testDistributeCountries.class,
				testFortification.class,
				testMapFileSyntax.class,
				testMapValidation.class,
				testTournament.class,
				testTrade.class})
public class TestSuiteGameConsole 
{
	
}
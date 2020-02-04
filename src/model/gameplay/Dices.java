package model.gameplay;

import java.util.Collections;
import java.util.PriorityQueue;

import model.utilities.Rng;
/**
 * The class for the main functions of dices
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen 
 *
 */
public class Dices {
	
	/**
	 * a list of several or one dice for the attacker player
	 */
	private PriorityQueue<Integer> attackerDices = new PriorityQueue<Integer>(Collections.reverseOrder());
	
	/**
	 * a list of several or one dice for the defender player
	 */
	private PriorityQueue<Integer> defenderDices = new PriorityQueue<Integer>(Collections.reverseOrder());
	
	/**
	 * the number of the attacker's max dice number
	 */
	private int attackerMaxDices;
	
	/**
	 * the number of the defender's max dice number
	 */
	private int defenderMaxDices;
	
	/**
	 * the total number that the dice gets from the attacker
	 */
	private int attDicesNumber = 0;
	
	/**
	 * the total number that the dice gets from the defender
	 */
	private int defDicesNumber = 0;
	
	/**
	 * show the attacker loss
	 */
	private int attackerLoss = 0;
	
	/**
	 * show the defender loss
	 */
	private int defenderLoss = 0;
	
	/**
	 * Dice value for both attack and defend
	 * @param new_attackerArmiesNumber attacker's army number
	 * @param new_defenderArmiesNumber defender's army number
	 */
	public Dices(int new_attackerArmiesNumber, int new_defenderArmiesNumber) {
		attackerMaxDices = Math.min(new_attackerArmiesNumber-1, 3);
		defenderMaxDices = Math.min(new_defenderArmiesNumber, 2);
	}

	/**
	 * get the max number of attacker's dice
	 * @return return the attacker max dice number
	 */
	public int getAttackerMaxDices() {
		return attackerMaxDices;
	}
	
	/**
	 * get the max number of Defender dice
	 * @return return the Defender max dice number
	 */
	public int getDefenderMaxDices() {
		return defenderMaxDices;
	}
	
	/**
	 * The roll function to check both player use the dice and compare it
	 */
	public void roll() {
		String result = "\n";
		
		if(attDicesNumber == 0 && defDicesNumber == 0) {	/* All-out mode */
			attDicesNumber = attackerMaxDices;
			defDicesNumber = defenderMaxDices;
		}

		result += "Attacker rolled: ";
		/* Rolling attacker dices */
		for(int dice = 1; dice <= attDicesNumber; dice++) {
			int a = rollADice();
			result += a + " ";
			attackerDices.add(a);
		}
		
		result += "/ Defender rolled: ";
		/* Rolling defender dices */
		for(int dice = 1; dice <= defDicesNumber; dice++) {
			int a = rollADice();
			result += a + " ";
			defenderDices.add(a);
		}
		
		/* Comparing dice results */
		while(attackerDices.size() != 0 && defenderDices.size() != 0) {
			int attDice = attackerDices.remove();	//Max attacker dice
			int defDice = defenderDices.remove();	//Max defender dice
			
			if(defDice >= attDice)	attackerLoss++;	
			else	defenderLoss++;
		}
		result += "\nAttacker loss: " + attackerLoss + " / Defender loss: " + defenderLoss;
		System.out.println(result);
	}
	
	/**
	 * get message of attacker loss
	 * @return message of attacker loss
	 */
	public int getAttackerLoss() {
		return attackerLoss;
	}
	
	/**
	 * get message of Defender loss
	 * @return message of Defender loss
	 */
	public int getDefenderLoss() {
		return defenderLoss;
	}
	
	/**
	 * set the dice number for both player
	 * @param attDicesNumber dice number of attacker
	 * @param defDicesNumber dice number of defender
	 */
	public void setDicesNumber(int attDicesNumber, int defDicesNumber) {
		this.attDicesNumber = attDicesNumber;
		this.defDicesNumber = defDicesNumber;
	}
	
	/**
	 * roll the single dice with random value 1~6
	 * @return the 1~6 number
	 */
	private int rollADice() {
		return Rng.getRandomInt(1, 6);
	}
}

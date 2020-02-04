package view.gameplay;

import model.gameplay.Player;
import model.map.Country;
import view.common.View;

/**
 * This class displays the attack view and asks for inputs
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public class AttackView extends View {

	/**
	 * The method is to choose the attacker country using the specify numbers.
	 * @param p The current player.
	 * @return Returning the selected country number if the value of the input is incorrect.
	 */
	public int chooseAttackerCountry(Player p) 
	{
		System.out.println("\nEnter the attacker country number (0 to skip): ");
		
		int country_number;
		boolean correctValue;
		
		do 
		{
			country_number = getInteger();
			correctValue = isValueCorrect(country_number, 0, Country.Counter);
			if(correctValue && !p.owns(country_number) && country_number != 0) 
			{
				correctValue = false;
				System.out.println("Error : This country does not belong you.");
			}
		}while(!correctValue);
		
		return country_number;
	}
	
	/**
	 * The method is to choose the attacked country using the specify numbers.
	 * @param p The current player.
	 * @return Returning the selected country number if the value of the input is incorrect.
	 */
	public int chooseAttackedCountry(Player p) 
	{
		System.out.println("Enter the country number you want to attack: ");
		
		int country_number;
		
		do 
		{
			country_number = getInteger();
		}while(!isValueCorrect(country_number, 1, Country.Counter));
		
		return country_number;
	}
	
	/**
	 * Asking for attack mode : all-out or classic
	 * @return return the chosen attack mode
	 */
	public int askAttackMode() {
		System.out.println("Select the attack mode number: 1 - All-out, 2 - Classic");
		int attackMode;
		
		do 
		{
			attackMode = getInteger();
		}while(!isValueCorrect(attackMode, 1, 2));
		
		return attackMode;
	}

	/**
	 * Display an error message : wrong attacker country selection
	 */
	public void errorCannotAttack() {
		System.out.println("Error : Choose an attacker country that has at least 2 armies and an enemy neighbor.");
	}

	/**
	 * Display an error message : wrong attacked country selection
	 * @param attacker the attacker
	 */
	public void errorCannotBeAttackedBy(Country attacker) {
		System.out.println("Error : Choose an enemy neighbor of " + attacker.getName() + ".");
	}

	/**
	 * Asking if the player wants to attack again
	 * @return again Answer : 1 is yes, 2 is no
	 */
	public boolean continueAttacking() {
		System.out.println("Do you want to attack again ? 1 - yes / 2 - No");
		int again;
		
		do 
		{
			again = getInteger();
		}while(!isValueCorrect(again, 1, 2));
		
		if(again == 1)	return true;
		return false;
	}

	/**
	 * Asking number of armies to move to the conquered country
	 * @param maxArmies Remaining number of armies in the attacker country
	 * @return the number of moving armies
	 */
	public int askMovingArmies(int maxArmies) {
		System.out.println("Congrats, the country has been conquered !");
		System.out.println("Enter the number of armies you want to move:");
		int selectedArmies;
		
		do 
		{
			selectedArmies = getInteger();
		}while(!isValueCorrect(selectedArmies, 1, maxArmies-1));
		
		return selectedArmies;
	}

	/**
	 * ask the number of defender's ice number
	 * @param p the defender player
	 * @param maxDicesNumber the max number of dice
	 * @return the dice result number
	 */
	public int askDefenderDices(Player p, int maxDicesNumber) {
		System.out.println(p.getName() + " (Defender) Enter the number of dices you want to use (1 to " + maxDicesNumber + "):");
		int diceNumber;
		
		do
		{
			diceNumber = getInteger();
		}while(!isValueCorrect(diceNumber, 1, maxDicesNumber));
		
		return diceNumber;
	}
	
	/**
	 * ask the number of attacker's ice number
	 * @param p the attack player
	 * @param maxDicesNumber the max number of the dice
	 * @return the dice result number
	 */
	public int askAttackerDices(Player p, int maxDicesNumber) {
		System.out.println(p.getName() + " (Attacker) Enter the number of dices you want to use (1 to " + maxDicesNumber + "):");
		int diceNumber;

		do 
		{
			diceNumber = getInteger();
		}while(!isValueCorrect(diceNumber, 1, maxDicesNumber));
		
		return diceNumber;
	}
}

package view.gameplay;

import model.gameplay.Player;
import model.map.Country;
import view.common.View;

/**
 * 
 * The class is for the view to show the changes and the steps of the Reinforcement phase in the game.
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public class ReinforcementView extends View 
{

	/**
	 * The method is dealing with the view functions of the current player asking the selected country.
	 * @param p The current player.
	 * @return Returning the selected country number if the value is not correct.
	 */
	public int askCountry(Player p)
	{
		System.out.println(" - "  + p.getArmies() + " armies available");
		System.out.println("Enter the country number to reinforce (0 to trade cards): ");
		
		int country_number;
		boolean correctValue;
		
		do
		{
			country_number = getInteger();
			correctValue = isValueCorrect(country_number, 0, Country.Counter);
			if(p.cards.size() >= 5 && country_number != 0) {
				System.out.println("Error : You have 5 cards, you must trade.");
				correctValue = false;
			}
			else if(correctValue && !p.owns(country_number) && country_number != 0)
			{
				correctValue = false;
				System.out.println("Error : This country does not belong you.");
			}
		}while(!correctValue);
		
		return country_number;
	}
	
	/**
	 * The method is for the view functions of the current player asking army numbers with the input.
	 * @param p The current player.
	 * @return Returning the selected army number if the value is not correct.
	 */
	public int askArmiesNumber(Player p)
	{
		System.out.println("Enter the number of armies to send: ");
		
		int armies_number;
		boolean correctValue;
		
		do 
		{
			armies_number = getInteger();
			correctValue = isValueCorrect(armies_number, 1, p.getArmies());
		}while(!correctValue);
		
		return armies_number;
	}

	/**
	 * ask the cards to let player to see and choose
	 * @param p the current player
	 * @return return the chosen int
	 */
	public int askCardsToTrade(Player p) {
		// TODO Auto-generated method stub
		System.out.println("Please choose the combination to trade: ");
		System.out.println("1 - artillery");
		System.out.println("2 - cavalry");
		System.out.println("3 - infantry");
		System.out.println("4 - different");
		
		int opt;
		boolean correctValue;
		
		do 
		{
			opt = getInteger();
			correctValue = isValueCorrect(opt, 1, 4);
		}while(!correctValue);
		
		return opt;
	}

	/**
	 * show the message of error when trade
	 */
	public void errorTraiding() {
		System.out.println("Error : You can't trade those cards !");
		
	}
}

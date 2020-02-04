package view.gameplay;

import model.gameplay.Player;
import model.map.Country;
import view.common.View;

/**
 * 
 * This class is for a view implement when the game just start.
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public class StartUpView extends View 
{
	
	/**
	 * The method is to show the view of asking the the countries and deploy the army to the asked country.
	 * @param p The current player.
	 * @return Returning the selected country number when input the incorrect value.
	 */
	public int askCountry(Player p) 
	{
		System.out.println(" - "  + p.getArmies() + " armies available");
		System.out.println("Enter the country number you want to deploy 1 army: ");

		int country_number;
		boolean correctValue;
		
		do 
		{
			country_number = getInteger();
			correctValue = isValueCorrect(country_number, 1, Country.Counter);
			if(correctValue && !p.owns(country_number)) 
			{
				correctValue = false;
				System.out.println("Error : This country does not belong you.");
			}
		}while(!correctValue);
		
		return country_number;
	}
}

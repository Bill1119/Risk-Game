package view.gameplay;

import model.gameplay.Player;
import model.map.Country;
import view.common.View;

/**
 * This class displays the fortification view and asks for inputs.
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public class FortificationView extends View 
{

	/**
	 * To choose the origin country using a correct number.
	 * @param p The current player.
	 * @return Returning the selected country number if the value of the input is incorrect.
	 */
	public int chooseOriginCountry(Player p) 
	{
		System.out.println("Enter the origin country number (0 to skip): ");
		
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
	 * To choose the destination country using a correct number.
	 * @param p The current player.
	 * @return Returning the selected country number if the value of the input is incorrect.
	 */
	public int chooseDestinationCountry(Player p) 
	{
		System.out.println("Enter the destination country number: ");
		
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

	/**
	 * The method for showing the entered armies numbers and the changes. 
	 * It also has several conditions for checking if the value is correct or not.
	 * @param p The current player.
	 * @param armiesMaxNumber The max number of the army that the current player can send.
	 * @return Returning the total army number that the current player can be deployed.
	 */
	public int askArmiesNumber(Player p, int armiesMaxNumber) 
	{
		System.out.println("Enter the number of armies to send: ");
		
		int armiesSelected;
		boolean correctValue;
		
		do 
		{
			armiesSelected = getInteger();
			correctValue = isValueCorrect(armiesSelected, 1, armiesMaxNumber);
			if(armiesSelected > armiesMaxNumber || armiesSelected <= 0) 
			{
				System.out.println("Error : You can only send between 1 and " + armiesMaxNumber + " armies.");
			}
		}while(!correctValue);
		
		return armiesSelected;
	}

	/**
	 * The method displays an error message for the origin country that is selected.
	 * @param p The current player.
	 * @return Returning the selected country number.
	 */
	public int errorOrigin(Player p) 
	{
		System.out.println("Error : Select more than 1 army.");
		
		int country_number;
		boolean correctValue;
		
		do 
		{
			country_number = getInteger();
			correctValue = isValueCorrect(country_number, 0, Country.Counter);
			if(!p.owns(country_number)) 
			{
				correctValue = false;
				System.out.println("Error : This country does not belong you.");
			}
		}while(!correctValue);
		
		return country_number;
	}

	/**
	 * The method for sending error message while sending armies
	 */
	public void errorSendingTroops() {
		System.out.println("Error : Choose a country that has allied neighbors and more than 1 army.");
	}

	/**
	 * The method to check if two country are connected
	 */
	public void errorNotConnectedCountries() {
		System.out.println("Error : Choose a country that is connected to origin country.");		
	}
}

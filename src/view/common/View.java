package view.common;

import java.util.Scanner;

/**
 * The class is to show the inputed integers for the user and check the input integer is correct to the program or not.
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public abstract class View 
{
	/**
	 * scan the input that the user just input
	 */
	private static Scanner input;
	
	/**
	 * The method to get the input integer.
	 * It also checks the input integer is correct or not. It is not correct, it will print a notice message to the user.
	 * @return Returning the next step texts if there are texts.
	 */
	protected int getInteger() 
	{
		 input = new Scanner(System.in);
		 
		 do
		 {
			 if(input.hasNextInt())
			 {
				 return input.nextInt();
			 }
			 else
			 {
				 input.nextLine();
				 System.out.println("Error : Please enter an integer number.");
			 }
		 }while(true);
	}
	
	/**
	 * The method to get the input string
	 * @return Returning input string if there are none or only space in the input
	 */
	protected String getString() 
	{
		String inputString = "";
		do {
			input = new Scanner(System.in);
			inputString = input.nextLine();
		}while(inputString == null || inputString.equals(""));
		return inputString;
	}
	
	/**
	 * The method is to check whether the input value is in the correct range.
	 * @param value The value of the input
	 * @param minimum The minimum acceptable input value 
	 * @param maximum The maximum acceptable input value
	 * @return Returning false if the value id not in the range, otherwise returning true
	 */
	protected boolean isValueCorrect(int value, int minimum, int maximum)
	{
		if(value < minimum || value > maximum)
		{
			System.out.println("Error : Number out of range");
			return false;
		}
		
		return true;
	}

}

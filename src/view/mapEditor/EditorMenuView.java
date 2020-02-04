package view.mapEditor;

import view.common.View;

/**
 * 
 * The class is for the main view of the map editor
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public class EditorMenuView extends View 
{
	
	/**
	 * The menu for create a map or editing the map
	 * @return returning the option a gain if input the incorrect value
	 */
	public int print() 
	{
		System.out.println("\n\n\n\n\n*****************************************");
		System.out.println("               Map Editor\n");
		
		System.out.println("Select an option :");
		System.out.println("1 - Create a new map");
		System.out.println("2 - Edit an existing map");
		
		
		int option;
		boolean correctValue;
		
		do 
		{
			option = getInteger();
			correctValue = isValueCorrect(option, 1, 2);
		}while(!correctValue);
		
		return option;
	}

	/**
	 * The method for asking selected map names
	 * @return Returning mao name once the function finished
	 */
	public String askMapName() 
	{
		System.out.println("Enter the name of the map (without extension): ");
		String mapName = getString();
		System.out.println();
		return mapName;
	}

	/**
	 * The method for showing menu of choices of the function for the map file
	 * @return Returning option that is entered if the value is incorrect
	 */
	public int menu() 
	{
		System.out.println("\n*** Map Editor ***");
		System.out.println("Select an option: ");
		System.out.println("1 - Add country");
		System.out.println("2 - Add continent");
		System.out.println("3 - Delete country");
		System.out.println("4 - Delete continent");
		System.out.println("\n0 - Save map & exit");
		System.out.println("5 - Exit");
		System.out.println("******************");
		
		int option;
		boolean correctValue;
		
		do 
		{
			option = getInteger();
			correctValue = isValueCorrect(option, 0, 5);
		}while(!correctValue);
		
		return option;
	}
}

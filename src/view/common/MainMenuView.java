package view.common;

/**
 * 
 * The class MainMenuView is the view class that is showing the choice of both play game and map editing features to the user.
 * It is the first view that the user will see.
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public class MainMenuView extends View 
{
		
	/**
	 * The method is to print messages for some introductions and 2 choices to the user.
	 * Once the user made the choice, it will open the chosen option.
	 * @return Returning the choice function to run the chosen option. 
	 * If input other input except 1 and 2, it will re ask the user with choosing 1 or 2. 
	 */
	public int print() 
	{	
		System.out.println("*****    Welcome in the Risk Game of team 14    *****");
		System.out.println();
		System.out.println("Choose an option by entering the number :");
		System.out.println("1 - Play a single game");
		System.out.println("2 - Play a tournament");
		System.out.println("3 - Map Editor");
		
		do
		{
			int choice = getInteger();
			
			if(isValueCorrect(choice, 1, 3))
			{
				return choice;
			}
		}while(true);
	}
}

package controller;

import java.io.IOException;

import view.common.MainMenuView;

/**
 * This class is the point of entry of the application. It controls the execution
 * flow of the main menu and let the user choose between playing or editing maps.
 * It is the first menu that is displayed to the user when he starts the program.
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public class MainController 
{
	
	/**
	 * The method is to start the application by creating a main menu.
	 * @param args introductions in main menu with string type
	 */
	public static void main(String[] args) 
	{
		try {
			mainMenu();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This function creates the main menu view that is displayed to the user. It also 
	 * controls the execution flow of the main menu and dispatch the work to other
	 * controllers, depending on the user's choice.
	 * @throws IOException the exception 
	 */
	public static void mainMenu() throws IOException 
	{
		MainMenuView mainMenu = new MainMenuView();
		int choice = mainMenu.print();
		
		switch(choice)
		{
		case 1:
			new GameController();
			break;
		case 2:
			TournamentController tournament = new TournamentController();
			tournament.setUp();
			tournament.execute();
			break;
		case 3:
			new MapEditorController();
			break;
		}
	}

}
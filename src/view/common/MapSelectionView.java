package view.common;

import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;
import java.util.HashMap;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

import model.utilities.StringAnalyzer;

/**
 * This class is the view of map selection function for user to see. 
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public class MapSelectionView extends View 
{
	/**
	 * the map name
	 */
	private String mapName;
	
	/**
	 * The method is to let the user to select 2 ~ x players for the game.
	 * @param maxPlayers the max players
	 * @return Returning the player number that was inputed if the value not right.
	 */
	public int print(int maxPlayers) 
	{
		System.out.println("Choose the number of players (2-" + maxPlayers + "): ");
		int player_num;
		boolean correctValue;
		
		do 
		{
			player_num = getInteger();
			correctValue = isValueCorrect(player_num, 2, maxPlayers);
		}
		while(!correctValue);
		
		return player_num;
	}

	/**
	 * The method is for view of showing the map selection function and also showing the information of the selected .map file.
	 * @return Returning the selected file path string if selected a file, otherwise returning null.
	 */
	public String selectMap() 
	{
		System.out.println("Select the map file (.map)...");
		JFileChooser jf = new JFileChooser() 
		{
			
		protected JDialog createDialog(Component parent) throws HeadlessException 
		{
			JDialog jDialog = super.createDialog(parent);
			jDialog.setAlwaysOnTop(true);
			return jDialog;		
		}
		};
		
		/*To open in current directory*/
		File workingDirectory = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "maps");
		jf.setCurrentDirectory(workingDirectory);
		
		jf.showOpenDialog(null);
		File path = jf.getSelectedFile();

		if(StringAnalyzer.checkMapType(path)) 
		{
			mapName = path.getName().substring(0, path.getName().length() - 4);
			return path.getAbsolutePath();
		}
			
		else
			selectMap();
		
		return null;
	}

	/**
	 * to get the map name
	 * @return the map name
	 */
	public String getMapName() 
	{
		return mapName;
	}
	
	/**
	 * Ask player strategies
	 * @param nb the number
	 * @return the number of strategy
	 */
	public int askStrategy(int nb) 
	{
		System.out.println("Choose a strategy for player " + nb + ": ");
		System.out.println("1 - Human");
		System.out.println("2 - Aggressive computer");
		System.out.println("3 - Benevolent computer");
		System.out.println("4 - Random computer");
		System.out.println("5 - Cheater computer");
		int strat;
		boolean correctValue;
		
		do 
		{
			strat = getInteger();
			correctValue = isValueCorrect(strat, 1, 5);
		}
		while(!correctValue);
		
		return strat;
	}
	
	/**
	 * Ask player strategies for tournament
	 * @param nb the number
	 * @param possibleStrategies the strategoes
	 * @return the number of strategy
	 */
	public int askTournamentStrategy(int nb, HashMap<Integer, String> possibleStrategies) 
	{
		System.out.println("Choose a strategy for player " + nb + ": ");
		for(int i = 1; i <= 4; i++) {
			if(possibleStrategies.get(i) != null)	System.out.println(i + " - " + possibleStrategies.get(i) + " computer");
		}
		
		int strat;
		boolean correctValue;
		
		do 
		{
			strat = getInteger();
			correctValue = possibleStrategies.containsKey(strat);
			if(!correctValue)	System.out.println("Strategy already choosen.");
		}
		while(!correctValue);
		
		return strat;
	}
	
	/**
	 * Ask the number of maps
	 * @return the map number
	 */
	public int askMapNumber()
	{
		System.out.println("Choose the number of maps (1 to 5):");

		int nb;
		boolean correctValue;
		
		do 
		{
			nb = getInteger();
			correctValue = isValueCorrect(nb, 1, 5);
		}
		while(!correctValue);
		
		return nb;
	}

	/**
	 * Ask the number of games to play for tournament
	 * @return the game number
	 */
	public int askGameNumber() {
		System.out.println("Choose the number of games to play on each map (1 to 5):");

		int nb;
		boolean correctValue;
		
		do 
		{
			nb = getInteger();
			correctValue = isValueCorrect(nb, 1, 5);
		}
		while(!correctValue);
		
		return nb;
	}

	/**
	 * Ask the maximum number of turns for each game
	 * @return the game turn
	 */
	public int askGameMaxTurns() {
		System.out.println("Choose the maximum number of turns of each game (10 to 50):");

		int nb;
		boolean correctValue;
		
		do 
		{
			nb = getInteger();
			correctValue = isValueCorrect(nb, 10, 50);
		}
		while(!correctValue);
		
		return nb;
	}
}

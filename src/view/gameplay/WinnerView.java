package view.gameplay;

import java.util.ArrayList;

import model.gameplay.Player;

/**
 * This class is in charge of displaying the winner who won the game
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 *
 */
public class WinnerView {
	
	/**
	 * show the winner view of the game
	 * @param new_winner the winner player
	 */
	public WinnerView(Player new_winner) {
		print(new_winner);
	}

	/**
	 * the view to show the winners
	 * @param winners winners in different games
	 * @param new_gamesNumber the number of games played
	 * @param new_mapNumber maps that played
	 */
	public WinnerView(ArrayList<String> winners, int new_gamesNumber, int new_mapNumber) {
		System.out.println("\nTournament results :");
		int nbLine = new_gamesNumber * new_mapNumber;
		
		for(int i = 0; i < nbLine; i++) {
			int game = ((i%new_mapNumber)+1);
			int map = ((i)/new_gamesNumber+1);
			System.out.println("Game " + game + ", Map " + map + " : " + winners.get(i));
		}
	}

	/**
	 * To print the player that won the game
	 * @param p the winner
	 */
	public void print(Player p) {
		System.out.println("Congrats " + p.getName() + ", you won !");
	}
}

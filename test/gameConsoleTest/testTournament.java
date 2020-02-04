package gameConsoleTest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import controller.TournamentController;
import model.gameplay.Player;
import model.gameplay.strategy.Aggressive;
import model.gameplay.strategy.Benevolent;
import model.gameplay.strategy.Cheater;
import model.gameplay.strategy.Random;
import model.map.Map;
import view.common.MapSelectionView;

/**
 * test the tournament
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 *
 */
public class testTournament {
	
	/**
	 * The tournament controller
	 */
	private TournamentController tournament;

	/**
	 * launch a simple tournament
	 */
	@Test
	public void testTournament() {
		tournament = new TournamentController();
		
		// Translating the set up with hard-coded inputs
		tournament.setPlayerNumber(2);
		tournament.getMap().setPlayerNumber(4);
		tournament.getMap().setPlayers(4);
		
		tournament.getMap().players.get(0).setStrategy(new Aggressive());
		tournament.getMap().players.get(1).setStrategy(new Benevolent());
		tournament.getMap().players.get(2).setStrategy(new Random());
		tournament.getMap().players.get(3).setStrategy(new Cheater());
		
		tournament.setPlayers(tournament.getMap().players);
		
		int mapNumber = 2;
		
		for(int i = 1; i <= mapNumber; i++) {
			
			File file = new File("maps" + System.getProperty("file.separator") + "World.map");
			String mapFilePath = file.getAbsolutePath();
			tournament.getMaps().add(mapFilePath);
		}
		
		tournament.setGamesNumber(2);
		tournament.setMaximumTurns(10);
		
		try {
			tournament.execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertEquals(tournament.getWinners().size(), 4);
	}
}

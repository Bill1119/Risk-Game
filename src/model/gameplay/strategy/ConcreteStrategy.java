package model.gameplay.strategy;

import model.gameplay.Player;
import model.map.Country;
import model.map.Map;
import model.utilities.Rng;

/**
 * this class is for basic Strategy functions and let other classes to use it
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 *
 */
public abstract class ConcreteStrategy implements Strategy {

	/**
	 * the players of the game
	 */
	protected Player player;
	
	/**
	 * the map of the game
	 */
	protected Map map;
	
	/**
	 * setting the players
	 * @param player the current player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	/**
	 * setting maps
	 * @param map the map file
	 */
	public void setMap(Map map) {
		this.map = map;
	}
	
	/**
	 * Method to place a country during the startup phase.
	 */
	@Override
	public void placeOneArmy() {
		map.getPhase().setPhase("Start up phase", player);
		int randomIndex = Rng.getRandomInt(0, player.ownedCountries.size()-1);
		Country randomCountry = player.ownedCountries.get(randomIndex);
		
		map.getPhase().setAction(player.getName() + " added 1 army in " + randomCountry.getName() + "\n");
		map.addArmiesFromHand(randomCountry, 1);		
	}
}

package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import model.gameplay.Phase;
import model.gameplay.Player;
import model.gameplay.strategy.Aggressive;
import model.gameplay.strategy.Benevolent;
import model.gameplay.strategy.Cheater;
import model.gameplay.strategy.Random;
import model.map.Country;
import model.map.Map;
import view.common.MapSelectionView;
import view.gameplay.CardExchangeView;
import view.gameplay.MapView;
import view.gameplay.PhaseView;
import view.gameplay.WinnerView;
import view.gameplay.WorldDominationView;

/**
 * this method is for the control of the Tournament mode
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 *
 */
public class TournamentController {
	/**
	 * The current map of the game
	 */
	private Map map;
	
	/**
	 * To store the winners of each game
	 */
	private ArrayList<String> winners = new ArrayList<String>();
	
	/**
	 * Player number
	 */
	private int playerNumber;
	
	/**
	 * The players of the tournament
	 */
	private ArrayList<Player> players = new ArrayList<Player>();
	
	/**
	 * The maps selected for the tournament
	 */
	private ArrayList<String> maps = new ArrayList<String>();
	
	/**
	 * Number of games played on each map
	 */
	private int gamesNumber;
	
	/**
	 * Maximum number of turns for each game
	 */
	private int maximumTurns;
	
	/**
	 * data of phase changes, 4 kind of phases included
	 */
	private Phase phase;
	
	/**
	 * the data of map view that shows the map to the users
	 */
	private MapView mapView;
	
	/**
	 * the data that should show in the phase view to the users
	 */
	private PhaseView phaseView;
	
	/**
	 * data of the world domination view, it shows the data of players in the map who owned the most country and army
	 */
	private WorldDominationView worldDomiView;
	
	/**
	 * the data for card exchange view, it shows how many and what kind of the card of a player has
	 */
	private CardExchangeView cardExchangeView;
	
	/**
	 * This is a constructor method for GameController
	 */
	public TournamentController()
	{
		initMap();
	}
	
	/**
	 * This method deals with phase steps and checks whether the game has a winner or not
	 * @throws IOException reject an error
	 */
	public void execute() throws IOException
	{
		for(String currentMap : maps) {
			for(int game = 1; game <= gamesNumber; game++) {
				initMap();
				loadMap(currentMap);

				Player winner = playOneGame();
				if(winner == null)	winners.add("Draw");
				else	winners.add(winner.getName());
			}
		}
		
		new WinnerView(winners, gamesNumber, maps.size());
	}
	
	/**
	 * To get winners
	 * @return	list of winners
	 */
	public ArrayList<String> getWinners() {
		return winners;
	}

	/**
	 * To set winners
	 * @param winners set winners
	 */
	public void setWinners(ArrayList<String> winners) {
		this.winners = winners;
	}

	/**
	 * To get number of players
	 * @return number of players
	 */
	public int getPlayerNumber() {
		return playerNumber;
	}

	/**
	 * set number of players
	 * @param playerNumber number of players
	 */
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	/**
	 * get players
	 * @return the players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * set number of players
	 * @param players number of players
	 */
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	/**
	 * get maps
	 * @return maps
	 */
	public ArrayList<String> getMaps() {
		return maps;
	}
	
	/**
	 * set maps path
	 * @param maps maps path
	 */
	public void setMaps(ArrayList<String> maps) {
		this.maps = maps;
	}

	/**
	 * get games number
	 * @return number of games
	 */
	public int getGamesNumber() {
		return gamesNumber;
	}

	/**
	 * set number of games
	 * @param gamesNumber number of games
	 */
	public void setGamesNumber(int gamesNumber) {
		this.gamesNumber = gamesNumber;
	}

	/**
	 * get maximum turns
	 * @return maximum turns
	 */
	public int getMaximumTurns() {
		return maximumTurns;
	}

	/**
	 * set maximum turns
	 * @param maximumTurns maximum turns
	 */
	public void setMaximumTurns(int maximumTurns) {
		this.maximumTurns = maximumTurns;
	}

	/**
	 * get phase
	 * @return phase
	 */
	public Phase getPhase() {
		return phase;
	}

	/**
	 * set phase
	 * @param phase the phase
	 */
	public void setPhase(Phase phase) {
		this.phase = phase;
	}
	
	/**
	 * get map
	 * @return map
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * set map
	 * @param map the map
	 */
	public void setMap(Map map) {
		this.map = map;
	}

	/**
	 * To play one game
	 * @return the winner
	 */
	private Player playOneGame() {
		Country.Counter = map.countries.size();
		Player winner = null;
		int turn = 0;
		
		startUpPhase();
		
		do
		{
			turn++;
			System.out.println("\n - TURN " + turn + " -");

			for(Player p : map.players) 
			{
				if(p.ownedCountries.size() > 0)
				{
					reinforcementPhase(p);
					attackPhase(p);
					
					if(map.isOwned()) {
						winner = p;
						break;
					}
					
					fortificationPhase(p);
				}
			}
		}while(!map.isOwned() && turn < maximumTurns); /* When map is owned, end of the game */
		
		return winner;
	}
	
	/**
	 * Set up the parameters of the tournament : number of players, types of players, maps selection.
	 * @throws IOException reject an error
	 */
	public void setUp() throws IOException  
	{
		MapSelectionView mapSelectionView = new MapSelectionView();
		playerNumber = mapSelectionView.print(4);	//4 max players
		map.setPlayers(playerNumber);
		HashMap<Integer, String> possibleStrategies = new HashMap<Integer, String>();
		possibleStrategies.put(1, "Aggressive");
		possibleStrategies.put(2, "Benevolent");
		possibleStrategies.put(3, "Random");
		possibleStrategies.put(4, "Cheater");
		
		for(Player p : map.players) {
			int strat = mapSelectionView.askTournamentStrategy(p.getNumber(), possibleStrategies);
			
			switch(strat) {
				case 1:
					p.setStrategy(new Aggressive());
					break;
				case 2:
					p.setStrategy(new Benevolent());
					break;
				case 3:
					p.setStrategy(new Random());
					break;
				case 4:
					p.setStrategy(new Cheater());
					break;
			}
			possibleStrategies.remove(strat);
		}
		players = map.players;	//Memorizing players of the tournament
		
		int mapNumber = mapSelectionView.askMapNumber();
		
		for(int i = 1; i <= mapNumber; i++) {
			String mapFilePath = mapSelectionView.selectMap();
			Map map = new Map();
			map.setPlayers(playerNumber);
			map.load(mapFilePath);	//Check if map is correct
			maps.add(mapFilePath);
		}
		
		gamesNumber = mapSelectionView.askGameNumber();
		maximumTurns = mapSelectionView.askGameMaxTurns();
	}

	/**
	 * This method deals with deploying players and armies on the map when the game just start.
	 * The deployment of each player with countries is randomly deployed.
	 * The deployment will end until every country have an owner.
	 */
	private void startUpPhase()
	{
		map.distributeCountries(); /* Randomly split the countries between the players */
		ArrayList<Player> remainingPlayers = new ArrayList<Player>(map.players);
		
		do 
		{
			Iterator<Player> i = remainingPlayers.iterator();
			
			while (i.hasNext()) 
			{
				Player p = i.next();
				if(p.getArmies() == 0) 
				{
					i.remove();
				} 
				else 
				{
					p.placeOneArmy();
				}
			}
		}while(remainingPlayers.size() != 0);
	}
	
	/**
	 * This method collects all the inputs in the reinforcement phase.
	 * @param p The current player that is in the reinforcementPhase
	 */
	private void reinforcementPhase(Player p) 
	{
		int armyNum = map.calculateArmyNum(p);
		p.setArmies(armyNum);
		p.reinforce();
	}
	
	/**
	 * This method collects all the inputs in the attack phase by calling views. The player can choose origin and destination, and armies number.
	 * Origin and destination countries must be connected.
	 * @param p The current player that is in the fortificationPhase
	 */
	private void attackPhase(Player p) 
	{
		p.attack();
	}

	/**
	 * This method deals with the logic of the fortification phase. The player can choose origin and destination, and armies number.
	 * Origin and destination countries must be connected.
	 * @param p The current player that is in the fortificationPhase
	 */
	private void fortificationPhase(Player p) 
	{
		p.fortify();
	}
	
	/**
	 * To create / reset the map 
	 */
	public void initMap() {
		map = new Map();
		map.clear();
		phase = new Phase();
		map.setPhase(phase);
		mapView = new MapView();
		phaseView = new PhaseView();
		worldDomiView = new WorldDominationView();
		cardExchangeView = new CardExchangeView();
		phase.addObserver(phaseView);
		phase.addObserver(cardExchangeView);
		map.addObserver(worldDomiView);
		map.addObserver(mapView);
	}
	
	/**
	 * To load a clean map
	 * @param path the path
	 */
	private void loadMap(String path) {
		try {
			for(Player p : players)	p.clear();
			map.players = players;
			map.setPlayerNumber(playerNumber);
			for(Player p : map.players) {
				p.setMap(map);
				p.setArmies(map.getInitialArmiesNumber());
			}
			map.setPlayerNumber(map.players.size());
			map.load(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

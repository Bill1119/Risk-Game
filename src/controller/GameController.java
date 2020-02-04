package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import view.common.MapSelectionView;
import view.gameplay.CardExchangeView;
import view.gameplay.MapView;
import view.gameplay.PhaseView;
import view.gameplay.WinnerView;
import view.gameplay.WorldDominationView;
import model.gameplay.Phase;
import model.gameplay.Player;
import model.gameplay.strategy.Aggressive;
import model.gameplay.strategy.Benevolent;
import model.gameplay.strategy.Cheater;
import model.gameplay.strategy.Human;
import model.gameplay.strategy.Random;
import model.map.Map;
	
/**
 * This class is a controller for game play part
 * It also includes controls for different phases in the game
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public class GameController 
{
	
	/**
	 * The total data of a map, the game map of the game
	 */
	private Map map;
	
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
	 * the player who wins the game
	 */
	private Player winner;
	
	/**
	 * the data for card exchange view, it shows how many and what kind of the card of a player has
	 */
	private CardExchangeView cardExchangeView;

	/**
	 * This is a constructor method for GameController
	 */
	public GameController()
	{
		try
		{
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
			
			execute();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * This method deals with phase steps and checks whether the game has a winner or not
	 * @throws IOException reject an error
	 */
	private void execute() throws IOException
	{
		setUp();
		startUpPhase();
		
		do 
		{
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
		}while(!map.isOwned()); /* When map is owned, end of the game */
		
		new WinnerView(winner);
	}
	
	/**
	 * Set up the parameters of the game : number of players, types of players, map selection.
	 * @throws IOException reject an error
	 */
	public void setUp() throws IOException  
	{
		MapSelectionView mapSelectionView = new MapSelectionView();
		int playerNumber = mapSelectionView.print(6);
		map.setPlayers(playerNumber);
		
		for(Player p : map.players) {
			int strat = mapSelectionView.askStrategy(p.getNumber());
			
			switch(strat) {
				case 1:
					p.setStrategy(new Human());
					break;
				case 2:
					p.setStrategy(new Aggressive());
					break;
				case 3:
					p.setStrategy(new Benevolent());
					break;
				case 4:
					p.setStrategy(new Random());
					break;
				case 5:
					p.setStrategy(new Cheater());
					break;
			}
		}
		
		String mapFilePath = mapSelectionView.selectMap();		
		map.load(mapFilePath);
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
}
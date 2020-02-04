package model.gameplay;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

import model.gameplay.strategy.ConcreteStrategy;
import model.gameplay.strategy.Strategy;
import model.map.Country;
import model.map.Map;
import model.utilities.Rng;

/**
 * This class is dealing with each player's data changes like owned countries, armies, and cards
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public class Player extends Observable
{
	
	/**
	 * the number of players
	 */
	private int number;
	
	/**
	 * The number of armies that a player holds
	 */
	private int armies;
	
	/**
	 * what kind of strategies of the AI player has
	 */
	private Strategy strategy;
	
	/**
	 * the map information
	 */
	private Map map;
	
	/**
	 * whether a player got the card or not
	 */
	public boolean gotCard = false;
	
	/**
	 * list of countries of a player has
	 */
	public ArrayList<Country> ownedCountries = new ArrayList<Country>();
	
	/**
	 * list of cards that a player has
	 */
	public ArrayList<Card> cards = new ArrayList<Card>();
	
	/**
	 * The Constructor method of player
	 * @param new_number new number of countries
	 * @param new_armies new number of armies
	 * @param map the map
	 * @param new_strategy the strategy
	 */
	public Player(int new_number, int new_armies, Map map, Strategy new_strategy) 
	{
		setNumber(new_number);
		setArmies(new_armies);
		setStrategy(new_strategy);
        setMap(map);
	}
	
	/**
	 * set the map
	 * @param map the game map
	 */
	public void setMap(Map map) {
		this.map = map;
		((ConcreteStrategy) strategy).setMap(map);
	}

	/**
	 * get player id
	 * @return return player id
	 */
	public int getNumber() 
	{
		return number;
	}
	
	/**
	 * set player id
	 * @param number the ID of the player
	 */
	public void setNumber(int number) 
	{
		this.number = number;
	}
	
	/**
	 * get player name
	 * @return return player name
	 */
	public String getName() 
	{
		return (strategy.getClass().getSimpleName() + " " + getNumber());
	}
	
	/**
     * Plugs in a specific strategy to be used
     * @param strategy the strategy of the player
     */
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
		((ConcreteStrategy) strategy).setPlayer(this);
		this.setMap(map);
    }

	/**
	 * get method for the armies
	 * @return current armies number of the player
	 */
	public int getArmies() 
	{
		return armies;
	}
	
	/**
	 * set number of the armies
	 * @param armies The number of armies of the current players
	 */
	public void setArmies(int armies) 
	{
		this.armies = armies;
	}
	
	/**
	 * The method is for checking correctness that a player with how many countries he or she owned
	 * @param countryNumber The total owned country number of a player
	 * @return return Return true means correct, and false means incorrect with the owned country number of a player
	 */
	public boolean owns(int countryNumber) 
	{
		for(Country c : ownedCountries) 
		{
			if(c.getNumber() == countryNumber) 
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * To execute a reinforcement move. It moves the armies and update the action.
	 * @param country The country to reinforce.
	 * @param armies The number of armies to add.
	 */
	public void reinforcementMove(Country country, int armies) {
		map.addArmiesFromHand(country, armies);
		map.getPhase().setAction(this.getName() + " reinforced " + armies + " army in " + country.getName() + "\n");		
	}
	
	/**
	 * To execute a conquest move after an attack. It moves the armies and update the action.
	 * @param attacker The attacker country.
	 * @param defender The defender country.
	 * @param armies The number of armies to move.
	 * @return boolean saying if the attacker conquered the defender territory.
	 */
	public boolean conquestMove(Country attacker, Country defender, int armies) {
		if(defender.getArmyNumber() == 0) {
			map.addArmiesToCountry(attacker, -armies);
			map.addArmiesToCountry(defender, armies);
			map.getPhase().setAction(map.getPhase().getAction() + attacker.getName() + "(" + this.getName() + ") conquered " + defender.getName() + " and moved " + armies + " armies\n");
			return true;
		} else {
			map.getPhase().setAction(map.getPhase().getAction() + attacker.getName() + "(" + this.getName() + ") failed to conquer " + defender.getName() + " (" + defender.getPlayer().getName() + ")\n");
			return false;
		}
	}
	
	/**
	 * To execute a fortification move. It moves the armies and update the action.
	 * @param origin Origin country.
	 * @param destination Destination country.
	 * @param armies The number of armies to move.
	 */
	public void fortificationMove(Country origin, Country destination, int armies) {
		map.addArmiesToCountry(origin, -armies);
		map.addArmiesToCountry(destination, armies);
		
		map.getPhase().setAction(this.getName() + " fortified " + armies + " army from " +
				origin.getName()+" to "+ destination.getName() + "\n");		
	}
	
	/**
	 * This method will process the all-out attack mode.
	 * @param attackerCtry Country attacking
	 * @param defenderCtry Country defending
	 * @return boolean Tell if the defender country has been conquered
	 */
	public boolean allOutAttack(Country attackerCtry, Country defenderCtry) 
	{
		map.getPhase().setAction(attackerCtry.getName() + "("+ this.getName()+ ") attacked " + defenderCtry.getName() + "(" + defenderCtry.getPlayer().getName() + ")\n");

		do {
			Dices dices = new Dices(attackerCtry.getArmyNumber(), defenderCtry.getArmyNumber());
			battle(map, dices, attackerCtry, defenderCtry);
			
			if(conquer(defenderCtry))	return true;		// Trying to conquer the country

		}while(attackerCtry.getArmyNumber() > 1);	// Continue until no attack is possible
		
		return false;
	}

	/**
	 * This method will process the classic attack mode. The players provide their dices.
	 * @param attackerCtry Country attacking
	 * @param defenderCtry Country defending
	 * @param dices Dices selected by the players
	 * @return boolean Tell if the defender country has been conquered
	 */
	public boolean classicAttack(Country attackerCtry, Country defenderCtry, Dices dices) {
		map.getPhase().setAction(attackerCtry.getName() + "("+ this.getName()+ ") attacked " + defenderCtry.getName() + "(" + defenderCtry.getPlayer().getName() + ")\n");
		battle(map, dices, attackerCtry, defenderCtry);
		return conquer(defenderCtry);		// Trying to conquer the country	
	}
	
	/**
	 * This class will compute a battle. It rolls the dice, computing the result, and resolve the loss of both sides.
	 * @param map the map
	 * @param dices the dices
	 * @param attackerCtry attacker country
	 * @param defenderCtry defender country
	 */
	private void battle(Map map, Dices dices, Country attackerCtry, Country defenderCtry) {
		dices.roll();
		/* Resolving loss in both armies */
		map.addArmiesToCountry(attackerCtry, -dices.getAttackerLoss());
		map.addArmiesToCountry(defenderCtry, -dices.getDefenderLoss());
	}
	
	/**
	 * To conquer a country
	 * @param c the country to conquer
	 * @return boolean True if the country has been conquered, false otherwise
	 */
	private boolean conquer(Country c)
	{
		if(c.getArmyNumber() == 0) {
			Player defender = c.getPlayer();
			defender.ownedCountries.remove(c);
			this.ownedCountries.add(c);
			c.setPlayer(this);
			return true;
		}
		return false;
	}

	/**
	 * get the percentage of each player owned countries in a map
	 * @param p each player
	 * @param map map file
	 * @return each player's percentage
	 */
	public float getPercentage(Player p, Map map)
    {
		float count = p.ownedCountries.size();
		float total = map.countries.size();
		float percentage = count/total;
        BigDecimal bd = new BigDecimal(percentage);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        
        return (float)bd.doubleValue();
    }
	
	/**
	 * get the total army of each player
	 * @return total army number of each player
	 */
	public int getTotalArmy() {
		int totalArmy = 0;
		
		for(Country c : ownedCountries) {
			totalArmy += c.getArmyNumber();
		}
		
		return totalArmy;
	}

	/**
	 * get the card
	 * @return the card that get
	 */
	public String getCards() {
		String result = "";
		for(Card c : cards) {
			result += c.name() + " ";
		}
		
		return result;
	}

	/**
	 * get one type of the card
	 */
	public void getOneCard() {		
		switch(Rng.getRandomInt(1, 3)) {
		case 1:
			cards.add(Card.artillery);
			break;
		case 2:
			cards.add(Card.cavalry);
			break;
		case 3:
			cards.add(Card.infantry);
			break;			
		}
	}

	/**
	 * trade the card
	 * @param combination 3 same type to trade
	 * @return true if can trade, otherwise false
	 */
	public boolean trade(int combination) {
		switch(combination)
		{
		case 1: 
			if(checkSameCards(Card.artillery)) {
				removeSameCards(Card.artillery);
				return true;
			}		
			break;
		case 2:
			if(checkSameCards(Card.cavalry)) {
				removeSameCards(Card.cavalry);
				return true;
			}
			break;
		case 3:
			if(checkSameCards(Card.infantry)) {
				removeSameCards(Card.infantry);
				return true;
			}
			break;
		case 4:
			if(checkDifferentCards()) {
				removeDifferentCards();
				return true;
			}
			break;
		}
		
		return false;
	}

	/**
	 * check weather cards are different
	 * @return return the types string of cards names
	 */
	private boolean checkDifferentCards() {
		boolean artillery = false, infantry = false, cavalry = false;
		
		for(Card card : cards) {
			if(card.name().equals("artillery"))	artillery = true;
			if(card.name().equals("infantry"))	infantry = true;
			if(card.name().equals("cavalry"))	cavalry = true;
		}
		
		return (artillery & infantry & cavalry);
	}

	/**
	 * remove 3 different types of cards to add extra army
	 */
	private void removeDifferentCards() {
		Iterator<Card> i = cards.iterator();
		boolean artillery = false, infantry = false, cavalry = false;
		
		while(i.hasNext() && (artillery  == false || infantry == false || cavalry  == false)) {
			Card c = i.next();
			if(c.name().equals("artillery") && artillery == false) {
				i.remove();
				artillery = true;
			}
			else if(c.name().equals("infantry") && infantry == false) {
				i.remove();
				infantry = true;
			}
			else if(c.name().equals("cavalry") && cavalry == false) {
				i.remove();
				cavalry = true;
			}
		}
	}

	/**
	 * check do the player get the same 3 cards
	 * @param c the card that hold
	 * @return true if have three same cards, otherwise false
	 */
	public boolean checkSameCards(Card c) {
		int sameCard = 0;
		
		for(Card card : cards) {
			if(card.name().equals(c.name()))	sameCard++;
		}
		
		if(sameCard >= 3) return true;
		
		return false;
	}
	
	/**
	 * remove same 3 cards
	 * @param t same 3 cards
	 */
	public void removeSameCards(Card t) {
		Iterator<Card> i = cards.iterator();
		int removed = 0;
		
		while(i.hasNext() && removed < 3) {
			Card c = i.next();
			if(c.name().equals(t.name()))	{
				i.remove();
				removed++;
			}
		}
	}
	
	/**
	 * Check if a player can continue to attack during the attack phase. At least one
	 * of its own countries must be able to launch an attack.
	 * @return boolean if we can continue  attacking
	 */
	public boolean canContinueAttacking() {
		for(Country c : this.ownedCountries) {
			if(c.canAttack())	return true;
		}
		
		return false;
	}
	
	/**
	 * To get the country with the biggest army number of the player.
	 * @return the strongest country.
	 */
	public Country getStrongestCountry() {
		if(ownedCountries == null || ownedCountries.size() == 0)	
			return null;
		
		Country strongest = ownedCountries.get(0);
		
		for(Country c : ownedCountries)
			if(c.getArmyNumber() > strongest.getArmyNumber()) strongest = c;
		
		return strongest;
	}

	/**
	 * to get the country with the lest army number of the player
	 * @return the weakest country
	 */
	public Country getWeakestCountry() {
		if(ownedCountries == null || ownedCountries.size() == 0)
			return null;
		Country weakest = ownedCountries.get(0);
		
		for(Country c : ownedCountries)
		{
			if(c.getArmyNumber() < weakest.getArmyNumber()) weakest = c;
		}
		
		return weakest;
	}

	/**
	 * the reinforce strategy
	 */
	public void reinforce() {
		strategy.reinforce();
	}
	
	/**
	 * the attack strategy
	 */
	public void attack() {
		strategy.attack();
	}
	
	/**
	 * the fortify strategy
	 */
	public void fortify() {
		strategy.fortify();
	}

	/**
	 * strategy to place one army
	 */
	public void placeOneArmy() {
		strategy.placeOneArmy();
	}
	
	/**
	 * To clear players
	 */
	public void clear() {
		ownedCountries.clear();
		armies = map.getInitialArmiesNumber();
		gotCard = false;
		cards.clear();
	}
}

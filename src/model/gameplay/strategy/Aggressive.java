package model.gameplay.strategy;

import java.util.ArrayList;

import model.map.Country;

/**
 * This class is for the aggressive AI player 
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 *
 */
public class Aggressive extends ConcreteStrategy implements Strategy {

	/**
	 * This method will try to reinforce the strongest country in priority.
	 * If the strongest country has only 1 army, we pick a country that has an
	 * enemy neighbor : this strategy focuses on attacking !
	 */
	@Override
	public void reinforce() {
		map.getPhase().setPhase("Reinforcement phase", player);

		Country strongestCountry = player.getStrongestCountry();
		if(strongestCountry.getArmyNumber() == 1)	strongestCountry = getACountryThatHasEnnemyNeighbor();
		
		int maxArmies = player.getArmies();
		player.reinforcementMove(strongestCountry, maxArmies);
	}

	/**
	 * This method will let the aggressive AI to choose the country with the strongest army to attack enemy
	 */
	@Override
	public void attack() {
		Country attacker = player.getStrongestCountry();

		do {
			map.getPhase().setPhase("Attack phase", player);

			if(attacker != null) {
				Country defender = attacker.getEnnemyNeighbor();
				if(defender == null)	return;
				player.allOutAttack(attacker, defender);
				boolean conquered = player.conquestMove(attacker, defender, attacker.getArmyNumber() - 1);
				if(conquered)	attacker = defender;
			}
		}while(attacker.canAttack());
		
	}

	/**
	 * This method tries to send troops from the second strongest country to the 
	 * strongest one. In case there the strongest country has only 1 army, we choose
	 * a country with an enemy neighbor.
	 */
	@Override
	public void fortify() {
		map.getPhase().setPhase("Fortification phase", player);

		Country destination = player.getStrongestCountry();
		if(destination.getArmyNumber() == 1 || player.ownedCountries.size() <= 1)	return;
		
		Country origin = getSecondStrongestCountry(destination);
		if(origin == null) return;
		
		if(destination.getArmyNumber() == 1)	destination = getACountryThatHasEnnemyNeighbor();
		player.fortificationMove(origin, destination, (origin.getArmyNumber()-1));
	}

	/**
	 * the method to get the enemy neighbors
	 * @return null if the method can't get any enemy neighbors
	 */
	private Country getACountryThatHasEnnemyNeighbor() {
		for(Country c : player.ownedCountries) {
			if(c.getEnnemyNeighbor() != null)	return c;
		}
		return null;
	}
	
	/**
	 * This method will find the second strongest country connected to the strongest one.
	 * @param first	the strongest country
	 * @return The 2nd strongest country connected to the strongest one.
	 */
	private Country getSecondStrongestCountry(Country first) {
		ArrayList<Country> closed = new ArrayList<Country>();
		ArrayList<Country> open = new ArrayList<Country>();
		Country current = first;
		int max = 1;
		Country second = null;
		
		open.add(current);
		
		while(open.size() > 0) {
			current = open.remove(0);
			
			for(Country neighbor : current.neighbors) 
			{ /* Check all the neighbors */
				if(player.ownedCountries.contains(neighbor) && !closed.contains(neighbor)) { /* Add not inspected allied country in open list */
					open.add(neighbor);
				}
			}
			
			closed.add(current);
			
			if(current != first && current.getArmyNumber() > max) {
				second = current;
				max = second.getArmyNumber();
			}
		}
		
		return second;		
	}
}

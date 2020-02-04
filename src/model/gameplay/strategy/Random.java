package model.gameplay.strategy;

import java.util.ArrayList;

import model.gameplay.Dices;
import model.map.Country;
import model.map.Map;
import model.utilities.Rng;

/**
 * the class dealing with Random AI
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 *
 */
public class Random extends ConcreteStrategy implements Strategy {

	/**
	 * the reinforce method of Random AI
	 */
	@Override
	public void reinforce() {
		map.getPhase().setPhase("Reinforcement phase", player);
		
		int randomArmy = Rng.getRandomInt(1, player.getArmies());
		int randomCtyIndex = Rng.getRandomInt(0, player.ownedCountries.size()-1);
		player.reinforcementMove(player.ownedCountries.get(randomCtyIndex), randomArmy);
	}

	/**
	 * the attack method of Random AI
	 */
	@Override
	public void attack() {
		/* Choosing random attacker */
		ArrayList<Country> potentialAttackers = new ArrayList<Country>();	//Looking for potential attackers
		for(Country c : player.ownedCountries) {
			if(c.canAttack())	potentialAttackers.add(c);
		}
		if(potentialAttackers.size() == 0) {
			map.getPhase().setPhase("attack phase", player);
			return;
		}
		
		int attackerIndex = Rng.getRandomInt(0, potentialAttackers.size()-1);
		Country attacker = potentialAttackers.get(attackerIndex);
		
		do {
			map.getPhase().setPhase("attack phase", player);

			int attack = Rng.getRandomInt(0, 3);	//Chance to attack 75%
			if(attack == 0 || attacker.canAttack() == false)	 return;
			
			Country defender = attacker.getEnnemyNeighbor();
			
			player.classicAttack(attacker, defender, new Dices(attacker.getArmyNumber(), defender.getArmyNumber()));
			boolean conquered = player.conquestMove(attacker, defender, attacker.getArmyNumber() - 1);
			if(conquered)	attacker = defender;
		}while(true);		
	}

	/**
	 * the fortify method of Random AI
	 */
	@Override
	public void fortify() {
		/* Choosing random country that can be fortified */
		ArrayList<Country> potentialDestinations = new ArrayList<Country>();	//Looking for potential attackers
		for(Country destination : player.ownedCountries) {
			for(Country origin : destination.neighbors) {
				if(origin.isConnectedTo(destination) && origin.getArmyNumber() >= 2 && !potentialDestinations.contains(destination)) {
					potentialDestinations.add(destination);
				}
			}			
		}
		if(potentialDestinations.size() == 0)	return;
		
		int randomDestIndex = Rng.getRandomInt(0, potentialDestinations.size()-1);
		Country randomDest = potentialDestinations.get(randomDestIndex);
		
		/* finding the origin */
		for(Country origin : player.ownedCountries) {
			if(origin.isConnectedTo(randomDest) && origin.getArmyNumber() >= 2) {
				int armies = Rng.getRandomInt(1, origin.getArmyNumber()-1);
				player.fortificationMove(origin, randomDest, armies);
				return;
			}
		}
	}
}

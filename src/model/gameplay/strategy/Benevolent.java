package model.gameplay.strategy;

import model.map.Country;

/**
 * the class dealing with Benevolent AI functions
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 *
 */
public class Benevolent extends ConcreteStrategy implements Strategy {

	/**
	 * The reinforce Strategy of Benevolent AI
	 */
	@Override
	public void reinforce() {
		do
		{
			map.getPhase().setPhase("Reinforcement phase", player);
			Country weakestCountry = player.getWeakestCountry();
			player.reinforcementMove(weakestCountry, 1);
		}while(player.getArmies() > 0);
	}

	/**
	 * The attack Strategy of Benevolent AI
	 */
	@Override
	public void attack() 
	{
		map.getPhase().setPhase("Attack phase", player);
	}

	/**
	 * The fortify Strategy of Benevolent AI
	 */
	@Override
	public void fortify() {
		map.getPhase().setPhase("Fortification phase", player);
		
		Country weakestCountry = player.getWeakestCountry();
		Country origin = null;
		
		for (Country c : player.ownedCountries) {
			//Check if a connected country can reinforce the weakest
			if (c.isConnectedTo(weakestCountry) && c.getArmyNumber() > 1) {
				origin = c;
				break;
			}
		}

		if (origin == null)
			return;

		int armies = origin.getArmyNumber() / 2;
		player.fortificationMove(origin, weakestCountry, armies);
	}
}

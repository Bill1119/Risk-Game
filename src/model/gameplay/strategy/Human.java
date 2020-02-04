package model.gameplay.strategy;

import model.gameplay.Dices;
import model.map.Country;
import model.map.Map;
import view.gameplay.AttackView;
import view.gameplay.FortificationView;
import view.gameplay.ReinforcementView;
import view.gameplay.StartUpView;

/**
 * A concrete Strategy that implements human strategy operation
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public class Human extends ConcreteStrategy implements Strategy {
	
	/**
	 * the reinforcementView data
	 */
	private ReinforcementView reinforcementView;
	
	/**
	 * attackView data
	 */
	private AttackView attackView;
	
	/**
	 * fortificationView data
	 */
	private FortificationView fortificationView;

	/**
	 * Reinforce phase according to the strategy of the human player
	 */
	@Override
	public void reinforce() {
		reinforcementView = new ReinforcementView();
		
		do 
		{
			map.getPhase().setPhase("Reinforcement phase", player);
			int countryNumber = reinforcementView.askCountry(player);
			if(countryNumber == 0) {	//Trading menu
				int combination = reinforcementView.askCardsToTrade(player);
				if(player.trade(combination)) {
					int cardBonus = Map.getCardBonus();
					player.setArmies(player.getArmies() + cardBonus);
					map.getPhase().setAction(player.getName() + " traded cards and got " + cardBonus + " new armies\n");
				} else {
					reinforcementView.errorTraiding();
				}
			} else {	//Reinforcement menu
				int selectedArmies = reinforcementView.askArmiesNumber(player);				
				map.addArmiesFromHand(countryNumber, selectedArmies);
				map.getPhase().setAction(player.getName() + " reinforced " + selectedArmies + " army in " + map.countries.get(countryNumber-1).getName() + "\n");
			}
		}while(player.getArmies() > 0);
	}

	/**
	 * the attack method for Human 
	 */
	@Override
	public void attack() {
		attackView = new AttackView();
		player.gotCard = false;
		
		do {
			map.getPhase().setPhase("Attack phase", player);

			Country attackerCtry = getAttackerCountry();
			if(attackerCtry == null)	return;
			
			Country defenderCtry = getDefenderCountry(attackerCtry);

			/* Getting attack mode : all-out or classic */
			boolean conquered = false;
			
			if(attackView.askAttackMode() == 1) { //All-out
				conquered = player.allOutAttack(attackerCtry, defenderCtry);
			}
			else {			//Classic
				String name = defenderCtry.getPlayer().getName();
				String defenderName = name.substring(0, name.length() - 3);
				Dices dices = new Dices(attackerCtry.getArmyNumber(), defenderCtry.getArmyNumber());
				int attackerDices = attackView.askAttackerDices(player, dices.getAttackerMaxDices());
				int defenderDices = 1;
				
				if(!defenderName.equals("Human")) {
					defenderDices = dices.getDefenderMaxDices();
				} else {
					defenderDices = attackView.askDefenderDices(defenderCtry.getPlayer(), dices.getDefenderMaxDices());
				}
				
				dices.setDicesNumber(attackerDices, defenderDices);

				conquered = player.classicAttack(attackerCtry, defenderCtry, dices);
			}
			
			/* Attacker conquered the country */
			if(conquered) {
				int movingArmies = attackView.askMovingArmies(attackerCtry.getArmyNumber());
				map.addArmiesToCountry(attackerCtry, -movingArmies);
				map.addArmiesToCountry(defenderCtry, movingArmies);
				
				if(!player.gotCard) {
					player.getOneCard();
					player.gotCard = true;
				}
				map.getPhase().setAction(map.getPhase().getAction() + attackerCtry.getName() + "(" + player.getName() + ") conquered " + defenderCtry.getName() + " and moved " + movingArmies + " armies\n");
			} else {
				map.getPhase().setAction(map.getPhase().getAction() + attackerCtry.getName() + "(" + player.getName() + ") failed to conquer " + defenderCtry.getName() + " (" + defenderCtry.getPlayer().getName() + ")\n");
			}
		}while(player.canContinueAttacking() && attackView.continueAttacking());
	}
	
	/**
	 * the fortify method for human
	 */
	@Override
	public void fortify() {
		fortificationView = new FortificationView();
		map.getPhase().setPhase("Fortification phase", player);
		
		/* Getting origin country */
		boolean canSendTroops;
		int	originCountryId;
		Country origin;
		do 
		{
			originCountryId = fortificationView.chooseOriginCountry(player); /* Select a valid country owned by the current player */
			if(originCountryId == 0) return;	/* 0 to skip */
			
			origin = map.countries.get(originCountryId-1);
			canSendTroops = origin.canSendTroopsToAlly(); /* Check if the selected country can send troops */
			if(!canSendTroops)	fortificationView.errorSendingTroops();
		}while(!canSendTroops);
		
		/* Getting destination country */
		boolean connected;
		int destinationCountryId;
		Country destination;
		do 
		{
			destinationCountryId = fortificationView.chooseDestinationCountry(player);
			
			destination = map.countries.get(destinationCountryId-1);
			 connected = origin.isConnectedTo(destination);
			if(!connected)	fortificationView.errorNotConnectedCountries();
		}while(!connected);
			
		Country c = map.countries.get(originCountryId-1);
		
		/* Getting number of armies to send */
		int selectedArmies = fortificationView.askArmiesNumber(player, c.getArmyNumber()-1);	/* User has to let at least 1 army on the origin country */
		player.fortificationMove(origin, destination, selectedArmies);
	}

	/**
	 * Ask the user to choose a country to attack
	 * @return the selected attacker country
	 */
	private Country getAttackerCountry() {
		boolean canAttack;
		int	attackerCountryId;
		Country attackerCtry;
		do 
		{
			attackerCountryId = attackView.chooseAttackerCountry(player);
			if(attackerCountryId == 0) return null;	/* 0 to skip */
			
			attackerCtry = map.countries.get(attackerCountryId-1);
			canAttack = attackerCtry.canAttack(); /* Check if the selected country can attack another country */
			if(!canAttack)	attackView.errorCannotAttack();
		}while(!canAttack);
		
		return attackerCtry;
	}
	
	/**
	 * Ask the user to choose a country to defend
	 * @param attackerCtry A n
	 * @return the selected defender country
	 */
	private Country getDefenderCountry(Country attackerCtry) {
		boolean canBeAttacked;
		int defenderCountryId;
		Country defenderCtry;
		do 
		{
			defenderCountryId = attackView.chooseAttackedCountry(player);
			defenderCtry = map.countries.get(defenderCountryId-1);
			canBeAttacked = defenderCtry.canBeAttackedBy(attackerCtry);
			if(!canBeAttacked)	attackView.errorCannotBeAttackedBy(attackerCtry);
		}while(!canBeAttacked);
		
		return defenderCtry;
	}
	
	/**
	 * Method to place a country during the startup phase.
	 */
	@Override
	public void placeOneArmy() {
		map.getPhase().setPhase("Start up phase", player);
		StartUpView startUpView = new StartUpView();
		int ctryId = startUpView.askCountry(player);
		map.getPhase().setAction(player.getName() + " added 1 army in " + map.countries.get(ctryId-1).getName() + "\n");
		map.addArmiesFromHand(ctryId, 1);		
	}
}

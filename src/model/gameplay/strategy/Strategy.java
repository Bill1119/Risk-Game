package model.gameplay.strategy;

/**
 * The classes that implement a concrete strategy should implement this. The
 * Player class uses this to use a concrete strategy.
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public interface Strategy {

	/**
	 * Method to attack.
	 */
	void attack();

	/**
	 * Method to reinforce.
	 */
	void reinforce();

	/**
	 * Method to fortify.
	 */
	void fortify();

	/**
	 * Method to place a country during the startup phase.
	 */
	void placeOneArmy();
}

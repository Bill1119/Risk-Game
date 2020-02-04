package classTests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.gameplay.Phase;
import model.gameplay.Player;
import model.gameplay.strategy.Human;
import model.map.Map;

import org.junit.Before;

/**
 * this method to test the functionality of Phase class
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public class testPhase {

	/**
	 * the phase data
	 */
	Phase phase;
	
	/**
	 * the player
	 */
	Player p;
	
	/**
	 * map file
	 */
	Map map;
	
	/**
	 * Before test method for testing, with new data
	 */
	@Before
	public void Before() {
		phase = new Phase();
		p = new Player(1,1, map, new Human());
		map = new Map();
		map.players.add(p);
		phase.setPhase("start up", p);
		phase.setAction("ok");
	}
	
	/**
	 * test whether can get the right value
	 */
	@Test
	public void test() {
		assertSame("start up",phase.getPhase());
		assertSame("ok",phase.getAction());
		assertSame(p,phase.getPlayer());
	}
	

}

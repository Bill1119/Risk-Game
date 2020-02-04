package view.gameplay;

import java.util.Observable;
import java.util.Observer;

import model.gameplay.Phase;
import view.common.View;
/**
 * this class is the observer for every action during each phase
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 *
 */
public class PhaseView extends View implements Observer {

	/**
	 * the observer method of phases
	 */
	@Override
	public void update(Observable o, Object org) {
		print((Phase) o);
	}

	/**
	 * print out the action and the phase
	 * @param o the current phase
	 */
	private void print(Phase o) {
		System.out.print("\n"+o.getAction()+"\n");
		System.out.print("** "+ o.getPlayer().getName() + " " + o.getPhase() +" ** ");
	}
}

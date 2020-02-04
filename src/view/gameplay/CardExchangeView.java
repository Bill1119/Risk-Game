package view.gameplay;

import java.util.Observable;
import java.util.Observer;

import model.gameplay.Phase;
import view.common.View;
/**
 * the view for card
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 *
 */
public class CardExchangeView extends View implements Observer 
{	
	/**
	 * The override method for update observer of the printed map.
	 */
	@Override
	public void update(Observable o, Object arg) 
	{
		print((Phase) o);
	}
	
	/**
	 * print which phase of the card state
	 * @param phase one type of the phase in string
	 */
	public void print(Phase phase) {
		if(phase.getPhase().equals("Reinforcement phase")) {
			System.out.println("\nYour cards : " + phase.getPlayer().getCards());
		}
	}
}

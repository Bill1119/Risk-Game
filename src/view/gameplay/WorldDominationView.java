package view.gameplay;

import java.util.Observable;
import java.util.Observer;

import model.gameplay.Player;
import model.map.Continent;
import model.map.Map;
import view.common.View;
/**
 * The view showing whole total data for player to see
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen 
 *
 */
public class WorldDominationView extends View implements Observer {

	/**
	 * observer for the view
	 */
	@Override
	public void update(Observable o, Object org) {
		print((Map) o);
	}
	
	/**
	 * print out the message of the World Domination View
	 * @param o the map file
	 */
	private void print(Map o) {
		System.out.print("\n*****************************************\n\n");
        System.out.println(" World Domination View ");
       
        for(Player p : o.players)
        {
        	int c = (int)(100*p.getPercentage(p, o));
            System.out.println(p.getName() + " conquered Percentage " + c+"%");
        }
       
        for(Player p : o.players)
        {  
            System.out.println(p.getName() + " total army : " + (p.getTotalArmy()+ p.getArmies()));
        }
       
        for(Continent c : o.continents)
        {
        	if(c.getOwner() != null)	System.out.println("Continent " + c.getName() + " owned by " + c.getOwner().getName());
        }
       
        System.out.print("\n*****************************************\n");
	}
}

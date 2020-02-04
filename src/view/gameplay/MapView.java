package view.gameplay;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.map.Continent;
import model.map.Country;
import model.map.Map;
import view.common.View;

/**
 * 
 * The class is dealing with showing the data and data changes of the map and update with the observer pattern 
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public class MapView extends View implements Observer 
{	
	/**
	 * the frame of the map
	 */
	private static Frame aFrame = new Frame("Map");
	
	/**
	 * the Jpanel container for the map view
	 */
	private JPanel container;
	
	/**
	 * Map view constructor
	 */
	public MapView() {
		aFrame.setSize(900, 1000);
	    aFrame.setVisible(true);
	    container = new JPanel();
	    JScrollPane scrPane = new JScrollPane(container);
	    container.setLayout(new FlowLayout(FlowLayout.LEFT));

	    aFrame.add(scrPane);
	}
	
	/**
	 * The override method for update observer of the printed map.
	 */
	@Override
	public void update(Observable o, Object arg) 
	{
		print((Map) o);
	}

	/**
	 * The method is to print the information in a type of order for the map and showing to the user
	 * @param o The map file that had been selected and will show to the user
	 */
	private void print(Map o) 
	{	    
		String text = "<html>";
		text += "          World map - " + o.getPlayerNumber() + " players<br/>";
		text += "*****************************************<br/>";

		for(Continent c : o.continents) 
		{
			text += "<b>Continent " + c.getName() + "</b>";
			if(c.getOwner() != null) 
			{
				text += " owned by " + c.getOwner().getName() + " (+" + c.getExtraArmies() + ")<br/>";
			} else 
			{
				text += "<br/>";
			}

			
			for(Country ctry : c.countries) 
			{
				text += ctry.getPlayer().getName() + " - " + ctry.getNumber() + "  " + ctry.getName() + " (" + ctry.getArmyNumber() + ") --> ";
				
				for(int i = 0; i < ctry.neighbors.size(); i++) {
					text += ctry.neighbors.get(i).getName();
					if(i != (ctry.neighbors.size() - 1)) 
					{
						text += ", ";
					}
				}
				text += "<br/>";
			}
			text += "<br/>";			
		}
		text += "</html>";
		JLabel label = new JLabel(text);
	    Font f = new Font("Cosmic Sans MS", Font.PLAIN, 20);
	    label.setFont(f);
		label.setFont(label.getFont().deriveFont(60));
		container.removeAll();
		container.add(label);
        aFrame.revalidate();
    }
 
	/**
	 * function for show the neighbor of a country
	 * @param ctry the current country
	 */
    public void showNeighbors(Country ctry){
        for(int i = 0; i < ctry.neighbors.size(); i++) {
            System.out.print(ctry.neighbors.get(i).getName());
            if(i != (ctry.neighbors.size() - 1))
            {
                System.out.print(", ");
            }
        }
    }
   
    /**
     * check the owner of the country in the current continent
     * @param c the current continent
     */
    public void checkOwner(Continent c) {
        if(c.getOwner() != null)
        {
            System.out.println("Continent " + c.getName() + " owned by " + c.getOwner().getName());
        } else
        {
            System.out.print("");
        }
    }

}

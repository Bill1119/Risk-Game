package model.map;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

/**
 * This class is for functions such as add, delete continents and countries in the map file.
 * 
 *@author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public class MapEditor extends Observable
{
	/**
	 * the map data that for the map editor to edit 
	 */
	public Map map;

	/**
	 * Create a new and empty map. If need to load existing map, please see
	 * function load().
	 */
	public MapEditor() 
	{
		map = new Map();
		map.clear();
		map.setPlayerNumber(0);
	}

	/**
	 * If the data has been changed, call this function to notify observers.
	 */
	public void changeState() 
	{
		setChanged();
		notifyObservers();
	}

	/**
	 * Add a qualified continent to the local variables.
	 * 
	 * @param cont
	 *            The input continent.
	 */
	public void addContinent(Continent cont) 
	{
		if (findContinent(cont.getName()) == null) 
		{
			map.continents.add(cont);
			changeState();
		}
	}

	/**
	 * Delete the input continent, and remove the countries inside and their links
	 * 
	 * @param contNumber
	 *            The target continent needed to remove.
	 * @return Returning true if the input continent is exist, otherwise return false           
	 */
	public boolean deleteContinent(int contNumber) 
	{
		Continent c = getContinent(contNumber);
		/* if the continent exists */
		if(c != null) 
		{
			int size = c.countries.size();
			
			for(int i = 0; i < size; i++) {
				deleteCountry(c.countries.remove(0));
			}
			
			map.continents.remove(c);
			changeState();
			return true;
		}
		return false;
	}
	
	/**
	 * Add a qualified Country to the local variables, then build the
	 * neighbor links.
	 * 
	 * @param ctry
	 *            The input Country.
	 * 
	 */
	public void addCountry(Country ctry) 
	{
		/* If country name not already exists */
		if (findCountry(ctry.getName()) == null) 
		{
			this.map.countries.add(ctry);
			/* Adding neighbors */
			ArrayList<String> neighborNames = ctry.neighborsNames;
			if (neighborNames.size() > 0) 
			{
				for (String name : neighborNames) 
				{
					Country neighbor = findCountry(name);
					if (neighbor != null) 
					{
						neighbor.neighborsNames.add(ctry.getName());
						connect(ctry, neighbor);
					}
				}
			}
			changeState();
		} else 
		{
			Country.Counter--;
		}
	}

	/**
	 * Delete the target Country, also remove all links in its neighbors.
	 * 
	 * @param ctryNumber
	 *            The target Country needed to be removed with int type.
	 * @return Returning true if the input suit with an existing country, otherwise return false
	 */
	public boolean deleteCountry(int ctryNumber) 
	{
		Country c = getCountry(ctryNumber);
		/* if the country exists */
		if(c != null) 
		{
			deleteCountry(c);
			return true;
		}
		return false;
	}
	
	/**
	 * The method for deleting country in the map file
	 * @param c the selected country
	 */
	public void deleteCountry(Country c) 
	{
		if (map.countries.contains(c)) 
		{
			disconnect(c);
			c.getContinent().countries.remove(c);
			map.countries.remove(c);
		}
		changeState();
	}
	
	/**
	 * Find and return a continent giving a name. In case is does not exist, return null
	 * 
	 * @param name The input continent name
	 * @return Returning continent type if the continent is found, otherwise return null
	 */
	private Continent findContinent(String name) 
	{
		for(Continent c : map.continents) 
		{
			if(c.getName().equals(name)) 
			{
				return c;
			}
		}
		return null;
	}
	
	/**
	 * Find and return a country giving a name. In case is does not exist, return null
	 * @param name The input country name
	 * @return Returning country type if the country is found, otherwise return null
	 */
	private Country findCountry(String name) 
	{
		for(Country c : map.countries) 
		{
			if(c.getName().equals(name)) 
			{
				return c;
			}
		}
		return null;
	}
	
	/**
	 * Create a connection between 2 countries. It generate a link in both side
	 * @param ctry The current country
	 * @param neighbor The current country's neighbor
	 */
	private void connect(Country ctry, Country neighbor) 
	{
		ctry.linkTo(neighbor);
		neighbor.linkTo(ctry);
	}
	
	/**
	 * To remove all the connections made between a specific country and the other countries
	 * @param c country that need to remove
	 */
	private void disconnect(Country c) 
	{
		for(Country n : c.neighbors) 
		{
			n.neighbors.remove(c);
			n.neighborsNames.remove(c.getName());
		}
		c.neighbors.clear();
		c.neighborsNames.clear();
	}

	/**
	 * Setting the map name
	 * @param mapName The current map name
	 */
	public void setMapName(String mapName) 
	{
		map.setName(mapName);
	}

	/**
	 * The loading function for map files
	 * @param mapFilePath The selected map file path with string type
	 */
	public void load(String mapFilePath) 
	{
		try 
		{
			map.load(mapFilePath);
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Translate the number displayed and entered by the user to the selected country
	 * @param inputNumber number entered by the user
	 * @return Returning country type if the input suit to the countries in the map file, otherwise return null.
	 */
	public Country getCountry(int inputNumber) 
	{
		int counter = 0;
		for(Continent c : map.continents) 
		{
			counter++;
			for(Country ctry : c.countries) 
			{
				counter++;
				if(counter == inputNumber) 
				{
					return ctry;
				}
			}
		}
		return null;
	}
	
	/**
	 * Translate the number displayed and entered by the user to the selected continent
	 * @param inputNumber number entered by the user
	 * @return Returning Continent type if the continent found in the map, otherwise return null.
	 */
	public Continent getContinent(int inputNumber) 
	{
		int counter = 0;
		for(Continent c : map.continents) 
		{
			counter++;
			if(counter == inputNumber) 
			{
				return c;
			}
			counter += c.countries.size();
		}
		return null;
	}

	/**
	 * The method for adding countries and the neighbor
	 * @param ctryName The target country with string type
	 * @param contNb The number of continents
	 * @param neighborNumbers Neighbor numbers of a country
	 * @return Returning false if countries have the same name, continent number is wrong, or neighbor is wrong. Otherwise return true.
	 */
	public boolean addCountry(String ctryName, int contNb, ArrayList<Integer> neighborNumbers) 
	{
		/* Check if there is an other country with the same name */
		if(findCountry(ctryName) != null)
		{
			return false;
		}
		/* Check if the continent number is correct */
		Continent c = getContinent(contNb);
		if(c == null)
		{
			return false;
		}
		/* Check for incorrect neighbors */
		ArrayList<Country> neighbors = new ArrayList<Country>();
		for(int i : neighborNumbers) 
		{
			Country n = getCountry(i);
			if(n == null) 
			{
				return false;
			} else {
				neighbors.add(n);
			}
		}
		
		/* add country & create neighbors */
		Country ctry = new Country(ctryName);
		map.countries.add(ctry);
		c.countries.add(ctry);
		ctry.setContinent(c);
		for(Country neighbor : neighbors) 
		{
			connect(ctry, neighbor);
		}
		
		changeState();
		return true;
	}

	/**
	 * Calculate the number of countries and continent that are displayed, so the user can't choose a greater number.
	 * @return Returning the max possible input.
	 */
	public int getMaxInputNumber() 
	{
		return map.continents.size() + map.countries.size();
	}

	/**
	 * to add a new continent
	 * @param continentName the name of the new continent
	 * @param bonus bonus armies of the continent
	 * @return Returning false if find the added continent name is already exist, otherwise return true
	 */

	public boolean addContinent(String continentName, int bonus) 
	{
		/* if continent name not already existing */
		if(findContinent(continentName) != null) 
		{
			return false;
		}
		Continent c = new Continent(continentName, bonus);
		map.continents.add(c);
		changeState();
		
		return true;

	}
		
	/**
	 * save and generate the .map file
	 */
	public void save() 
	{
		MapChecker checker = new MapChecker(map);
		if(checker.check()) 
		{
			String content = extractInfo(map);

			generate(map.getName(),content);
			System.out.println("Map successfully saved");
		}
	}

	/**
	 * to extract map info into the string
	 * @param map the map that needs to be saved
	 * @return the string form of the map
	 */
	private String extractInfo(Map map) 
	{
		if(map == null || map.continents.size() ==0) return null;
		String content = "[Map]" + System.lineSeparator(); 
		content += "image=" + map.getName() + ".bmp" + System.lineSeparator();
		content += "warn=yes" + System.lineSeparator();
		content += "author=Team 14" + System.lineSeparator();
		content += "scroll=horizontal" + System.lineSeparator();
		content += "wrap=no" + System.lineSeparator() + System.lineSeparator();
		
		content += "[Continents]" + System.lineSeparator();
		for(Continent con : map.continents) 
		{
			content += con.getName() + "=" + con.getExtraArmies() + System.lineSeparator();
		}
		content += System.lineSeparator() + "[Territories]";

		boolean firstLine = true;
		for(Continent con : map.continents) 
		{
			if(!firstLine)	content += System.lineSeparator();
			for(Country c : con.countries) 
			{
				content += System.lineSeparator() + c.getName()+",0,0,"+ c.getContinent().getName();
				for(Country cou1 : c.neighbors) {
					content += ","+cou1.getName();
				}
			}
			firstLine = false;
		}
		return content;
	}

	/**
	 * The function to generate the new file name and new properties that content in the map file
	 * @param fileName The selected file name
	 * @param content The string that content in the file
	 */
	public static void generate(String fileName, String content) 
	{ 
		try 
		{
			FileWriter fw = new FileWriter(new File("maps", fileName + ".map"));

			BufferedWriter out = new BufferedWriter(fw); 
			out.write(content); 
			out.close(); 
		}
		catch(IOException e) 
		{ 
			System.out.println(e); 
		 } 
	}
}

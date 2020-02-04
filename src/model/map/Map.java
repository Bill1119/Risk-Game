package model.map;

import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Observable;
import java.util.StringTokenizer;

import model.gameplay.Phase;
import model.gameplay.Player;
import model.gameplay.strategy.Human;
import model.utilities.Rng;
import model.utilities.StringAnalyzer;

/**
 * This is the class that includes most logic functions for map
 * It includes the functions like :
 * 1. The functions of data changes for Continents, countries, and players of the whole map. 
 * 2. The finding, and loading functions of the map file
 * 3. The checking functions for checking the string properties of the map file is suitable to the program or not
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public class Map extends Observable 
{
	/**
	 * the list of continents that the map has
	 */
	public ArrayList<Continent> continents = new ArrayList<Continent>();
	
	/**
	 * the list of countries that the map has
	 */
	public ArrayList<Country> countries = new ArrayList<Country>();
	
	/**
	 * the list of players that the map has
	 */
	public ArrayList<Player> players = new ArrayList<Player>();
	
	/**
	 * the name of the map
	 */
	private String name;
	
	/**
	 * the file path of the map
	 */
	private String mapFilePath;
	
	/**
	 * the image file path of the map
	 */
	private String imageFilePath;
	
	/**
	 * the wrap data of the map
	 */
	private boolean wrap;
	
	/**
	 * the scroll data in the map
	 */
	private String scroll;
	
	/**
	 * the author of the map
	 */
	private String author;
	
	/**
	 * the data of warn in the map
	 */
	private boolean warn;
	
	/**
	 * the number of the player in the map
	 */
	private int playerNumber;
	
	/**
	 * the phase state of the map
	 */
	private Phase phase;
	
	/**
	 * the card bonus when conquer a country
	 */
	private static int cardBonus = 0;
	
	/**
	 * Map constructor 
	 */
	public Map() {
		Country.Counter = 0;
		Map.cardBonus = 0;
	}
	
	/**
	 * The method for loading the map file and checking the syntax of the map file is suit to the program or not
	 * @param mapFilePath The file path of the map file in string type
	 * @throws IOException reject an error
	 */
	public void load(String mapFilePath) throws IOException 
	{	
		this.mapFilePath = mapFilePath;
		LineNumberReader in = new LineNumberReader(new FileReader(mapFilePath));
		loadMapSection(in);
		loadContinents(in);
		loadCountries(in);
		
		MapChecker checker = new MapChecker(this);
		if(!checker.check())	throw new IOException("This map is incorrect");
	}

	/**
	 * 	 * To load and check if the map file has the good syntax.
	 * @param in The string type in the map file that need to be read
	 * @throws IOException reject an error
	 */
	private void loadMapSection(LineNumberReader in) throws IOException 
	{
		reachSection(in, "Map");
		while(true) 
		{
			String line = in.readLine();
			if (line == null) 
			{
				throw new IOException("[Continents] Section expected; found EOF");
			}
			if (!line.trim().equals("")) 
			{
				if (line.startsWith("[")) 
				{
					if (line.equalsIgnoreCase("[Continents]")) 
					{
						return;
					}
					throw new IOException("[Continents] Section expected; found " + line);
				}
				String[] pair = line.split("=", 2);
				if ((pair != null) && (pair.length == 2)) 
				{
					String prop = pair[0].toLowerCase();
					String val = pair[1];
					if ("image".equals(prop)) 
					{
						if ((val != null) && (val.length() > 0)) 
						{
							this.imageFilePath = (new File(this.mapFilePath).getParent() + "\\" + val);
						}
					} 
					else if ("wrap".equals(prop)) 
					{
						this.wrap = val.equalsIgnoreCase("yes");
					} 
					else if ("scroll".equals(prop)) 
					{
						this.scroll = val;
					} 
					else if ("author".equals(prop)) 
					{
						this.author = val;
					} 
					else if ("warn".equals(prop)) 
					{
						this.warn = val.equalsIgnoreCase("yes");
					}
				}
			}
		}
	}

	/**
	 * To load and check if the continent's section of the map file is correct.
	 * @param in The continent string type in the map file that need to be read
	 * @throws IOException reject an error
	 */
	private void loadContinents(LineNumberReader in) throws IOException 
	{
		while(true) 
		{
			String line = in.readLine();
			if (line == null) 
			{
				throw new IOException("[Territories] Section expected; found EOF");
			}
			if (!line.trim().equals("")) 
			{
				if (line.startsWith("[")) 
				{
					if (line.equalsIgnoreCase("[Territories]")) 
					{
						return;
					}
					throw new IOException("[Territories] Section expected; found " + line);
				}
				int eqloc = line.indexOf("=");
				if (eqloc == -1) 
				{
					throw new IOException("Invalid continent line: " + line);
				}
				String cname = line.substring(0, eqloc).trim();
				int cbonus = StringAnalyzer.parseInt(line.substring(eqloc + 1).trim(), -1);
				if ((cname.length() < 1) || (cbonus == -1)) 
				{
					throw new IOException("Invalid continent line: " + line);
				}
				this.continents.add(new Continent(cname.toLowerCase(), cbonus));
			}
		}
	}
	
	/**
	 * To load and check if the territorie's section of the map file is correct.
	 * @param in The country string type in the map file that need to be read
	 * @throws IOException reject an error
	 */
	private void loadCountries(LineNumberReader in) throws IOException 
	{
		while(true) 
		{
			String line = in.readLine();
			if (line == null) 
			{
				break;
			}
			if (!line.trim().equals("")) 
			{
				parseCountryLine(line.toLowerCase());
			}
		}
		
		for (Country c : countries) 
		{
			for(String n : c.neighborsNames) 
			{
				c.linkTo(findCountry(n));
			}
		}
	}
	
	/**
	 * The method is to find the specific country and its information
	 * @param name The specific country name in string type
	 * @return Returning to the current country in country type if the condition is correct, otherwise print incorrect map file with the current country name
	 * @throws IOException reject an error
	 */
	private Country findCountry(String name) throws IOException 
	{
		for (Country c : countries) 
		{
			if(c.getName().equals(name)) 
			{
				return c;
			}
		}
		throw new IOException("Incorrect map file (Country " + name + " not found)");
	}
	
	/**
	 * The method is to parse the neighbors of the current country with separated by ",".
	 * It also will check some conditions like the current country is exist or not and the correctness of the coordinates (x,y)
	 * @param line This is the string line of neighbors of the current country
	 * @throws IOException reject an error
	 */
	private void parseCountryLine(String line) throws IOException 
	{
		try 
		{
			StringTokenizer st = new StringTokenizer(line, ",");
			Country ctry = new Country();
			ctry.setName(st.nextToken().trim().toLowerCase());
			ctry.setCenter(Integer.parseInt(st.nextToken().trim()), Integer.parseInt(st.nextToken().trim()));

			if (st.hasMoreTokens()) 
			{
				String name = st.nextToken().trim().toLowerCase();
				ctry.setContinent(findContinent(name));
				if ((ctry.getName() == null) || (ctry.getName().length() < 0)) 
				{
					throw new Exception("country name not found");
				}
				if ((ctry.getXLocation() == -1) || (ctry.getYLocation() == -1)) 
				{
					throw new Exception("invalid coordinates");
				}
				if (ctry.getContinent() != null && !name.equals("")) 
				{
					ctry.getContinent().countries.add(ctry);
					countries.add(ctry);
				}
				while (st.hasMoreTokens()) 
				{
					ctry.neighborsNames.add(st.nextToken().trim());
				}
			}
			
		} catch (Exception e) 
		{
			throw new IOException(" :Invalid country line (" + e + "): " + line);
		}
	}
	
	/**
	 * The method is to find the specific continent and its information
	 * @param name The specific country name in string type
	 * @return Returning to the current continent in continent type if the condition is correct, otherwise returning null
	 */
	private Continent findContinent(String name) 
	{
		for (Continent cont : this.continents) 
		{
			if (name.equalsIgnoreCase(cont.getName())) 
			{
				return cont;
			}
		}
		return null;
	}

	/**
	 * The method is to make a pointer for the LineNumberReader to point the target section in the map file
	 * @param in The string line number that need to be read 
	 * @param section The part of the string in the map file that is selected as the target
	 * @return Returning the LineNumberReader type "in" while the read file string is not equal to the head
	 * @throws IOException reject an error
	 */
	private int reachSection(LineNumberReader in, String section) throws IOException 
	{
		String head = "[" + section + "]";
		String line;
		do 
		{
			line = in.readLine();
			if (line == null) 
			{
				throw new EOFException("EOF encountered before section " + head + " found");
			}
		} while (!line.equalsIgnoreCase(head));
		return in.getLineNumber();
	}

	/**
	 * The method is to get the current player number in the game
	 * @return Returning the current player number in the game
	 */
	public int getPlayerNumber() 
	{
		return playerNumber;
	}

	/**
	 * The method is to set the current player number in the game
	 * @param playerNumber The current total player number of the game with int type
	 */
	public void setPlayerNumber(int playerNumber) 
	{
		this.playerNumber = playerNumber;
	}
	
	/**
	 * The method is to set the current player number that selected by the user to the game
	 * It also contains the function to assign the initial armies for each player
	 * @param playerNumber The number of players that are selected with int type
	 */
	public void setPlayers(int playerNumber) 
	{
		setPlayerNumber(playerNumber);
		int armiesNumber = getInitialArmiesNumber();
		
		for(int i = 1; i <= playerNumber; i++) 
		{
			players.add(new Player(i, armiesNumber, this, new Human()));
		}
	}
	
	/**
	 * Return card bonus and increase it by 5 each time a player receives this bonus
	 * @return the card bonus
	 */
	public static int getCardBonus() {
		cardBonus += 5;
		return cardBonus;
	}
	
	/**
	 * The method is to generate initial number of armies for each player when the game start
	 * It also includes the condition for different player number with different initial armies number for each player
	 * @return Returning different army number with different cases:
	 * 	case 2 with 40 army each player, 
	 * 	case 3 with 35 army each player, 
	 *  case 4 with 30 army each player, 
	 *  case 5 with 25 army each player, 
	 *  case 6 with 20 army each player, 
	 *  others Return 0; 
	 */	
	public int getInitialArmiesNumber() 
	{
		switch(this.playerNumber) 
		{
		case 2:
			return 40;
		case 3:
			return 35;
		case 4:
			return 30;
		case 5:
			return 25;
		case 6:
			return 20;
		}
		return 0;
	}
	
	/**
	 * The method of this function is to distribute the countries on the map between the players.
	 * It will deploy 1 army of each player as take turns until all the countries are owned by players
	 */
	public void distributeCountries() 
	{
		ArrayList<Country> freeCountries = new ArrayList<Country>(countries);

		do 
		{
			for(Player p : players) 
			{
				if(freeCountries.size() > 0) 
				{
					int i = Rng.getRandomInt(0, freeCountries.size()-1); //Random assignment
					Country c = freeCountries.remove(i);
					c.setPlayer(p);
					c.setArmyNumber(1);
					p.ownedCountries.add(c);
					p.setArmies(p.getArmies()-1);
				}
			}
		}while(freeCountries.size() > 0);
		
		setChanged();
		notifyObservers(this);
	}

	/**
	 * The method is to check whether the country owned by the correct player. 
	 * Returning false if the match is incorrect, and returning true if the match is correct.
	 * @return Returning false if the country is not owned by the correct owned player, otherwise returning true.
	 */
	public boolean isOwned() 
	{
		Player owner = countries.get(0).getPlayer();
		for(Country c : countries) 
		{
			if(c.getPlayer() != owner)	return false;
		}
		
		return true;
	}
	
	/**
	 * Add armies to a specific country by its id 
	 * @param c Country id
	 * @param armiesNumber Armies number to add
	 */
	public void addArmiesToCountry(Country c, int armiesNumber) 
	{
		if(armiesNumber == 0) return;
		
		c.setArmyNumber(c.getArmyNumber() + armiesNumber);
		
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * Add armies to a country, and reduces the number of army in player's hand.
	 * @param countryNumber Country id.
	 * @param armiesNumber Number of armies to add.
	 */
	public void addArmiesFromHand(int countryNumber, int armiesNumber) {
		if(armiesNumber == 0) return;
		
		Country c = countries.get(countryNumber-1);
		Player p = c.getPlayer();
		
		p.setArmies(p.getArmies() - armiesNumber);
		c.setArmyNumber(c.getArmyNumber() + armiesNumber);
		
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * Add armies to a country, and reduces the number of army in player's hand.
	 * @param c The country that receives the armies.
	 * @param armiesNumber Number of armies to add.
	 */
	public void addArmiesFromHand(Country c, int armiesNumber) {
		if(armiesNumber == 0) return;
		
		Player p = c.getPlayer();
		
		p.setArmies(p.getArmies() - armiesNumber);
		c.setArmyNumber(c.getArmyNumber() + armiesNumber);
		
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * To get selected country with an id
	 * @param ctryId the id entered by the user
	 * @return the country selected
	 */
	public Country getCountry(int ctryId) {
		return countries.get(ctryId-1);
	}

	/**
	 * The method is to calculate the country number that the current player owned.
	 * It also calculate the number of deployable armies that the current player can get
	 * @param p The current player
	 * @return Returning the number of deployable armies that the current player left
	 */
	public int calculateArmyNum(Player p) 
	{
		int nbCtry = 0;
		/* Search how many countries are owned by current player */
		for(Country c : countries) {
			if(p == c.getPlayer()) 
			{
				nbCtry ++;
			}
		}
		/* calculate how many armies the player should have */
		int numOfArmy = nbCtry/3;
		int continentBonus = wholeContinentBonus(p);
		if((numOfArmy + continentBonus) < 3) 
		{
			return 3;
		}
		return (numOfArmy+continentBonus);
	}
	
	/**
	 * The method is to check whether the player can take over the whole continent
	 * @param p The current player
	 * @return Returning the number of extra armies that the player will get for owned the current continent
	 */
	public int wholeContinentBonus(Player p)
	{
		int bonusArmies = 0;
		/* search continents */
		for(Continent conti: continents) 
		{
			/* if the player owns current continent, get bonus armies */
			 if(conti.ownedBy(p)) 
			 {
				 bonusArmies += conti.getExtraArmies();
			 }
		}
		return bonusArmies;
	}
	
	/**
	 * The method is to clear the data information of the country
	 */
	public void clear() 
	{
		Country.Counter = 0;
		Map.cardBonus = 0;
		for(Player p : this.players)	p.clear();
		countries.clear();
		continents.clear();
	}

	/**
	 * The method for getting map name
	 * @return Returning name of the map name
	 */
	public String getName() 
	{
		return name;
	}

	/**
	 * The method for setting map name
	 * @param name The map name
	 */
	public void setName(String name) 
	{
		this.name = name;
	}

	/**
	 * get the player's current phase
	 * @return print out the phase and player state
	 */
	public Phase getPhase() {
		return phase;
	}
	
	/**
	 * set the player's current phase
	 * @param phase Set the phase
	 */
	public void setPhase(Phase phase) {
		this.phase = phase;
	}
}

package model.map;

import java.util.HashSet;
import java.util.Stack;

/**
 * This class is in charge of checking if a map pass all the tests, to determine if it is playable
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public class MapChecker {
	
	/**
	 * the map data that need to check
	 */
	private Map map;
	
	/**
	 * Constructor of the MapChecker class.
	 * 
	 * @param newMap The map that is going to be checked.
	 */
	public MapChecker(Map newMap) {
		this.map = newMap;
	}
	
	/**
	 * check if the map is valid by: 
	 * 1. Checking if the map is empty, 
	 * 2. Checking if a country without any neighbor
	 * 3. Checking if a continent without any country
	 * 4. Checking if a continent is connected to another one
	 * 5. Checking if a continent is a connected subgraph
	 * 
	 * Unconnected map = DFS on Countries, trying to get all the countries
	 * Unconnected continent = DFS on continents
	 * 
	 * @return Returning true when all the checking functions pass, another situation will return false
	 */
	public boolean check() 
	{
		return (checkPlayableMap() && checkConnectedMap() && checkNoEmptyContinent() && checkConnectedContinents() && checkContinentsAsConnectedSubgraphs());
	}
	
	/**
	 * check if there is any country and continent in the map 
	 * check if the total country number is more than total player number
	 * @return true if the map is valid, otherwise false
	 */
	public boolean checkPlayableMap() 
	{
		/* check does map have any country and continent */
		if (map.countries == null || map.countries.size() == 0 || 
				map.continents == null ||map.continents.size() == 0) 
		{
			System.out.println("There is no country or continent in the map");
			return false;
		}
		/*check if the total number of country is more than total number of player*/
		else if (map.players.size() > map.countries.size()) 
		{
			System.out.println("There are not enough country for all players");
			return false;
		}
		
		return true;
	}
	
	/**
	 * The method checks if there are empty continents which does not have any country signed under those continents
	 * @return Returning false if such continents exist, otherwise true
	 */
	public boolean checkNoEmptyContinent() 
	{
		for(Continent ct : map.continents) 
		{
			if(ct.countries == null || ct.countries.size() == 0)
			{
				System.out.print("There is no country in the continent");
				return false;
			}
		}
		return true; 
	}
	
	/**
	 * To check if the map is connected : each country must be able to access all other countries.
	 * @return Returning false if map unconnected, otherwise true
	 */
	public boolean checkConnectedMap() 
	{
		if (map.countries == null || map.countries.size() == 0)	return false;
				
		Stack<Country> open = new Stack<Country>();
		HashSet<Country> closed = new HashSet<Country>();
		
		open.push(map.countries.get(0));
		
		while(open.size() != 0) {
			Country current = open.pop();
			
			for(Country neighbor : current.neighbors) {
				if(!closed.contains(neighbor)) {
					open.push(neighbor);
				}
			}
			closed.add(current);
		}
		
		if(closed.size() != map.countries.size())
		{
			System.out.println("The map is not a connected graph");
			return false;
		}
		
		return true;
	}
	
	/**
	 * To check if the continents are connected : each continent must be able to access all other continent.
	 * @return Returning false if a continent is unconnected, otherwise true
	 */
	public boolean checkConnectedContinents() 
	{
		if(map.countries == null || map.countries.size() == 0
		|| map.continents == null || map.continents.size() == 0)	return false;
		
		Stack<Country> open = new Stack<Country>();
		HashSet<Country> closed = new HashSet<Country>();
		HashSet<Continent> closedContinents = new HashSet<Continent>();
		
		open.push(map.countries.get(0));
		
		while(open.size() != 0) {
			Country current = open.pop();
			
			for(Country neighbor : current.neighbors) {
				if(!closed.contains(neighbor)) {
					open.push(neighbor);
				}
			}
			closedContinents.add(current.getContinent());
			closed.add(current);
		}
		
		if(closedContinents.size() != map.continents.size())
		{
			System.out.println("The continents are not all accessible");
			return false;
		}
		
		return true;
	}

	/**
	 * To check if each continent is a connected subgraph. DFS on each continent, trying to access all the countries once.
	 * @return Returning false if a continent is not a connected subgraph, otherwise true
	 */
	public boolean checkContinentsAsConnectedSubgraphs() 
	{
		if(map.countries == null || map.countries.size() == 0
		|| map.continents == null || map.continents.size() == 0)	return false;
		
		for(Continent c : map.continents) {
			Stack<Country> open = new Stack<Country>();
			HashSet<Country> closed = new HashSet<Country>();
			
			open.push(c.countries.get(0));
			
			while(open.size() != 0) {
				Country current = open.pop();
				
				for(Country neighbor : current.neighbors) {
					if(!closed.contains(neighbor) && neighbor.getContinent() == c) {
						open.push(neighbor);
					}
				}
				closed.add(current);
			}
			
			if(closed.size() !=  c.countries.size())
			{
				System.out.println("The continent " + c.getName() + " is not a connected subgraph");
				return false;
			}
		}

		return true;
	}
}

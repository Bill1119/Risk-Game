package controller;

import java.io.IOException;
import java.util.ArrayList;

import model.map.MapEditor;
import view.common.MapSelectionView;
import view.mapEditor.EditView;
import view.mapEditor.EditorMenuView;
import view.mapEditor.MapEditorView;

/**
 * The class is for the control of map editing part
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public class MapEditorController 
{
	/**
	 * the data of map editor that include the changing of a map and edit it.
	 */
	private MapEditor mapEditor;
	
	/**
	 * the data of map editor view that shows map editor options
	 */
	private MapEditorView mapEditorView;
	
	/**
	 * the data of editor menu view that can show the editor menu
	 */
	private EditorMenuView editorMenuView;
	
	/**
	 * the data of edit view that show the edit option
	 */
	private EditView editView;

	/**
	 * This is the constructor method of MapEditorController
	 */
	public MapEditorController()
	{
		try 
		{
			mapEditor = new MapEditor();
			mapEditorView = new MapEditorView();
			editorMenuView = new EditorMenuView();
			editView = new EditView();
			
			mapEditor.addObserver(mapEditorView);
			
			execute();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * The method is to execute the map editor and then manage the choice options to the players.
	 * @throws IOException reject an error
	 */
	private void execute() throws IOException
	{
		int choice = editorMenuView.print();
		
		if(choice == 1) 
		{
			String mapName = editorMenuView.askMapName();
			mapEditor.setMapName(mapName);
		} 
		else if(choice == 2) 
		{
			MapSelectionView mapSelectionView = new MapSelectionView();
			String mapFilePath = mapSelectionView.selectMap();
			mapEditor.setMapName(mapSelectionView.getMapName());
			mapEditor.load(mapFilePath);
			mapEditorView.print(mapEditor.map);
		}
		
		int option;
		do 
		{
			option = editorMenuView.menu();
			
			switch(option) 
			{
			case 0: /* Save & exit */
				mapEditor.save();
				break;
			case 1:
				addCountry();
				break;
			case 2:
				addContinent();
				break;
			case 3:
				deleteCountry();
				break;
			case 4:
				deleteContinent();
				break;
			case 5: /* Exit */
				return;
			}
		}while(option != 0);
	}
	
	/**
	 * The method to delete country
	 */
	private void deleteCountry() 
	{
		int maxInput = mapEditor.getMaxInputNumber();
		int ctryNumber = editView.askCountryNumber(maxInput);
		mapEditor.deleteCountry(ctryNumber);
	}

	/**
	 * The method to delete continent
	 */
	private void deleteContinent() 
	{
		int maxInput = mapEditor.getMaxInputNumber();
		int contNumber = editView.askContinentNumber(maxInput);
		mapEditor.deleteContinent(contNumber);
	}

	/**
	 * The method to add Country
	 */
	public void addCountry() 
	{
		if(mapEditor.map.continents.size() > 0) 
		{
			String ctryName = editView.askCountryName();
			int maxInput = mapEditor.getMaxInputNumber();
			int contNb = editView.askContinentNumber(maxInput);
			ArrayList<Integer> neighborNumbers = new ArrayList<Integer>();
			/* Ask all neighbors until user presses 0 */
			int currentInput;
			do {
				currentInput = editView.askNeighbor(maxInput);
				if(currentInput != 0) 
				{
					neighborNumbers.add(currentInput);
				}
			}while(currentInput != 0);
			
			if(!mapEditor.addCountry(ctryName, contNb, neighborNumbers))	editView.errorAddingCountry();
		}
	}
	
	/**
	 * The method to add Continent
	 */
	public void addContinent() 
	{		
		String continentName = editView.askContinentName();
		int bonus = editView.askBonus();
		if(!mapEditor.addContinent(continentName, bonus))	editView.errorAddingContinent();
	}
}

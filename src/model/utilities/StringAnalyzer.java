package model.utilities;

import java.io.File;

/**
 * 
 * The class's main function is to read and check .map file string lines of the current selected file.
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 *
 */
public class StringAnalyzer 
{
	/**
	 * The method is to read the contents of .map files.
	 * 
	 * @param str Content of a specific string type line.      
	 * @param lineNb Line number of the string line.      
	 * @return Returning the specific line number.
	 */
	public static int parseInt(String str, int lineNb) 
	{
		if (str == null) 
		{
			return lineNb;
		}
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) 
		{
			
		}
		return lineNb;
	}
	
	/**
	 * The method is to check whether the file is .map file type.
	 * @param file The current file that selected.
	 * @return Returning true if the file is .map file type, otherwise returning false and print message showing that the file is not .map file.
	 */
	public static boolean checkMapType(File file)
	{
		if(file.getName().contains(".map"))
			return true;
		else
		{
			return false;
		}
	}
	
	
}

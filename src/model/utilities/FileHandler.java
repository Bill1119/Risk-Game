package model.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This is the class for file reading, wrting functions.
 * It helps other classes to implement getting and editing the selected files.
 * @author Yann Kerichard, Yueshuai Jiang, Che-Shao Chen
 */
public class FileHandler 
{
	/**
	 * The method is to read the selected file and check whether the file can be open or read.
	 * If the file cannot be open or read, print error messages and the file name.
	 * @param fileName The current selected file name with string type.
	 * @return Returning the file path and the name of the file, or returning as null if the file cannot be open or read.
	 */
	public static String read(String fileName)
	{
        String line = null;

        try 
        {
            FileReader fileReader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line += bufferedReader.readLine()) != null) 
            {
            	//Line is getting the content
            }   

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) 
        {
            System.out.println("Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex) 
        {
            System.out.println("Error reading file '" + fileName + "'");                  
        }
        
        return line;
	}
	
	/**
	 * The method is to write the selected current file.
	 * @param fileName The selected current file that will be write with string type.
	 * @param content The content of the selected file that will be write with string type.
	 */
	public static void write(String fileName, String content)
	{
		try 
		{
            FileWriter fileWriter = new FileWriter(fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(content);

            bufferedWriter.close();
        }
        catch(IOException ex) 
		{
            System.out.println("Error writing to file '" + fileName + "'");
        }
	}
}

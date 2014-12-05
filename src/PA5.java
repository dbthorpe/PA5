// PA5
// Authors: David Thorpe, Melinda Ryan
// Date: 12/1/2014
// Class: CS200

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class PA5
{
	
	public static void main(String[] args)
	{
		//arraylist for the file names
		ArrayList<String> fileNames = new ArrayList<String>();
		
		//arraylist for words on which you should run whichPages
		ArrayList<String> whichPagesWords = new ArrayList<String>();
		
		int size = 0;
		
		try
		{
			//Read in the file
			Scanner scanFile = new Scanner(new File(args[0]));
			
			//The name of the dot file to create in Graph class
			String dotFileName = scanFile.next();
			
			//Read in the size of the hash table
			size = scanFile.nextInt();
			
			//eat the extra newline
			scanFile.nextLine();
			
			//tell the scanner to scan file names until the flag
			while(true){
				
				//scan in the line
				String s = scanFile.nextLine();
				if(s.equals("*EOFs*")){
					break;
				}
				else{
					fileNames.add(s);
				}
			}
			
			//read in the words for whichPages
			/*while(scanFile.hasNext()){
			String s = scanFile.nextLine();
			whichPagesWords.add(s);
			}*/
			
			//create the WebPages object
			WebPages webPages = new WebPages(size);
			
			//add the new pages
			for(int i = 0; i < fileNames.size(); i++){
				webPages.addPage(fileNames.get(i));
			}
			
			//webPages.printTerms();
			
			//Get stop words for removal
			String stops = scanFile.nextLine().trim();
			
			while(!stops.equals("*STOPs*"))
			{
				//Remove stop words from webpages 
				webPages.pruneStopWords(stops);
				stops = scanFile.nextLine().trim();
			}
			
			//print all the terms
			webPages.printTerms();
			
			//get querys and call bestPage
			Object[] arr;
			String query = "";
			
			while(scanFile.hasNext()){
				query = scanFile.nextLine().toLowerCase();
				arr = webPages.bestPages(query);
				
				//Separate the query words to sort alphabetically
				StringTokenizer tokens = new StringTokenizer(query);
				int numWords = tokens.countTokens();
				int i = 0;
				String[] separateWords = new String[numWords];
				
				//fill array with individual words
				while(tokens.hasMoreTokens())
				{
					separateWords[i] = tokens.nextToken();
					i++;
				}
				
				//Sort array of individual query words alphabetically
				Arrays.sort(separateWords);
				
				//Create output string for query words
				String outputWords = "";
				for(String val : separateWords)
				{
					outputWords += val + " ";
				}
				
				//If arr not null, at least one term in query also in webpages
				if(arr != null)
					System.out.printf("[%s] in %s: %.2f\n", outputWords, arr[0],arr[1]);
				else
					System.out.printf("[%s] not found\n", outputWords);
			}
			scanFile.close();
			//run whichPages method
			/*for(int i = 0; i < whichPagesWords.size(); i++){
			//String array of pages in which the word occurs
			String[] array = webPages.whichPages(whichPagesWords.get(i).toLowerCase());
			//String to be printed for each whichPages word
			String s = whichPagesWords.get(i);
			//print the depth
			webPages.printDepth(s);
			//if the word isn't found, return word not found
			if(array == null){
			s += " not found";
			}
			//otherwise, return the word and the pages it occurs on + the TFIDF for that page
			else{
			//String to print
			s += " in pages: ";
			double d;
			DecimalFormat df = new DecimalFormat("0.00");
			for(int j = 0; j < array.length-1; j++){
			d = webPages.TFIDF(array[j], whichPagesWords.get(i).toLowerCase());
			s += array[j] + ": " + df.format(d) + ", ";
			}
			d = webPages.TFIDF(array[array.length-1], whichPagesWords.get(i).toLowerCase());
			s += array[array.length-1] + ": " + df.format(d);
			}
			System.out.println(s);
			}*/
		}//end try
		catch(Exception e){
			System.out.println("Error: " + e);
			System.exit(0);
		}//end catch
	}
}
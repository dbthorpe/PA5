// PA5
// Authors: David Thorpe, Melinda Ryan
// Date: 12/1/2014
// Class: CS200

import java.util.ArrayList;
import java.util.LinkedList;

public class Term 
{

	//Instance variable for the word
	private String name;
	//Instance variable for the number of times a word appears in all documents
	private int totalFrequency;
	//Instance variable for the number of documents in which word appears
	private int docFrequency;
	//Instance variable for LinkedList of Occurrences (one for each document in which the term appears)
	private LinkedList<Occurrence> listOfFiles = new LinkedList<Occurrence> ();
	//Instance variable for ArrayList of file names
	private ArrayList<String> listOfFileNames = new ArrayList<String>();
	//depth of each Term in the binary search tree
	private int depth;
	

	//Constructor
	public Term(String name){
		
		//set name to lower case
		this.name = name.toLowerCase();
		
		//initialize the docFrequency to 0
		docFrequency = 0;
	
		//initialize the totalFrequency to 0
		totalFrequency = 0;
	}
	
	//Getter for name
	public String getName() {
		return name;
	}

	//Getter for docFrequency
	public int getDocFrequency() {
		return docFrequency;
	}
	
	//Getter for totalFrequency
	public int getTotalFrequency() {
		return totalFrequency;
	}
	
	//Get termFrequency for a specific document
	public double getTermFrequency(String document){
		Occurrence occurrence = new Occurrence(document);
		int index = listOfFiles.indexOf(occurrence);
		int frequency = listOfFiles.get(index).getTermFrequency();
		return frequency;
	}
	
	//Getter for list of file names
	public ArrayList<String> getListOfFileNames(){
		return listOfFileNames;
	}
	
	public void setDepth(int depth)
	{
		this.depth = depth;
	}
	public int getDepth()
	{
		return depth;
	}
	
	public void incFrequency(String document){
		
		//increment the totalFrequency
		totalFrequency += 1;
		
		//create a new Occurrence
		Occurrence newWord = new Occurrence(document);
		
		//if the list of files doesn't contain an occurrence for the document, add it
		if(!listOfFiles.contains(newWord)){

			//add the new occurrence to the list
			listOfFiles.add(newWord);
			
			//add the file name to the arraylist
			listOfFileNames.add(newWord.getDocName());
			
			//increment docFrequency
			docFrequency += 1;			
			
		}
		
		//otherwise, increment the frequency for that document
		else{
			
			//increment frequency
			int index = listOfFiles.indexOf(newWord);
			listOfFiles.get(index).incFrequency();
		}		
		
	}

	public boolean equals(Object other)
	{
		Term otherTerm = (Term) other;
		return (this.name.equals(otherTerm.name));
	}
	

}
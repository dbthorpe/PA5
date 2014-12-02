// PA5
// Authors: David Thorpe, Melinda Ryan
// Date: 12/1/2014
// Class: CS200

public class Occurrence 
{

	//instance variable for the name of the document
	private String docName;

	//Getter for docName
	public String getDocName(){
		return docName;
	}

	//instance variable for the frequency of a term in a document
	private int termFrequency;  
	
	//Getter for termFrequency
	public int getTermFrequency(){
		return termFrequency;
	}

	//constructor
	public Occurrence(String name)
	{

		docName = name;
		termFrequency = 1;

	}

	//method to increment termFrequency by 1
	public void incFrequency()
	{

		termFrequency += 1;

	}

	//equals
	public boolean equals(Object other){

		Occurrence newObject = (Occurrence)other;

		if(newObject.docName.equals(this.docName)){
			return true;
		}

		else{
			return false;
		}
	}

}
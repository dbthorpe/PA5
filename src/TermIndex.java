// PA5
// Authors: David Thorpe, Melinda Ryan
// Date: 12/1/2014
// Class: CS200

public interface TermIndex 
{
    //Add a term to the TermIndex
	public void add(String filename, String newWord);

    //Return number of terms, or size of termIndex data structure
	public int size();

    //Remove a term from the termIndex
	public void delete(String word);

    //Return a term from the termIndex
	public Term get(String word, Boolean printP);
}

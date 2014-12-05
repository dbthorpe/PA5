// PA5
// Authors: David Thorpe, Melinda Ryan
// Date: 12/1/2014
// Class: CS200

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Pattern;
public class WebPages
{
	//Instance variable for the GRAPH? will probably need to change this
	private Graph docGraph = new Graph();
	//Instance variable for hash table of Terms
	private HashTable termIndex;
	//instance variable for the number of pages read in
	private int pageCount;
	//initializes a new index, a binary search tree of Term
	public WebPages(int size)
	{
		termIndex = new HashTable(size);
	}
	//reads in the page in filename, divides it into words as before
	//and adds those words and their counts to the termIndex.
	public void addPage(String filename)
	{
		try
		{
			//increment the pageCount
			pageCount++;
			
			makeGraph(filename);
			
			//read line-by-line through the file to get words
			Scanner readFile = new Scanner(new File(filename));
			while(readFile.hasNextLine())
			{
				//read in a line
				String line = readFile.nextLine();
				//remove HTML tags from the line
				line = stripHTML(line);
				//line = line.replaceAll("<", " ").replaceAll(">", " ");
				//delimit by everything but letters, numbers, ', and <>
				Scanner readLine = new Scanner(line).useDelimiter("[^\\w'<>]+");
				while(readLine.hasNext())
				{
					//set the line to lowercase
					String word = readLine.next().toLowerCase();
					//add the word to the TermIndex
					addToTermIndex(word, filename);
				}
				readLine.close();
			}
			readFile.close();
		}
		catch(IOException e)
		{
			System.out.println("Error: Unable to read file");
		}
	}
	
	private void makeGraph(String filename){
		try {
			
			Scanner doc = new Scanner(new File(filename));
			Pattern p = Pattern.compile("<?a(\\W*)href=\"http:/(/)(.*)\">");
			String temp = doc.findWithinHorizon(p,0);
			while(temp != null){
				//do stuff
				temp = temp.substring(temp.indexOf('/')+2, temp.indexOf('>')-1);
				if(!temp.isEmpty()){
					docGraph.addEdge(temp, filename, 1);
				}
				//advance temp
			 temp = doc.findWithinHorizon(p,0);
			
			}
			
		} catch (FileNotFoundException e) {
		}
	}
	
	public void printDepth(String word)
	{
		//get term depth in binary tree
		termIndex.get(word, true);
	}
	//prints on a separate line each word followed by two spaces followed by its
	//frequency in the order in which it is stored in the ArrayList (as in PA1).
	public void printTerms()
	{
		System.out.println(toListString());
	}
	//returns required output string for arraylist
	public String toListString()
	{
		String outputString = "WORDS\n";
		//check for an empty tree
		if(termIndex.size() == 0)
			return "Error: Empty List";
		else
		{
			HashTableIterator itr = new HashTableIterator(termIndex);
			while(itr.hasNext())
			{
				outputString += ((itr.next()).getName() + "\n");
			}
		}
		return outputString;
	}
	//method to strip HTML tags out of a string
	public String stripHTML(String a)
	{
		return a.replaceAll("<.*?>", "");
	}
	//adds words to the term tree
	public void addToTermIndex(String word, String document)
	{
		termIndex.add(document, word);
	}
	//TFIDF method
	public double TFIDF(String document, String word)
	{
		//get the term for that word
		Term term = termIndex.get(word, false);
		float TF = (float) term.getTermFrequency(document);
		float D = pageCount;
		float DF = term.getDocFrequency();
		return TF * Math.log(D / DF);
	}
	//whichPages method
	public String[] whichPages(String word){
		//make a new term to compare to the term index
		Term newTerm = new Term(word);
		//search through term index
		if(termIndex.contains(newTerm)){
			//get the listOfFileNames for that term
			ArrayList<String> arrayList = termIndex.get(word, false).getListOfFileNames();
			//copy array list to string array
			String[] stringArray = new String[arrayList.size()];
			for(int i = 0; i < arrayList.size(); i++){
				stringArray[i] = arrayList.get(i);
			}
			//return array
			return stringArray;
		}
		else{
			return null;
		}
	}
	public Object[] bestPages(String query){
		// if there is no query, then return null
		if(query.isEmpty()){
			return null;
		}
		// iterator and supporting data structures
		HashTableIterator iter = new HashTableIterator(termIndex);
		ArrayList<String> queryList = new ArrayList<String>();
		ArrayList<String> docList = new ArrayList<String>();
		ArrayList<Double> common = new ArrayList<Double>();
		ArrayList<Double> docSpecific = new ArrayList<Double>();
		ArrayList<Double> simList = new ArrayList<Double>();
		// variables needed for computation
		double n = pageCount;
		int highestSim = 0;
		double queryWeights = 0;
		double wiq = 0;
		double tfidf = 0;
		// add each word in the query to the arraylist
		Scanner q = new Scanner(query);
		while(q.hasNext()){
			queryList.add(q.next());
		}
		Collections.sort(queryList);
		// traverse term index
		while(iter.hasNext()){
			Term temp = iter.next();
			//check to see if term is in query
			if(queryList.contains(temp.getName())){
				//since term is in query calculate the queryweights
				wiq = .5 * (1+ Math.log(n/(double)temp.getDocFrequency()));
				queryWeights += (wiq*wiq);
			}
			// for each doc that contains term i
			for(int i=0; i< temp.getDocFrequency(); i++){
				// calculate tfidf and add to the docSpecific
				tfidf = TFIDF(temp.getListOfFileNames().get(i), temp.getName()); //* docGraph.inDegree(temp.getListOfFileNames().get(i));
				if(!docList.contains(temp.getListOfFileNames().get(i))){
					docList.add(temp.getListOfFileNames().get(i));
					docSpecific.add((tfidf*tfidf));
					// if term is also in the query, calculate common, and added to current common
					if(queryList.contains(temp.getName()))
						common.add(wiq*tfidf);
					else
						common.add(0.0);
				}
				else{
					// add the value to the current docSpecific
					int index = docList.indexOf(temp.getListOfFileNames().get(i));
					docSpecific.set(index, docSpecific.get(index) + (tfidf*tfidf));
					//if term is also in the query, calculate common, and added to current common
					if(queryList.contains(temp.getName()))
						common.set(index, common.get(index) + (wiq*tfidf));
				}
			}
		}
		// calculate the sim(d,q) for each document and store it
		for(int i=0; i<docList.size(); i++){
			double sim = common.get(i) / (Math.sqrt(docSpecific.get(i)) * Math.sqrt(queryWeights));
			sim = sim * docGraph.inDegree(docList.get(i));
			simList.add(sim);
			// if this sim is higher than previous highestSim, change index
			if(sim > simList.get(highestSim))
				highestSim = i;
			// if they are the same, set index to lexigraphically larger document
			else if(sim == simList.get(highestSim)){
				if(docList.get(i).compareTo(docList.get(highestSim)) > 0){
					highestSim = i;
				}
			}
		}
		// array to return 2 items
		Object[] returnArr = {docList.get(highestSim), simList.get(highestSim)};
		// no terms in query were found in the array
		if(queryWeights == 0)
			returnArr = null;
		// return the doc and sim
		return returnArr;
	}
	//removes terms from the index
	public void pruneStopWords(String s)
	{
		termIndex.delete(s);
		// call delete based on the string passed
	}
}
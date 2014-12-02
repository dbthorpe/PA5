// PA5
// Authors: David Thorpe, Melinda Ryan
// Date: 12/1/2014
// Class: CS200

public class HashTable implements TermIndex
{
	//Size of the hashtable array
	private int arraySize;
	
	//Size of previous array
	private int oldArraySize;
	
	//number of unique words in the hashtable
	private int count;
	
	//Hash table
	private Term[] table;
	
	//Initial array size to be read from file
	public HashTable(int arraySize)
	{
		this.arraySize = arraySize;
		count = 0;
		table = new Term[this.arraySize];
		oldArraySize = arraySize;
	}
	
	/*public static void main(String[] args)
	{
		HashTable hTable = new HashTable(6);
		Term a = new Term("a");
		hTable.add("txt", a.getName());
		Term g = new Term("g");
		hTable.add("txt", g.getName());
		Term m = new Term("m");
		hTable.add("txt", m.getName());
		Term s = new Term("s");
		hTable.add("txt", s.getName());
		
		
		for(int i = 0; i < hTable.arraySize; i++)
		{
			if(hTable.table[i] != null)
				System.out.println("Index: " + i + " Value: " + hTable.table[i].getName());
			else
				System.out.println("Index: " + i + " NULL");
		}
		
	}*/
	/*The add method will need to expand the size of the array and re-hash all the entries
	 * when it approaches becoming full. Use a threshold of 80% full as the trigger for when to
	 * re-build the hash table. The code should calculate the next size using the following equation:
	 * new_size = (2 * current_size) + 1
	 */
	public void add(String filename, String newWord)
	{
		//Check for table rebuild if more than 80% full
		if(checkRebuildTable())
			rebuildTable();
		
		//create a term from the word
		Term term = new Term(newWord);
		
		//generate a hash function for the Term word
		int code = getHashFunction(newWord);
		
		//if the word is already in the table, increment the frequency
		if(contains(term))
		{
			get(newWord, false).incFrequency(filename);
		}
		//if the word is not in the table, add it
		else
		{
			//increment number of unique words and frequency for filename
			count++;
			term.incFrequency(filename);
			
			//If hash function location null or reserved, add term
			if(table[code] == null || table[code].getName().equals("reserved"))
				table[code] = term;
			
			//If location occupied, try quadratic probing
			else
			{
				int newLocation = quadraticProbe(code, table);
					
				//Place term in new location
				table[newLocation] = term;
			}
		}
	}
	
	//Probe for new location for term in table. Return new location or -1 if no available location found
	private int quadraticProbe(int code, Term[] t)
	{
		int newLocation = 0;
		int probe = 0;
		int i = 1;
		
		while(true)
		{
			//Get new location to check
			newLocation = quadraticProbe(code, i);
			
			//If location is free, return the int
			if(t[newLocation] == null || t[newLocation].getName().equals("reserved"))
			{
				return newLocation;
			}
			
			//If location is not free, increment i to try again
			i++;
			
			//If number of probes (i) is greater than the table size, terminate probe
			if(i >= arraySize)
				throw new IndexOutOfBoundsException("Open position not found.");
		}
	}
	
	//quadratic probe to return location
	private int quadraticProbe(int code, int i)
	{
		int probe = 0;
		int newLocation = 0;
		//for(int j = 1; j <= i; j++)
		//{
		//probe += j * j;
		//newLocation = (code + probe) % arraySize;
		//}
		
		//New quadratic probe
		probe = i * i;
		newLocation = (code + probe) % arraySize;
		return newLocation;
	}
	
	//Generate hash code for word
	private int getHashFunction(String word)
	{
		int code = Math.abs(word.toLowerCase().hashCode());
		code = Math.abs(code % arraySize);
		
		//System.out.println(word + ": " + code);
		return code;
	}
	
	//Returns true if table is >= 80% full
	private Boolean checkRebuildTable()
	{
		double percentFull = (double) count/arraySize;
		return (percentFull >= 0.8);
	}
	
	//Rebuilds a larger table
	private void rebuildTable()
	{
		//New size of the array
		oldArraySize = arraySize;
		arraySize = (2 * arraySize) + 1;
		
		//Make new array
		Term[] newTable = new Term[arraySize];
		
		//Reset the count
		count = 0;
		
		//Iterate through old table to get terms
		HashTableIterator itr = new HashTableIterator(this);
		while(itr.hasNext())
		{
			Term term = itr.next();
			String word = term.getName();
			int code = getHashFunction(word);
			//add terms to new table
			if(newTable[code] == null)
			{
				newTable[code] = term;
				count ++;
			}
			else
			{
				int newLocation = quadraticProbe(code, newTable);
				if(newLocation != -1)
				{
					newTable[newLocation] = term;
					count ++;
				}
			}
		}
		
		oldArraySize = arraySize;
		table = newTable;
	}
	
	//returns the number of unique words in the document (i.e., count).
	public int size()
	{
		return count;
	}
	
	//Returns size of the array
	public int getArraySize()
	{
		return arraySize;
	}
	
	public int getOldArraySize()
	{
		return oldArraySize;
	}
	
	public void setOldArraySize(int size)
	{
		oldArraySize = size;
	}
	
	//Remove terms from the hash table
	public void delete(String word)
	{
		//Create term object from word to delete
		Term term = new Term(word);
		//Find where in the table it may be located
		int code = getHashFunction(word);
		//If the first location checked contains the term, removed it
		if(table[code] != null)
		{
			if(table[code].equals(term))
			{
				Term reservedTerm = new Term("reserved");
				table[code] = reservedTerm;
				count --;
			}
			//Perform quadratic probing to find the term
			else
			{
				for(int i = 1; i <= arraySize; i++)
				{
					int newLocation = quadraticProbe(code, i);
					if(table[newLocation] != null)
					{
						if(table[newLocation].equals(term))
						{
							Term reservedTerm = new Term("reserved");
							table[newLocation] = reservedTerm;
							count --;
							return;
						}
					}
				}
			}
		}
		//Perform quadratic probing to find the term
		else
		{
			for(int i = 1; i <= arraySize; i++)
			{
				int newLocation = quadraticProbe(code, i);
				if(table[newLocation] != null)
				{
					if(table[newLocation].equals(term))
					{
						Term reservedTerm = new Term("reserved");
						table[newLocation] = reservedTerm;
						count --;
						return;
					}
				}
			}
		}
	}
	//returns the Term object for the word. Boolean printP not used
	public Term get(String word, Boolean printP)
	{
		//Term object for search word
		Term findTerm = new Term(word);
		int code = getHashFunction(word);
		//New iterator to search table
		HashTableIterator itr = new HashTableIterator(this);
		//Look through table for the word
		while(itr.hasNext())
		{
			Term term = itr.next();
			//If term has word, return it
			if(term.equals(findTerm))
				return term;
		}
		return null;
	}
	//returns the Term object at a particular position
	public Term get(int position)
	{
		if((position >= 0) && (position<arraySize))
			return table[position];
		else
			return null;
	}
	//Returns true if term is in the hash table
	public boolean contains(Object other)
	{
		if(other instanceof Term)
		{
			Term otherTerm = (Term) other;
			if(get(otherTerm.getName(), false)!=null)
				return true;
		}
		return false;
	}
}
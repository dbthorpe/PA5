// PA5
// Authors: David Thorpe, Melinda Ryan
// Date: 12/1/2014
// Class: CS200

import java.util.Iterator;


public class HashTableIterator implements Iterator<Term>
{
	Queue hashQueue = new Queue();
	private HashTable hashTable;
	
	public HashTableIterator(HashTable hashTable) 
	{
		this.hashTable = hashTable; 
		addToQueue();
		
	}
	public boolean hasNext() 
	{
		return !hashQueue.isEmpty();
	}

	public Term next()
	{
		return hashQueue.dequeue();
	}

	public void remove() 
	{
		//Not implemented
		throw new UnsupportedOperationException();
	}
	
	private void addToQueue()
	{
		for(int i = 0; i < hashTable.getOldArraySize(); i++)
		{
			if(hashTable.get(i) != null && !hashTable.get(i).getName().equals("reserved"))
			{
				hashQueue.enqueue(hashTable.get(i));
			}
		}
	}

}

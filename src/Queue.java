// PA5
// Authors: David Thorpe, Melinda Ryan
// Date: 12/1/2014
// Class: CS200

/** 
*  Code borrowed from CS200 Fall 2014 recitation 7.
*  Changed params to HashNode objects
**/

/**
 * Implements a simple reference based for use in CSU
 * CS200 Fall 2010 Lab 9.
 * Based on code downloaded from the Carrano and Pritchard
 * text web site in 2007. Modified to make greater use of
 * generics.
 * @author David Newman
 * @date 2010-10-14
 *
 * @param <T>
 */
public class Queue 
{
  private HashNode lastNode;
  
  public Queue() 
  {
    lastNode = null;   
  }  // end default constructor
  
  // queue operations:
  public boolean isEmpty() 
  {
    return lastNode == null;
  }  // end isEmpty

  public void dequeueAll() 
  {
    lastNode = null;
  }  // end dequeueAll

  public void enqueue(Term newItem) 
  {
	  HashNode newNode = new HashNode(newItem);

    // insert the new node
    if (isEmpty()) 
    {
      // insertion into empty queue
      newNode.setNext(newNode);
    }
    else 
    {
      // insertion into nonempty queue
      newNode.setNext(lastNode.getNext());
      lastNode.setNext(newNode);
    }  // end if

    lastNode = newNode;  // new node is at back
  }  // end enqueue

  public Term dequeue() throws QueueException 
  {
    if (!isEmpty()) 
    {
      // queue is not empty; remove front
    	HashNode firstNode = lastNode.getNext();
      if (firstNode == lastNode) { // special case?
        lastNode = null;           // yes, one node in queue
      }
      else 
      {
        lastNode.setNext(firstNode.getNext());
      }  // end if
      return firstNode.getItem();
    }
    else 
    {
      throw new QueueException("QueueException on dequeue:"
                             + "queue empty");
    }  // end if
  }  // end dequeue

  public Term peek() throws QueueException 
  {
    if (!isEmpty()) 
    {  
      // queue is not empty; retrieve front
    	HashNode firstNode = lastNode.getNext();
      return (Term) firstNode.getItem();
    }
    else 
    {
      throw new QueueException("QueueException on peek:"
                             + "queue empty");
    }  // end if
  }  // end peek

   
} // end QueueReferenceBased
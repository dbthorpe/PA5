// PA5
// Authors: David Thorpe, Melinda Ryan
// Date: 12/1/2014
// Class: CS200

/** 
*  Code borrowed from CS200 Fall 2014 recitation 7.
*  No changes made.
**/

/**
 * Implements a simple queue for use in CSU
 * CS200 Fall 2010 Lab 9.
 * Based on code downloaded from the Carrano and Pritchard
 * text web site in 2007. Modified to make greater use of
 * generics.
 * @author David Newman
 * @date 2010-10-14
 *
 * @param <T>
 */

public interface QueueInterface<T> {

  public boolean isEmpty();
  // Determines whether a queue is empty.
  // Precondition: None.
  // Postcondition: Returns true if the queue is empty;
  // otherwise returns false.
   
  public void enqueue(T newItem) throws QueueException;
  // Adds an item at the back of a queue.
  // Precondition: newItem is the item to be inserted. 
  // Postcondition: If the operation was successful, newItem
  // is at the back of the queue. Some implementations
  // may throw QueueException if newItem cannot be added
  // to the queue.
  public T dequeue() throws QueueException;
  // Retrieves and removes the front of a queue.
  // Precondition: None.
  // Postcondition: If the queue is not empty, the item
  // that was added to the queue earliest is returned and
  // the item is removed. If the queue is empty, the
  // operation is impossible and QueueException is thrown.

  public void dequeueAll();
  // Removes all items of a queue.
  // Precondition: None.
  // Postcondition: The queue is empty.

  public T peek() throws QueueException;
  // Retrieves the item at the front of a queue.
  // Precondition: None.
  // Postcondition: If the queue is not empty, the item
  // that was added to the queue earliest is returned. 
  // If the queue is empty, the operation is impossible
  // and QueueException is thrown.
}  // end QueueInterface
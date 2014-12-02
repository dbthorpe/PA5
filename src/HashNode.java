// PA5
// Authors: David Thorpe, Melinda Ryan 
// Date: 12/1/2014
// Class: CS200

/** 
*  Code borrowed from CS200 Fall 2014 recitation 7.
*  Changed param to Term object
**/

/**
 * Implements a simple node object for use in lists for use in CSU
 * CS200 Fall 2010 Lab 9.
 * Based on code downloaded from the Carrano and Pritchard
 * text web site in 2007. Modified to make greater use of
 * generics.
 * @author David Newman
 * @date 2010-10-14
 *
 * 
 */

  public class HashNode {
  private Term item;
  private HashNode next;

  public HashNode(Term newItem) {
    item = newItem;
    next = null;
  } // end constructor

  public HashNode(Term newItem, HashNode nextNode) {
    item = newItem;
    next = nextNode;
  } // end constructor

  public void setItem(Term newItem) {
    item = newItem;
  } // end setItem

  public Term getItem() {
    return item;
  } // end getItem

  public void setNext(HashNode nextNode) {
    next = nextNode;
  } // end setNext

  public HashNode getNext() {
    return next;
  } // end getNext

} // end class Node


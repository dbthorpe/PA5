//PA5
//Authors: David Thorpe, Melinda Ryan 
//Date: 12/1/2014
//Class: CS200

import java.util.ArrayList;
import java.util.LinkedList;

public class Graph 
{
	//Adjacency list implemented as an arraylist of linked lists
	ArrayList<Vertex> adjList;
	private int numEdges;
	private int numVertices;
	
	public Graph()
	{
		numVertices = 0;
		numEdges = 0;
		
		adjList = new ArrayList<Vertex>();
		
	}
	
	public int getNumVertices()
	{
		return numVertices;
	}
	
	public int getNumEdges()
	{
		return numEdges;
	}
	
	//Returns the edge weight between from v to w
	public int getEdgeWeight(String v, String w)
	{
		//Make filenames into Edge objects to search list
		Vertex fromVertex = new Vertex(v);
		Vertex toVertex = new Vertex(w);
		
		//If the filename is in the adjacency list
		if(adjList.contains(toVertex))
		{
			//Get the index of the second edge item to retrieve the item
			int index = adjList.indexOf(toVertex);
			toVertex = adjList.get(index);
			
			//Iterate over the linked list of edge items to find second filename
			GraphIterator itr = new GraphIterator(toVertex);
			while(itr.hasNext())
			{
				Vertex vert = itr.next();
				//Next item is the second edge in the graph
				if(vert.equals(fromVertex))
				{
					//get the second edge and return its edge weight
					fromVertex = vert;
					return fromVertex.getWeight();
				}
			}
		}
		
		//Filename is not in the adjacency arraylist
		System.out.println("Filename " + v + " not found.");
		return 0;
	}
	
	//Add directed edge from v to w
	public void addEdge(String v, String w, int weight)
	{
		//Make filenames into Edge objects to search list
		Vertex toVertex = new Vertex(w);
		Vertex fromVertex = new Vertex(v);
		
		/*If the first file is in the adjacency list, add an edge between 
		* the first and second files
		*/
		if(adjList.contains(toVertex))
		{
			int index = adjList.indexOf(toVertex);
			Vertex current = adjList.get(index);
			GraphIterator itr = new GraphIterator(current);
			
			while(itr.hasNext())
			{
				current = itr.next();
			}
			
			current.setNext(fromVertex, weight);
		}
		//If the first file not in adjacency list, add it with new edge
		else
		{
			toVertex.setNext(fromVertex, weight);
			adjList.add(toVertex);
			numVertices ++;
		}
		
		//if the starting vertex not in the list, add it
		if(!adjList.contains(fromVertex))
		{
			//New vertex so it does not have a reference to next
			Vertex newVertex = new Vertex(fromVertex.getName());
			adjList.add(newVertex);
			numVertices ++;
		}
		
		//Increase the number of edges
		numEdges ++;
	}
	
	//Returns the number of edges incoming to a particular filename.
	public int inDegree(String filename)
	{
		
		Vertex toVertex = new Vertex(filename);
		int degree = 0;
		
		//Look for vertex in adjacency list
		if(adjList.contains(toVertex))
		{
			//if it is in the list, add the linked list nodes
			int index = adjList.indexOf(toVertex);
			Vertex current = adjList.get(index);
			GraphIterator itr = new GraphIterator(current);
			
			while(itr.hasNext())
			{
				current = itr.next();
				degree ++;
			}
			
			
		}
		return degree;
	}
	
	/*Creates a file that specifies the graph and can be printed by the dot program. 
	 * The outputFile should have the extension ".dot" added to it
	 */
	public void writeDotFile(String outputFile)
	{
		
	}
    
	public static void main(String[] args)
	{
		Graph graph = new Graph();
		
		graph.addEdge("a","c",5);
		graph.addEdge("a","d",4);
		graph.addEdge("b","c",8);
		graph.addEdge("b","d",10);
		graph.addEdge("c","d",9);
		graph.addEdge("b","a",2);
		
		System.out.println(graph);
	}
	
	public String toString()
	{
		String s = "";
		
		for(int i = 0; i < adjList.size(); i++)
		{
			//get the vertex at the current index of the adjacency list
			Vertex vert = adjList.get(i);
			
			s += vert.getName();
			//System.out.print(s);
			GraphIterator itr = new GraphIterator(vert);
			//Iterate through the linked list
			while(itr.hasNext())
			{
				Vertex curr = itr.next();
				s+= " => " + curr.getName()  + "[" + curr.getWeight() + "]";
				//System.out.print(s);
				
			}
			
			s += " Degree: " + inDegree(vert.getName()) + "\n";
		}
		
		return s;
	}
	
}

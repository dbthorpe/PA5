// PA5
// Authors: David Thorpe, Melinda Ryan 
// Date: 12/1/2014
// Class: CS200

public class Vertex 
{
	private String name;
	private Vertex incomingVertex;
	private int weight;
	
	public Vertex(String v)
	{
		name = v;
		weight = 0;
		incomingVertex = null;
	}
	
	public Vertex(String toVertexName, Vertex fromVertex, int weight)
	{
		name = toVertexName;
		incomingVertex = fromVertex;
		this.weight = 0;
		incomingVertex.weight = weight;
	}
	
	//Accessors and Mutators for instance variables
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public Vertex getIncomingVertex() 
	{
		return incomingVertex;
	}
	public void setIncomingVertex(Vertex incomingVertex) 
	{
		this.incomingVertex = incomingVertex;
	}
	public int getWeight() 
	{
		return weight;
	}
	public void setWeight(int edgeWeight) 
	{
		weight = edgeWeight;
	}
	
	public void setNext(Vertex nextVertex, int nextWeight)
	{
		incomingVertex = nextVertex;
		nextVertex.weight = nextWeight;
	}
	
	public boolean equals(Object other)
	{
		if(other instanceof Vertex)
		{
			Vertex otherVertex = (Vertex) other;
			return this.name.equals(otherVertex.name);
		}
		
		return false;
	}
	
}

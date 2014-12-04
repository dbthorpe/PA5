// PA5
// Authors: David Thorpe, Melinda Ryan 
// Date: 12/1/2014
// Class: CS200

import java.util.Iterator;

public class GraphIterator implements Iterator<Vertex>
{

	Vertex vert;
	
	public GraphIterator(Vertex v)
	{
		vert = v;
	}

	public boolean hasNext() 
	{
		return vert.getIncomingVertex() != null;
	}

	public Vertex next() 
	{
		vert = vert.getIncomingVertex();
		return vert;
	}

	public void remove() 
	{
		throw new UnsupportedOperationException();
		
	}

}

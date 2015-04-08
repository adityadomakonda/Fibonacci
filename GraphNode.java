import java.io.*;
import java.lang.*;
import java.util.*;
import java.math.*;

public class GraphNode{
	int id;
	LinkedList<AdjacencyNode> adjacencyList;
	long distance;

	public GraphNode(int id){
		this.id = id;
		adjacencyList = new LinkedList<Integer>();
		distance = Integer.MAX_VALUE;
		System.out.println("New GraphNode Created");
	}

	public void addNeighbor(int id, long distance){
		AdjacencyNode newAdjacencyNode = new AdjacencyNode(id,distance);
		adjacencyList.add(newAdjacencyNode);
		System.out.println("GraphNode with id: "+id+"	added to GraphNode "+this.id+"'s adjecency list");
	}

	public void resetDistance(){
		// sets the distance to infinity
		distance = Long.MAX_VALUE;
	}
}
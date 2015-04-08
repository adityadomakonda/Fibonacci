import java.io.*;
import java.lang.*;
import java.util.*;
import java.math.*;

public class GraphNode implements Comparable<GraphNode>{
	int id;
	LinkedList<AdjacencyNode> adjacencyList;
	long distance;
	String path;

	public GraphNode(int id){
		this.id = id;
		adjacencyList = new LinkedList<AdjacencyNode>();
		distance = Integer.MAX_VALUE;
		path = "";
		System.out.println("New GraphNode Created");
	}

	public void addNeighbor(int id, long weight){
		AdjacencyNode newAdjacencyNode = new AdjacencyNode(id,weight);
		adjacencyList.add(newAdjacencyNode);
		System.out.println("GraphNode with id: "+id+"	added to GraphNode "+this.id+"'s adjecency list and edge weight is: "+weight);
	}

	public void resetDistance(){
		// sets the distance to infinity
		distance = Long.MAX_VALUE;
		path = "";
	}
	public void setDistance(long val){
		distance = val;
	}

	public int compareTo(GraphNode other){
		if(this.distance == other.distance)
			return 0;
		else
			return (this.distance < other.distance)?-1:1;
	}
}
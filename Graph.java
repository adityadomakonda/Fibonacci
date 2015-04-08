import java.io.*;
import java.lang.*;
import java.util.*;
import java.math.*;

public class Graph{
	int numOfVertices;
	HashMap<Integer,GraphNode> vertexSet;

	public Graph(){
		numOfVertices = 0;
		vertexSet = new HashMap<Integer,GraphNode>();
		System.out.println("New Graph Created");
	}

	public void addVertex(int id){
		if(!vertexSet.containsKey(id)){
			GraphNode newVertex = new GraphNode(id);
			vertexSet.put(id,newVertex);
			numOfVertices++;
			System.out.println("New vertex added with id: "+id);
		}
	}

	public void resetAllDistances(){
		// Sets all the distances in the graph to infinty
		for(int nodeId: vertexSet.keySet()){
			vertexSet.get(nodeId).resetDistance();
		}
	}
}
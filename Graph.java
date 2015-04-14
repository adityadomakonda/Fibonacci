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
			//System.out.println("New vertex added with id: "+id+"	total vetices: "+numOfVertices);
		}
	}

	public void resetAllDistances(){
		// Sets all the distances in the graph to infinty
		for(int nodeId: vertexSet.keySet()){
			vertexSet.get(nodeId).resetDistance();
		}
		System.out.println("All vertex distances set to infinty");
	}

	public void populate(String input){
		// reads stuff from input file and populates the graph
		try{
			Scanner in = new Scanner(new File(input));
			int inputNumOfVertices = in.nextInt();
			int inputNumOfEdges = in.nextInt();
			for(int i=0;i<inputNumOfEdges;i++){
				int vertex1 = in.nextInt();
				int vertex2 = in.nextInt();
				long weight = in.nextLong();
				addVertex(vertex1);
				addVertex(vertex2);
				vertexSet.get(vertex1).addNeighbor(vertex2,weight);
				vertexSet.get(vertex2).addNeighbor(vertex1,weight);
			}
		}
		catch(Exception e){
			System.out.println("File not found: "+input);
		}
		
	}

	public void dijkstraSSP(int source,int destination){
		// Implements dijkstra ssp using Fibonacci;
		resetAllDistances();
		vertexSet.get(source).setDistance(0);
		HashSet<Integer> visited = new HashSet<Integer>();
		//PriorityQueue<GraphNode> heap = new PriorityQueue<GraphNode>();
		FibonacciHeap heap = new FibonacciHeap();
		GraphNode start_node = vertexSet.get(source);
		start_node.path = Integer.toString(start_node.id);
		heap.insert(start_node);
		visited.add(start_node.id);
		while(heap != null){
			//GraphNode min = heap.poll();
			GraphNode min = heap.removeMin();
			if(min.id == destination){
				System.out.println("Shortest Distance between node: "+source+"  and node: "+destination+"  is: "+min.distance+"\n with path: "+min.path);
				break;
			}
			//visited.add(min.id);
			for(AdjacencyNode neighbor: min.adjacencyList){
				GraphNode neighborNode = vertexSet.get(neighbor.vertexId);
				if(!visited.contains(neighborNode.id)){
					heap.insert(neighborNode);
					visited.add(neighborNode.id);
				}
				long edge_weight = neighbor.weight;
				long new_distance = min.distance + edge_weight;
				long old_distance =  neighborNode.distance;
				if(new_distance < old_distance){
					/*if(old_distance != Long.MAX_VALUE){
						heap.remove(neighborNode);
					}*/
					//neighborNode.distance = new_distance;
					neighborNode.path = min.path + " " + Integer.toString(neighborNode.id);
					//heap.add(neighborNode);
					heap.decreaseKey(neighborNode.id, new_distance);
				}
			}
		}
	}

}
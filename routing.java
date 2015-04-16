import java.io.*;
import java.lang.*;
import java.util.*;
import java.math.*;

public class routing{
	public static void main(String[] args){
		String input_file = args[0];
		String ip_file = args[1];
		int source = Integer.parseInt(args[2]);
		int destination = Integer.parseInt(args[3]);
		Graph graph = new Graph();
		graph.populate(input_file);
		graph.populate_ip(ip_file);
		for(int i=0; i<graph.total_vertices; i++){
			// Run Dijkstra's SSP from source ID i
			graph.dijkstraSSP(i);
			// Create the routing table for Vertex id i using the result of Dijkstra's
			graph.createRoutingTable(i);
		}		
		long distance = graph.dijkstraSSP(source,destination);
		System.out.println(distance);
		// Routes a packet from source to destination and prints all the prefixes matched
		graph.route(source, destination);
	}
}
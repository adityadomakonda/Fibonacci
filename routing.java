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
			graph.dijkstraSSP(i);
			graph.createRoutingTable(i);
		}		
		long distance = graph.dijkstraSSP(source,destination);
		System.out.println(distance);
		graph.route(source, destination);
	}
}
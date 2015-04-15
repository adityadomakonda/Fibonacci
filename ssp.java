import java.io.*;
import java.lang.*;
import java.util.*;
import java.math.*;

public class ssp{
	public static void main(String[] args){
		String input_file = args[0];
		int source = Integer.parseInt(args[1]);
		int destination = Integer.parseInt(args[2]);
		Graph graph = new Graph();
		graph.populate(input_file);
		long result = graph.dijkstraSSP(source,destination);
		System.out.println(result);
		System.out.println(graph.vertexSet.get(destination).path);
	}
}
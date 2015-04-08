import java.io.*;
import java.lang.*;
import java.util.*;
import java.math.*;

public class Global{
	public static void main(String[] args){
		String input_file = args[0];
		int source = Integer.parseInt(args[1]);
		int destination = Integer.parseInt(args[2]);
		Graph graph = new Graph();
		graph.populate(input_file);
		graph.dijkstraSSP(source,destination);
	}
}
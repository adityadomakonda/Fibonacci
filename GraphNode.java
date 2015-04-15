import java.io.*;
import java.lang.*;
import java.util.*;
import java.math.*;
import java.util.regex.Pattern;

public class GraphNode implements Comparable<GraphNode>{
	int id;
	LinkedList<AdjacencyNode> adjacencyList;
	long distance;
	String path;
	String ip_add;
	String ip_binary;
	Trie routing_table;

	public GraphNode(int id){
		this.id = id;
		adjacencyList = new LinkedList<AdjacencyNode>();
		distance = Integer.MAX_VALUE;
		path = "";
		ip_add = "";
		ip_binary = "";
		routing_table = null;
		//System.out.println("New GraphNode Created");
	}

	public void addNeighbor(int id, long weight){
		AdjacencyNode newAdjacencyNode = new AdjacencyNode(id,weight);
		adjacencyList.add(newAdjacencyNode);
		//System.out.println("GraphNode with id: "+id+"	added to GraphNode "+this.id+"'s adjecency list and edge weight is: "+weight);
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

	public void setIp(String ip){
		ip_add = ip;
		ip_binary = IptoString(ip);
		//System.out.println("IPs set  string: "+ip_add+"    binary:  "+ip_binary);
	}

	public String IptoString(String ip)
	{
		String[] inp = ip.split(Pattern.quote("."));
		int num = 0;
		StringBuilder binaryCode = new StringBuilder(); 
		String currbinaryCode = "";
		for(int i = 0; i < inp.length; i++)
		{
			num = Integer.parseInt(inp[i]);
			currbinaryCode = Integer.toBinaryString(num);
			if(currbinaryCode.length() != 8)
			{
				currbinaryCode = AppendZeros(currbinaryCode);
			}
			binaryCode.append(currbinaryCode);
		}
		return binaryCode.toString();
	}
	
	public String AppendZeros(String code)
	{
		StringBuilder sb = new StringBuilder();
		int numzeros = 8 - code.length();
		
		for(int i = 0; i < numzeros; i++)
		{
			sb.append("0");
		}
		sb.append(code);
		return sb.toString();
	}
}
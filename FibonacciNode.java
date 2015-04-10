import java.io.*;
import java.lang.*;
import java.util.*;
import java.math.*;

public class FibonacciNode{
	int degree;
	GraphNode vertex;
	boolean childCut;
	FibonacciNode parent;
	FibonacciNode left;
	FibonacciNode right;
	FibonacciNode child;

	public FibonacciNode(GraphNode vertex){
		degree = 0;
		this.vertex = vertex;
		childCut = false;
		parent = null;
		left = null;
		right = null;
		child = null;
		//System.out.println("FibonacciNode created");
	}
}
import java.io.*;
import java.lang.*;
import java.util.*;
import java.math.*;

public class FibonacciHeap{
	FibonacciNode head;
	int numOfMinTrees;

	public FibonacciHeap(){
		head = null;
		System.out.println("FibonacciHeap created");
		numOfMinTrees = 0;
	}

	public void insert(int data){
		FibonacciNode new_node = new FibonacciNode(data);
		numOfMinTrees++;
		if(head == null){
			// Heap is empty so make new node as the new min
			head = new_node;
			head.left = head;
			head.right = head;
			System.out.println("Node with data: "+data+"	inserted as head");
			return;
		}
		else{
			// Inserting to the left of the head
			head.left.right = new_node;
			new_node.left = head.left;
			head.left = new_node;
			new_node.right = head;
			System.out.println("Node with data: "+data+"	inserted to the left of head");			
		}
		// Update the new min
		if(new_node.data < head.data){
			head = new_node;
		}	
		return;
	}

	public int removeMin(){
		if(numOfMinTrees == 0){
			// Heap is empty
			System.out.println("removeMin failed: Heap is empty");
			return -1;
		}
		else if(numOfMinTrees == 1){
			// Single min tree
			int min = head.data;
			numOfMinTrees = head.degree;
			head = head.child;
			// set new min
			if(numOfMinTrees < 2){
				// if heap has only one or no min trees then there is no need to update the head;
				System.out.println("min removed is: "+min+"		new heap has: "+numOfMinTrees+"		min trees at head");
				return min;
			}
			else{
				// Finding new min				
				numOfMinTrees = pairWiseCombine(head);
				FibonacciNode new_min = head;
				FibonacciNode cur = head.left;
				while(cur != head){
					if(cur.data < new_min.data){
						new_min = cur;
					}
					cur = cur.left;
				}
				head = new_min;
				System.out.println("min removed is: "+min+"		new heap has: "+numOfMinTrees+"		min trees at head and new min is: "+head.data);
				return min;
			}			
		}
		else{
			// More than one min trees are present in the heap
			int min = head.data;
			FibonacciNode min_node = head;

		}
	}
}
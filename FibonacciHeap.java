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
    /*
	Insert - Done
	Remove Min
    Meld - Done
    Remove - Done
    Decrease Key - Done
    */
	public void insert(GraphNode vertex){
		FibonacciNode new_node = new FibonacciNode(vertex);
		numOfMinTrees++;
		if(head == null){
			// Heap is empty so make new node as the new min
			head = new_node;
			head.left = head;
			head.right = head;
			System.out.println("Node with vertex id: "+vertex.id+"	and distance: "+vertex.distance+"  inserted as head");
			return;
		}
		else{
			// Inserting to the left of the head
			head.left.right = new_node;
			new_node.left = head.left;
			head.left = new_node;
			new_node.right = head;
			System.out.println("Node with vertex id: "+vertex.id+"	and distance: "+vertex.distance+"  inserted to the left of head");			
		}
		// Update the new min
		if(new_node.vertex.distance < head.vertex.distance){
			head = new_node;
		}	
		return;
	}

	public GraphNode removeMin(){
		if(head == null){
			// Heap is empty
			System.out.println("removeMin failed: Heap is empty");
			return -1;
		}
		else if(head.right == head){
			// Single min tree
			GraphNode min = head.vertex;
			//numOfMinTrees = head.degree;
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
			int min = head.vertex.distance;
			FibonacciNode min_node = head;

		}
	}

	public void meld(FibonacciNode heap_1, FibonacciNode heap_2){
		// melds heap_2 into heap_1
		// appends heap_2 to the right of the min of heap_1
		
		if(heap_1 == null && heap_2 == null){
			System.out.println("Melding: two empty trees");
			return;
		}
		else if(heap_1 == null && heap_2 !=null){
			heap_1 = heap_2;
			System.out.println("Melding: empty heap_1 with heap_2");
			return;
		}
		else if(heap_1 != null && heap_2 ==null){
			System.out.println("Melding: heap_1 with empty heap_2");
			return;
		}
		else{
			FibonacciNode min = (heap_1.vertex.distance < heap_2.vertex.distance)?heap_1:heap_2;
			heap_1.right.left = heap_2.left;
			heap_2.left.right = heap_1.right;
			heap_1.right = heap_2;
			heap_2.left = heap_1;
			heap_1 = min;	
			System.out.println("Melding: heap_1 with heap_2 and new min is:" + heap_1.vertex.distance);
			return;
		}		
	}

	public void remove(int id){
		if(head == null){
			System.out.println("Remove failed: Heap is empty");
			return;
		}
		if(head.vertex.id == id){
			System.out.println("Remove: Item requested to be removed id: "+id+"  is the min. Calling Removemin");
			removeMin();
			return;
		}
		//find the node with id and remove it and all its parents with childcut value of true;
		FibonacciNode node_to_remove = find(id);
		if(node_to_remove.child == null){
			// No children to add to min tree collection
			System.out.println("Removed node with id: "+id+"  : and this node has no children");
			removeFromParentAndSiblingList(node_to_remove);				
			return;
		}
		else{
			// node had children who have to be added to min tree
			FibonacciNode child_node = node_to_remove.child;
			removeFromParentAndSiblingList(node_to_remove);
			node_to_remove.child = null;
			FibonacciNode cur = child_node.right;
			cur.childCut = false;
			cur.parent = null;
			while(cur != child_node){
				cur.childCut = false;
				cur.parent = null;
				cur = cur.right;
			}
			meld(head,child_node);	
		}
	}

	public void removeFromParentAndSiblingList(FibonacciNode node){
		// Removes the node from sibiling list and updates the node's parent's child pointer if neccessary
		if(node.right == node){
			// Sibling list has only one node
			if(node.parent !=null){
				node.parent.child = null;
				node.parent.degree--;
				if(node.parent.degree < 0){
					System.out.println("Degree of node with node id: "+node.parent.vertex.id+"  is less then 0: ERROR");
				}
				if(node.parent.childCut == true){
					cascadeCut(node.parent);
				}
				else{
					if(node.parent.parent != null)
						node.parent.childCut = true;
				}
				node.parent = null;
				return;
			}			
		}
		else{
			node.left.right = node.right;
			node.right.left = node.left;
			if(node.parent != null){
				node.parent.degree--;
				if(node.parent.degree < 0){
					System.out.println("Degree of node with node id: "+node.parent.vertex.id+"  is less then 0: ERROR");
				}
				if(node.parent.child == node){
					node.parent.child = node.right;
				}
				if(node.parent.childCut == true){
					cascadeCut(node.parent);
				}
				else{
					if(node.parent.parent != null)
						node.parent.childCut = true;
				}
			}
			node.left = node;
			node.right = node;
			node.parent = null;
			return;
		} 
	}

	public void cascadeCut(FibonacciNode node){
		// removes tree rooted at node and adds it to the min tree collection
		FibonacciNode parent = node.parent;
		removeFromParentAndSiblingList(node);
		node.childCut = false;
		node.left = node;
		node.right = node;
		node.parent = null;
		meld(head,node);
		if(parent != null){
			if(parent.childCut == false)
				parent.childCut = true;
			else{
				cascadeCut(parent);
			}
		}
	}

	public FibonacciNode find(int id){
		// Returns the node reference of the Fibonacci node containing the vertex id vertex.
		// do a bfs 
		LinkedList<FibonacciNode> queue = new LinkedList<FibonacciNode>();
		// add all the min trees to the queue
		if(head == null){
			System.out.println("find: Node with node id: "+id+"  NOT found as heap is empty");
			return null;
		}
		FibonacciNode cur = head.right;
		queue.add(head);
		while(cur!=head){
			queue.add(cur);
			cur = cur.right;
		}

		while(!queue.isEmpty()){
			FibonacciNode check_node = queue.remove();
			if(check_node.vertex.id == id){
				System.out.println("find: Node with node id: "+id+"  found");
				return check_node;
			}
			else{
				if(check_node.child != null){
					check_node = check_node.child;
					queue.add(check_node);
					cur = check_node.right;
					while(cur != check_node){
						queue.add(cur);
						cur = cur.right;
					}
				}
			}
		}
		System.out.println("find: Node with node id: "+id+"  NOT found, checked the whole heap");
		return null;
	}

	public void decreaseKey(int id, long new_key){
		System.out.println("DecreaseKey called");
		FibonacciNode node = find(id);
		node.vertex.distance = new_key;
		if(node.parent == null){
			// node is one of the min trees			
			head = (node.vertex.distance < head.vertex.distance)?node:head;
			System.out.println("decreaseKey: of id: "+id+" from: "+node.vertex.distance+"   to: "+new_key+"  node is root of a min tree");
			return;
		}
		else{
			// node is a child in a mintree
			if(node.vertex.distance < node.parent.vertex.distance){
				System.out.println("decreaseKey: new key caused inconsistent heap so breaking off at node and triggering cascadeCut");
				removeFromParentAndSiblingList(node);
				meld(head,node);
			}
			System.out.println("decreaseKey: of id: "+id+" from: "+node.vertex.distance+"   to: "+new_key+"  node is not a root of a min tree");
			return;
		}
	}
}
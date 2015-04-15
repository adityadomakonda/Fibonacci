import java.io.*;
import java.lang.*;
import java.util.*;
import java.math.*;

public class FibonacciHeap{
	FibonacciNode head;
	int numOfMinTrees;

	public FibonacciHeap(){
		head = null;
		//System.out.println("FibonacciHeap created");
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
			//System.out.println("Node with vertex id: "+vertex.id+"	and distance: "+vertex.distance+"  inserted as head");
			return;
		}
		else{
			// Inserting to the left of the head
			head.left.right = new_node;
			new_node.left = head.left;
			head.left = new_node;
			new_node.right = head;
			//System.out.println("Node with vertex id: "+vertex.id+"	and distance: "+vertex.distance+"  inserted to the left of head");			
		}
		// Update the new min
		if(new_node.vertex.distance < head.vertex.distance){
			head = new_node;
		}	
		return;
	}

	public GraphNode removeMin(){
		System.out.print(".");
		if(head == null){
			// Heap is empty
			System.out.println("removeMin failed: Heap is empty");
			return null;
		}
		else{
			FibonacciNode min = head;
			FibonacciNode child = head.child;
			if(head != head.right){
				head.left.right = head.right;
				head.right.left = head.left;
				head = head.right;
				//updateMin();
			}
			else{
				head = null;
			}
			// change childs parent to nulls
			if(child == null){
				updateMin();
				//System.out.println("removeMin: removed vertex with id: "+min.vertex.id+"   distance: "+min.vertex.distance);
				return min.vertex;
			}
			FibonacciNode cur = child.right;
			child.parent = null;
			while(cur != child){
				cur.parent = null;
				cur = cur.right;
			}
			meld(head,child);
			pairWiseMerge();
			updateMin();
			//System.out.println("removeMin: removed vertex with id: "+min.vertex.id);
			return min.vertex;
		}
	}

	public void updateMin(){
		if(head == null){
			return;
		}
		if(head != head.right){
			FibonacciNode start = head;
			FibonacciNode cur = head.right;
			while(cur != start){
				if(cur.vertex.distance < head.vertex.distance){
					head = cur;
				}
				cur = cur.right;
			}
			//System.out.println("updateMin: New min set : "+head.vertex.distance);
			return;
		}
		else{
			return;
		}

	}

	public void pairWiseMerge(){
		long count = 0;
		HashMap<Integer,FibonacciNode> degree_table = new HashMap<Integer,FibonacciNode>();
		LinkedList<FibonacciNode> queue = new LinkedList<FibonacciNode>();
		FibonacciNode cur = head.right;
		queue.add(head);
		while(cur != head){
			queue.add(cur);
			cur = cur.right;
		}
		while(!queue.isEmpty()){
			FibonacciNode cur_min_tree = queue.remove();
			cur_min_tree.childCut = false;
			if(!degree_table.containsKey(cur_min_tree.degree)){
				degree_table.put(cur_min_tree.degree, cur_min_tree);
			}
			else {
				FibonacciNode tree_with_same_degree = degree_table.get(cur_min_tree.degree);
				degree_table.remove(cur_min_tree.degree);
				FibonacciNode merged_tree = mergeTrees(cur_min_tree,tree_with_same_degree);
				count++;
				queue.add(merged_tree);
				degree_table.put(merged_tree.degree,merged_tree);
			}
		}
		System.out.println("Pairwise Merged with  "+count+"   merges");
		return;
	}

	public FibonacciNode mergeTrees(FibonacciNode tree_1, FibonacciNode tree_2){
		// merges tree_1 with tree_2
		// update head
		//make tree_1 have the tree with smalles min
		if(tree_2.vertex.distance < tree_1.vertex.distance){
			// swap to make tree_1 smallest
			FibonacciNode temp = tree_1;
			tree_1 = tree_2;
			tree_2 = temp;
		}
		// make tree_2 a subtree of tree_1
		if(tree_1.child == null){
			// tree_1 has no children
			tree_1.child = tree_2;
			tree_2.parent = tree_1;
			tree_2.left = tree_2;
			tree_2.right = tree_2;
			tree_1.degree++;
		}
		else{
			// merge tree_1 child list with tree_2
			tree_2.parent = tree_1;
			tree_2.right = tree_1.child;
			tree_2.left = tree_1.child.left;
			tree_2.left.right = tree_2;
			tree_1.child.left = tree_2;
			tree_1.degree++;
		}
		head = tree_1;
		return tree_1;
	}

	public void meld(FibonacciNode heap_1, FibonacciNode heap_2){
		// melds heap_2 into heap_1
		// appends heap_2 to the right of the min of heap_1
		
		if(heap_1 == null && heap_2 == null){
			//System.out.println("Melding: two empty trees");
			updateMin();
			return;
		}
		else if(heap_1 == null && heap_2 !=null){
			heap_1 = heap_2;
			//System.out.println("Melding: empty heap_1 with heap_2");
			updateMin();
			return;
		}
		else if(heap_1 != null && heap_2 ==null){
			System.out.println("Melding: heap_1 with empty heap_2");
			updateMin();
			return;
		}
		else{
			FibonacciNode min = (heap_1.vertex.distance < heap_2.vertex.distance)?heap_1:heap_2;
			heap_1.right.left = heap_2.left;
			heap_2.left.right = heap_1.right;
			heap_1.right = heap_2;
			heap_2.left = heap_1;
			heap_1 = min;	
			//System.out.println("Melding: heap_1 with heap_2 and new min is:" + heap_1.vertex.distance);
			updateMin();
			return;
		}		
	}

	public void remove(int id){
		if(head == null){
			System.out.println("Remove failed: Heap is empty");
			return;
		}
		if(head.vertex.id == id){
			//System.out.println("Remove: Item requested to be removed id: "+id+"  is the min. Calling Removemin");
			removeMin();
			return;
		}
		//find the node with id and remove it and all its parents with childcut value of true;
		FibonacciNode node_to_remove = find(id);
		if(node_to_remove.child == null){
			// No children to add to min tree collection
			//System.out.println("Removed node with id: "+id+"  : and this node has no children");
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
			else{
			// nothing to do for this case
				head = null;
				return;
			}			
		}
		else{
			node.left.right = node.right;
			node.right.left = node.left;
			if(node == head){
				// since head is being remove we have to update the min;
				
				FibonacciNode start = head.right;
				FibonacciNode temp = start;
				head = head.right;
				temp = temp.right;
				while(temp != start){
					if(temp.vertex.distance < head.vertex.distance){
						head = temp;
					}
					temp = temp.right;
				}
				//System.out.println("New min set as previous head was removed. New min: "+head.vertex.distance);
			}
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
				//System.out.println("find: Node with node id: "+id+"  found");
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
		//System.out.println("DecreaseKey called");
		FibonacciNode node = find(id);
		long old_val = node.vertex.distance;
		node.vertex.distance = new_key;
		if(node.parent == null){
			// node is one of the min trees			
			head = (node.vertex.distance < head.vertex.distance)?node:head;
			//System.out.println("decreaseKey: of id: "+id+" from: "+old_val+"   to: "+new_key+"  node is root of a min tree");
			return;
		}
		else{
			// node is a child in a mintree
			if(node.vertex.distance < node.parent.vertex.distance){
				System.out.println("decreaseKey: new key caused inconsistent heap so breaking off at node and triggering cascadeCut");
				removeFromParentAndSiblingList(node);
				meld(head,node);
				updateMin();
			}
			//System.out.println("decreaseKey: of id: "+id+" from: "+old_val+"   to: "+new_key+"  node is not a root of a min tree");
			return;
		}
	}
}
import java.io.*;
import java.lang.*;
import java.util.*;
import java.math.*;

public class Trie{
	TrieNode root;
	public Trie(){
		root = null;
	}

	public int find(String destination){
		TrieNode leaf = root;
		TrieNode parent = null;
		while(leaf.value == -1){
			//System.out.println(".  .");
			parent = leaf;
			leaf = (destination.charAt(leaf.bit_number) == '0')?leaf.left:leaf.right;
		}
		//System.out.println("next hop: "+leaf.value+"  longest prefix match: "+destination.substring(0,parent.bit_number+1));
		System.out.print(destination.substring(0,parent.bit_number+1)+" ");
		return leaf.value;
	}

	public void insert(String destination, int next_hop){
		if(root == null){
			root = new TrieNode(destination,next_hop);
			//System.out.println("inserted as root of trie");
		}
		else{
			TrieNode leaf = root;
			TrieNode parent = null;
			while(leaf.bit_number != -1){
				parent = leaf;
				leaf = (destination.charAt(leaf.bit_number) == '0')?leaf.left:leaf.right;
			}
			int shared_prefix_length = prefix_length(destination,leaf.key);
			//System.out.println("shared_prefix_length: "+shared_prefix_length +"  for: "+destination+"  and  "+leaf.key);
			// This has three cases
			// case 1 where shared_prefix_length is less than root bit_number or root.bit_number is -1
			// case 2 where shared_prefix_length is less than parent.bit_number but greater than root.bit_number
			// case 3 where shared_prefix_length  is greater than parent.bit_number;
			if(parent == null || root.bit_number > shared_prefix_length){
				//case 1 where shared_prefix_length is less than root bit_number or parent is null. Trie with one node
				TrieNode new_node = new TrieNode(destination,next_hop);
				TrieNode new_root = new TrieNode(shared_prefix_length);
				if(destination.charAt(shared_prefix_length) == '0'){
					new_root.left = new_node;
					new_root.right = root;
					root = new_root;
				}
				else{
					new_root.left = root;
					new_root.right = new_node;
					root = new_root;	
				}
				//System.out.println("Case I insert");
			}
			else if(shared_prefix_length > root.bit_number && shared_prefix_length < parent.bit_number){
				// case 2 where shared_prefix_length is less than parent.bit_number but greater than root.bit_number
				TrieNode small_split_node = root;
				TrieNode big_split_node = (destination.charAt(small_split_node.bit_number) == '0')?small_split_node.left:small_split_node.right;
				while(!(shared_prefix_length > small_split_node.bit_number && shared_prefix_length < big_split_node.bit_number)){
					small_split_node = big_split_node;
					big_split_node = (destination.charAt(small_split_node.bit_number) == '0')?small_split_node.left:small_split_node.right;
				}
				TrieNode new_leaf_node = new TrieNode(destination,next_hop);
				TrieNode new_split_node = new TrieNode(shared_prefix_length);
				if(destination.charAt(small_split_node.bit_number) == '0'){					
					small_split_node.left = new_split_node;
					if(destination.charAt(new_split_node.bit_number) == '0'){
						new_split_node.left = new_leaf_node;
						new_split_node.right = big_split_node;
					}
					else{
						new_split_node.left = big_split_node;
						new_split_node.right = new_leaf_node;
					}

				}
				else{
					small_split_node.right = new_split_node;
					if(destination.charAt(new_split_node.bit_number) == '0'){
						new_split_node.left = new_leaf_node;
						new_split_node.right = big_split_node;
					}
					else{
						new_split_node.left = big_split_node;
						new_split_node.right = new_leaf_node;
					}	
				}
				//System.out.println("Case II insert");
			}
			else if(shared_prefix_length > parent.bit_number){
				// case 3 where shared_prefix_length  is greater than parent.bit_number;
				TrieNode new_leaf_node = new TrieNode(destination,next_hop);
				TrieNode new_split_node = new TrieNode(shared_prefix_length);
				if(destination.charAt(parent.bit_number) == '0'){					
					parent.left = new_split_node;
					if(destination.charAt(new_split_node.bit_number) == '0'){
						new_split_node.left = new_leaf_node;
						new_split_node.right = leaf;
					}
					else{
						new_split_node.left = leaf;
						new_split_node.right = new_leaf_node;
					}

				}
				else{
					parent.right = new_split_node;
					if(destination.charAt(new_split_node.bit_number) == '0'){
						new_split_node.left = new_leaf_node;
						new_split_node.right = leaf;
					}
					else{
						new_split_node.left = leaf;
						new_split_node.right = new_leaf_node;
					}	
				}
				//System.out.println("Case III");	
			}
			else{
				System.out.println("Error case insert trie");
			}


		}
	}

	public int prefix_length(String key1, String key2){
		int length = 0;
		for(int i=0 ; i<key1.length();i++){
			if(key1.charAt(i) == key2.charAt(i)){
				length++;
			}
			else{
				break;
			}
		}
		return length;
	}

	public void prune(){
		prune(root);
		//System.out.println("Pruning done");
	}

	public void prune(TrieNode root){
		if(root == null)
			System.out.println("ERROR in prune");
		if(root.left == null && root.right == null)
			return;
		prune(root.left);
		prune(root.right);
		if(root.left.value == root.right.value && root.left.value != -1 && root.right.value != -1){
			root.value = root.left.value;
			root.left = null;
			root.right = null;
		}
	}

}
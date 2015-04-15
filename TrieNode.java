import java.io.*;
import java.lang.*;
import java.util.*;
import java.math.*;

public class TrieNode{
	//TrieNode parent;
	TrieNode left; // 0 split
	TrieNode right;// 1 split
	int bit_number;// will be indexed from 0 to 31 and ewual to -1 if it is a leaf node
	String key; // Destination IP in 1's and 0's
	int value; // Next hop vertex id; and -1 for internal nodes

	public TrieNode(int bit_number){
		left = null;
		right = null;
		this.bit_number = bit_number;
		value = -1;
		key = null;
	}

	public TrieNode(String key,int value){
		left = null;
		right = null;
		bit_number = -1;
		this.value = value;
		this.key = key;
		//System.out.println("New Trienode Key: "+key+"    Value:"+value);
	}
}
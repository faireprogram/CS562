package org.stevens.cs562.utils.btree;

import java.util.ArrayList;
import java.util.List;

import org.stevens.cs562.utils.btree.BTreeNode.KeyNode;


/*
 * Used for ENTRIES OPTIMIZATION
 */
public class BTreePlus<T> {

	private BTreeNode<T> root;
	
	private int branch_num; // fan_out_factor
	
	public BTreePlus(int branch_num) {
		this.branch_num = branch_num;
	}
	
	
	/**
	 * used public add value
	 */
	public void add(T value) {
		KeyNode<T> keyNode = null;
		if(root == null) {
			root = new BTreeNode<T>(branch_num);
			keyNode = new KeyNode<T>(value, root);
			root.add(keyNode);
			return;
		}
		
		
		BTreeNode<T> leafNode = traveseByBTreeNode(root, value.hashCode());
		keyNode = new KeyNode<T>(value, leafNode); // default use root as 
		
		/**
		 * recursive add the node
		 */
		if(leafNode.getIndicator() == null) {
			add(keyNode, leafNode, null);
			return;
		} else {
			add(keyNode, leafNode, leafNode.getIndicator().getBelongTo());
			return;
		}
	}
	
	/**
	 * add keyNode to btreenode 
	 */
	private void add(KeyNode<T> keyNode, BTreeNode<T> btreeNode, BTreeNode<T> paretNode) {
	  
	  if(keyNode.getBelongTo().isLeaf()) {
		  addLeaf(keyNode, btreeNode, paretNode);
	  } else {
		  addNonLeaf(keyNode, btreeNode, paretNode);
	  }
	}
	
	/**
	 * btreeNode should be nonleaf
	 */
	private void addNonLeaf(KeyNode<T> keyNode, BTreeNode<T> btreeNode, BTreeNode<T> paretNode) {
		// if the NonleafNode have enough room
	    if(btreeNode.getCurrent_size() < this.branch_num) {
	    	btreeNode.add(keyNode);
	    	return;
	    }
	    
	    //1: else split the leafNode
	    List<BTreeNode<T>> splits = btreeNode.splitNode(keyNode);
	    
	    //2: push the node
	    KeyNode<T> pushup_node = splits.get(1).getHead();
	    
	    //3: reset the relation
	    BTreeNode<T> left_edge = splits.get(0).getTail().getRight();
	    BTreeNode<T> right_edge =  splits.get(1).getHead().getRight(); 
	    
	    pushup_node.setLeft(splits.get(0));
	    pushup_node.setRight(splits.get(1));
	    splits.get(0).setIndicator(pushup_node);
	    splits.get(1).setIndicator(pushup_node); //bind relation builder

	    splits.get(1).removeFromBegin();
	    
	    left_edge.setIndicator(splits.get(0).getTail());
	    right_edge.setIndicator(splits.get(1).getHead());
	    
	    if(paretNode == null) { // it's a root
	    	paretNode = new BTreeNode<T>(branch_num);
	    	root = paretNode;
	    }
    	pushup_node.setBelongTo(paretNode);
    	
    	pushup_node.setNext(null);
    	pushup_node.setPrevious(null);
	    
	    //3: add push up node to parent
	    BTreeNode<T> grandNode = null;
	    if(paretNode.getIndicator() != null) {
	 		   grandNode = paretNode.getIndicator().getBelongTo();
		    }
	    addNonLeaf(pushup_node, paretNode, grandNode);
	}
	
	private void addLeaf(KeyNode<T> keyNode, BTreeNode<T> btreeNode, BTreeNode<T> paretNode) {
		  // if the leafNode have enough room
	    if(btreeNode.getCurrent_size() < this.branch_num) {
	    	btreeNode.add(keyNode);
	    	return;
	    }
		
	    //1: else split the leafNode
	    List<BTreeNode<T>> splits = btreeNode.splitNode(keyNode);
	    //2: building the reference between them  :push up change
	    KeyNode<T> pushup_node = null;
	    
	    if(paretNode == null) { // it's a root
	    	paretNode = new BTreeNode<T>(branch_num);
	    	root = paretNode;
	    }
    	pushup_node = new KeyNode<T>( splits.get(1).getHead().getValue(), paretNode);
    	
    	//splits.get(0).getTail().setL
    	pushup_node.setLeft(splits.get(0));
	    pushup_node.setRight(splits.get(1));
	    splits.get(0).setIndicator(pushup_node);
	    splits.get(1).setIndicator(pushup_node); //bind relation builder
	    
	    //3: add push up node to parent
	    BTreeNode<T> grandNode = null;
	    if(paretNode.getIndicator() != null) {
	    	grandNode = paretNode.getIndicator().getBelongTo();
	    }
	    addNonLeaf(pushup_node, paretNode, grandNode);
	}
	
	/**
	 * @param value
	 * @return
	 */
	public T get(T value) {
		BTreeNode<T> leafNode = traveseByBTreeNode(root, value.hashCode());
		return leafNode.get(value);
	}
	

	/*
	 * use recursive method to traverse vertically the tree node
	 */
	private BTreeNode<T> traveseByBTreeNode(BTreeNode<T> treeBlockNode, int hash_code) {
		/*
		 * #need to implementation binary search
		 */
		KeyNode<T> current_cusor = treeBlockNode.getHead();
		if(current_cusor.getLeft() == null && current_cusor.getRight() == null) {
			return treeBlockNode;
		}
		BTreeNode<T> result = null;
		while(current_cusor != null) {
			/*
			 * if hash_code < Current Key Node value, then go to left
			 */
			if(hash_code < current_cusor.getHash_code()) {
				result = traveseByBTreeNode(current_cusor.getLeft(), hash_code);
				break;
			}
			/*
			 * if current_cusor is in the end in the BTreeNode
			 */
			if(current_cusor.getNext() == null) {
				result = traveseByBTreeNode(current_cusor.getRight(), hash_code);
				break;
			}
			/*
			 * if hash_code >= Current Key Node value, move the cursor to right
			 */
			if(hash_code >= current_cusor.getHash_code()) {
				current_cusor = current_cusor.getNext();
			}
		}
		return result;
	}
	
	public void analysis() {
		List<List<BTreeNode<T>>> queue = new ArrayList<List<BTreeNode<T>>>();
		List<BTreeNode<T>> root_list = new ArrayList<BTreeNode<T>>();
		root_list.add(root);
		queue.add(root_list);
		
		traverseTree(queue, 0);
		
		int sum =0;
		for(int i =0; i < queue.size(); i ++) {
			String s = printT( queue.size() - i);
			System.out.print(s);
			System.out.println(queue.get(i));
		}
		for(int i =0; i < queue.get(queue.size()-1).size(); i ++) {
			sum += queue.get(queue.size()-1).get(i).getCurrent_size();
		}
		System.out.println("The Sum amount is :  " + sum);
	}
	
	private String printT(int n) {
		String s ="";
		for(int i =0; i < n; i++) {
			s += "\t";
		}
		return s;
	}
	
	private void traverseTree(List<List<BTreeNode<T>>> queue, int index) {
//		KeyNode<T> current_cusor = treeBlockNode.getHead();
//		if(current_cusor.getLeft() == null && current_cusor.getRight() == null) {
//			System.out.println(current_cusor.getBelongTo());
//			return;
//		}
//		
		List<BTreeNode<T>> current_cur = queue.get(index);
		List<BTreeNode<T>> new_cur = new ArrayList<BTreeNode<T>>();
		for(int i =0; i < current_cur.size(); i++) {
			
			KeyNode<T> cur_keynode = current_cur.get(i).getHead();
			if(cur_keynode.getLeft() != null) {
				while(cur_keynode != null) {
					new_cur.add(cur_keynode.getLeft());
					
					cur_keynode = cur_keynode.getNext();
				}
				new_cur.add(current_cur.get(i).getTail().getRight());
			}
			
		}

		if(new_cur.size() != 0) {
			queue.add(new_cur);
		} else {
			return;
		}
		index++;
		traverseTree(queue, index);
	}
}

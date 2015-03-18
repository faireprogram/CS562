package org.stevens.cs562.utils.btree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author faire_000
 * 
 * @param <T>
 */
// one BTreeNode is a block
public class BTreeNode<T> implements Cloneable {

	// # ------------------- Inner List
	private KeyNode<T> head;

	private KeyNode<T> tail;
	
	private int current_size = 0;
	
	private int branch_num;
	

	// # ------------------- Outter List
	/**
	 * indicator means the KEYNODE<T> m which have the pointer to the BTreeNode itself;
	 */
	private KeyNode<T> indicator;
	

	public BTreeNode(int branch_num) { 
		this.branch_num = branch_num;
	}

	public static class KeyNode<T> {
		
		/**
		 * @param value
		 */
		public KeyNode(T value, BTreeNode<T> belongTo) {
			super();
			this.setValue(value);
			this.hash_code = value.hashCode();
			this.belongTo = belongTo;
		}
		
		
		
		public KeyNode(BTreeNode<T> belongTo) {
			super();
			hash_code = -1;
			this.belongTo = belongTo;
		}

		/**
		 * hash_code
		 */
		private int hash_code;

		/**
		 * 
		 */
		private KeyNode<T> next;

		/**
		 * 
		 */
		private KeyNode<T> previous;

		/**
		 * 
		 */
		T value;

		/**
		 * 
		 */
		private BTreeNode<T> left;

		/**
		 * right
		 */
		private BTreeNode<T> right;
		
		/**
		 * belongTo
		 */
		private BTreeNode<T> belongTo;

		/**
		 * @return the next
		 */
		public KeyNode<T> getNext() {
			return next;
		}

		/**
		 * @param next the next to set
		 */
		public void setNext(KeyNode<T> next) {
			this.next = next;
		}

		/**
		 * @return the previous
		 */
		public KeyNode<T> getPrevious() {
			return previous;
		}

		/**
		 * @param previous the previous to set
		 */
		public void setPrevious(KeyNode<T> previous) {
			this.previous = previous;
		}

		/**
		 * @return the value
		 */
		public T getValue() {
			return value;
		}

		/**
		 * @param value the value to set
		 */
		public void setValue(T value) {
			this.value = value;
		}

		/**
		 * @return the left
		 */
		public BTreeNode<T> getLeft() {
			return left;
		}

		/**
		 * @param left the left to set
		 */
		public void setLeft(BTreeNode<T> left) {
			this.left = left;
		}

		/**
		 * @return the right
		 */
		public BTreeNode<T> getRight() {
			return right;
		}

		/**
		 * @param right the right to set
		 */
		public void setRight(BTreeNode<T> right) {
			this.right = right;
		}

		/**
		 * @return the hash_code
		 */
		public int getHash_code() {
			return hash_code;
		}



		/**
		 * @return the belongTo
		 */
		public BTreeNode<T> getBelongTo() {
			return belongTo;
		}


		/**
		 * @param belongTo the belongTo to set
		 */
		public void setBelongTo(BTreeNode<T> belongTo) {
			this.belongTo = belongTo;
		}



		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return value.toString();
		}
		
		
		

	}

	/**
	 * @return the head
	 */
	public KeyNode<T> getHead() {
		return head;
	}

	/**
	 * @param head
	 *            the head to set
	 */
	public void setHead(KeyNode<T> head) {
		this.head = head;
	}

	/**
	 * @return the tail
	 */
	public  KeyNode<T> getTail() {
		return tail;
	}

	/**
	 * @param tail
	 *            the tail to set
	 */
	public void setTail(KeyNode<T> tail) {
		this.tail = tail;
	}

	/**
	 * @return the indicator
	 */
	public KeyNode<T> getIndicator() {
		return indicator;
	}

	/**
	 * @param indicator the indicator to set
	 */
	public void setIndicator(KeyNode<T> indicator) {
		this.indicator = indicator;
	}
	
	public void add(KeyNode<T> newKeyNode) {
		if(this.tail == null) {
			this.head = this.tail = newKeyNode;
			this.current_size ++;
			return;
		}
		sortAddNode(newKeyNode);
		this.current_size ++;
	}
	
	/**
	 * Input in the NewKeyNode,
	 * Sort it from the list and add
	 */
	private void sortAddNode(KeyNode<T> newKeyNode) {
		KeyNode<T> current_cursor = this.head;
		while(current_cursor != null) {
			if(newKeyNode.getHash_code() < current_cursor.getHash_code()) {
				KeyNode<T> previous_Node = current_cursor.getPrevious();
				KeyNode<T> later_Node = current_cursor;
				
				newKeyNode.previous = previous_Node;
				newKeyNode.next = later_Node;
				
				if(previous_Node != null) {// not the first 
					previous_Node.next = newKeyNode;
					later_Node.previous = newKeyNode;
					
					//build the pointer relation
					previous_Node.right = newKeyNode.left;
					later_Node.left = newKeyNode.right;
						
				} else { // the first node 
					later_Node.previous = newKeyNode;
					this.head = newKeyNode;
					
					//build the pointer relation
					later_Node.left = newKeyNode.right;
				}
				break;
			}
			
			// the last one, but still not find the less one
			if(current_cursor.next == null) {
				addToTail(newKeyNode);
				break;
			}
			
			// move the cursor if great than
			if(newKeyNode.getHash_code() >= current_cursor.getHash_code()) {
				current_cursor = current_cursor.next;
			}
		}
	}
	
	private void addToTail(KeyNode<T> newKeyNode) {
		if(this.tail == null) {
			this.head = this.tail = newKeyNode;
			return;
		}
		// build bidconnection
		this.tail.next = newKeyNode;
		newKeyNode.previous = this.tail;
		
		// build 
		this.tail.right = newKeyNode.left;
		this.tail = newKeyNode;
	}
	
	public List<BTreeNode<T>> splitNode(KeyNode<T> newKeyNode) {
		List<BTreeNode<T>> splits = new ArrayList<BTreeNode<T>>();
		BTreeNode<T> left_split = new BTreeNode<T>(this.branch_num);
		BTreeNode<T> right_split = new BTreeNode<T>(this.branch_num);
		splits.add(left_split);
		splits.add(right_split);
		
		left_split.setIndicator(this.indicator);
		right_split.setIndicator(this.indicator);
		
		//need to split this lists
		sortAddNode(newKeyNode);
		
		int i = 0;
		KeyNode<T> cursorl = this.head;
		KeyNode<T> cursorr = null;
		while(cursorl != null || cursorr != null) {
			if(i < divide(this.current_size)) {
				if( i == (divide(this.current_size)) - 1) {
					cursorr = cursorl.next;
					cursorl.next = null;
				}
				left_split.addToTail(cursorl);
				left_split.current_size ++;
				cursorl.setBelongTo(left_split);
				if(cursorl != null) {
					cursorl = cursorl.next;
				}
			} else {
				if( i == divide(this.current_size)) {
					cursorr.previous = null;
				}
				right_split.addToTail(cursorr);
				right_split.current_size ++;
				cursorr.setBelongTo(right_split);
				if(cursorr != null) {
					cursorr = cursorr.next;
				}
			}
			i++;
		}
		return splits;
	}
	
	private int divide(int bran) {
		if(bran % 2 == 1) {
			return (bran + 1)/2;
		}
		return (bran + 1)/2;
	}
	
	public T get(T value) {
		KeyNode<T> current_cur = this.head;
		while(current_cur != null) {
			if(current_cur.getValue().equals(value)) {
				return current_cur.getValue();
			}
			current_cur = current_cur.next;
		}
		return null;
	}

	/**
	 * @return the current_size
	 */
	public int getCurrent_size() {
		return current_size;
	}

	/**
	 * @return the branch_num
	 */
	public int getBranch_num() {
		return branch_num;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		KeyNode<T> current_cursor = this.head;
		String str = " {";
		while(current_cursor != null) {
			str += current_cursor.toString() + " ";
			current_cursor = current_cursor.next;
		}
		str += "}";
		if(this.indicator != null) {
			str = "[" + this.indicator.getValue() +"]=>" + str;
		} else {
			str = "[root]=>" + str;
		}
		return str;
	}
	
	

	/**
	 * @return the type
	 */
	public boolean isLeaf() {
		if(this.getHead().getLeft() == null && this.getHead().getRight() == null)
			return true; 
		else 
			return false;
	}
	
	public void removeFromBegin() {
		if(this.head != null) {
			this.head = this.head.next;
			 this.head.previous = null;
		}
		this.current_size--;
	}
	
	
	
	
	
	
}

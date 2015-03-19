package org.stevens.cs562.utils.graph;

import java.util.AbstractList;

/**
 * @author faire_000
 *
 */
public class AdjacentList<K, T extends AdjacentNode<K>>  extends AbstractList<T>{

	private AdjacentNode<K> head;
	private AdjacentNode<K> rear;
	private int length;
	
	
	
	public AdjacentList() {
		super();
		length = -1;
		head = rear = null;
	}

	@Override
	public T get(int index) {
		if(index > length || index < 0) {
			return null;
		}
		return findNode(index);
	}

	public AdjacentNode<K> get(K node) {
		return findNode(node);
	}
	
	@Override
	public int size() {		
		return (length + 1);
	}
	
	@Override
	public boolean add(T e) {
		if(head == null) {
			head = e;
			rear = head;
		} else {
			rear.setNextInDegree(e);
			rear = e;
		}
		length = length + 1;
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T remove(int index) {
		if(index > length || index < 0) {
			return null;
		}
		T removenode;
		if(index == 0) {
			removenode = (T)head;
			head = head.getNextInDegree();
		} else {
			T prevNode = get(index-1);
			removenode = (T)prevNode.getNextInDegree();
			T lateNode = (T)prevNode.getNextInDegree().getNextInDegree();
			prevNode.setNextInDegree(lateNode);
		}
		length = length -1;
		return removenode;
	}
	
	private AdjacentNode<K> findNode(K node) {
		AdjacentNode<K> tmp = head;
		while(tmp != null) {
			if(tmp.getValue().equals(node)) {
				break;
			}
			tmp = tmp.getNextInDegree();
		}
		return tmp;
	}

	@SuppressWarnings("unchecked")
	private T findNode(int num) {
		AdjacentNode<K> tmp = head;
		int i = 0;
		while(tmp != null) {
			if(num == i) {
				break;
			}
			tmp = tmp.getNextInDegree();
			i++;
		}
		return (T)tmp;
	}

}

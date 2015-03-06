package org.stevens.cs562.utils.graph;

import java.util.Collection;

public class NullObject<T> implements AdjacentNode<T>{

	public AdjacentNode<T> getCurrentInDegree() {
		return null;
	}

	public AdjacentNode<T> getNextInDegree() {
		return this;
	}

	public void setNextInDegree(AdjacentNode<T> node) {
	}

	public Collection<AdjacentNode<T>> getEdgeVetex() {
		return null;
	}

	public T getValue() {
		return null;
	}

	public T setValue() {
		return null;
	}

	public int getCurrentConnectionNum() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setCurrentConnectionNum(int currentConnectionNum) {
		// TODO Auto-generated method stub
		
	}

	public void setEdgeVetex(Collection<AdjacentNode<T>> edgeVetex) {
		// TODO Auto-generated method stub
		
	}

}

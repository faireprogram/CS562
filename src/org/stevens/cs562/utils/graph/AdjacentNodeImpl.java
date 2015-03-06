package org.stevens.cs562.utils.graph;

import java.util.ArrayList;
import java.util.Collection;

public class AdjacentNodeImpl<T> implements AdjacentNode<T>{

	/**
	 * next
	 */
	private AdjacentNode<T> next = null;
	
	/**
	 * 
	 */
	private T value;
	
	/**
	 * edgeVetex
	 */
	private Collection<AdjacentNode<T>> edgeVetex = new ArrayList<AdjacentNode<T>>();

	/**
	 * @param next
	 * @param value
	 */
	public AdjacentNodeImpl(AdjacentNode<T> next, T value) {
		super();
		this.next = next;
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see org.stevens.cs562.utils.graph.AdjacentNode#getCurrentInDegree()
	 */
	public AdjacentNode<T> getCurrentInDegree() {
		return this;
	}

	/* (non-Javadoc)
	 * @see org.stevens.cs562.utils.graph.AdjacentNode#getNextInDegree()
	 */
	public AdjacentNode<T> getNextInDegree() {
		return next;
	}

	/* (non-Javadoc)
	 * A->B
	 * A->C
	 * return {B,C}
	 */
	public Collection<AdjacentNode<T>> getEdgeVetex() {
		return edgeVetex;
	}

	/* (non-Javadoc)
	 * @see org.stevens.cs562.utils.graph.AdjacentNode#setNextInDegree(org.stevens.cs562.utils.graph.AdjacentNode)
	 */
	public void setNextInDegree(AdjacentNode<T> node) {
		this.next = node;
	}

	/**
	 * @return
	 */
	public T getValue() {
		return value;
	}

	/**
	 * @param value
	 */
	public void setValue(T value) {
		this.value = value;
	}

	public T setValue() {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see org.stevens.cs562.utils.graph.AdjacentNode#setEdgeVetex(java.util.Collection)
	 */
	public void setEdgeVetex(Collection<AdjacentNode<T>> edgeVetex) {
		this.edgeVetex = edgeVetex;
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result;
		result = prime * result
				+ ((edgeVetex == null) ? 0 : edgeVetex.hashCode());
		result = prime * result + ((next == null) ? 0 : next.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

}

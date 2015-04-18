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
		int result = this.getValue().hashCode() * 31;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj instanceof AdjacentNodeImpl) {
			@SuppressWarnings("unchecked")
			AdjacentNode<T> t_val = (AdjacentNodeImpl<T>)obj;
			if(this.value.equals(t_val.getValue())) {
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getValue().toString();
	}
	
	

}

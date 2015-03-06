package org.stevens.cs562.utils.graph;

import java.util.Collection;

public interface AdjacentNode<T>  {
	
	/**
	 * @return getValue
	 */
	public AdjacentNode<T> getCurrentInDegree();
	
	/**
	 * @return
	 */
	public AdjacentNode<T> getNextInDegree();
	
	/**
	 * @param node
	 * @return
	 */
	public void setNextInDegree(AdjacentNode<T> node);
	
	/**
	 * @return
	 */
	public Collection<AdjacentNode<T>>  getEdgeVetex();
	
	/**
	 * @param edgeVetex
	 */
	public void setEdgeVetex(Collection<AdjacentNode<T>> edgeVetex);
	
	/**
	 * @return
	 */
	public T getValue();
	
	/**
	 * @return
	 */
	public T setValue();
	
}

package org.stevens.cs562.utils.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class TopologicalGraph<K> {

	/**
	 * 
	 */
	AdjacentList<K, AdjacentNode<K>> graph;
	
	public TopologicalGraph(AdjacentList<K, AdjacentNode<K>> graph) {
		super();
		this.graph = graph;
	}



	public  List<Collection<AdjacentNode<K>>> sort() {
		List<Collection<AdjacentNode<K>>> results = new ArrayList<Collection<AdjacentNode<K>>>();
		
		int length = graph.size();
		
		//# initialize the indegree array
		List<IndegreeElement> indegree = new ArrayList<IndegreeElement>(graph.size());
		for(int i = 0; i < length; i++) {
			indegree.add(new IndegreeElement());
		}
		
		for(int i = 0; i < length; i++ ) {
			Collection<AdjacentNode<K>> edgeVetex =  graph.get(i).getEdgeVetex();
			if(edgeVetex.size() == 0) {
				continue;
			}
			modifyConnectionNum(indegree, edgeVetex, 1);
			
		}
		
		//# delete edge recursively
		for(int j = 0; j < length; j++) {
			
			Collection<AdjacentNode<K>> sameLayerElements = new ArrayList<AdjacentNode<K>>();
			List<AdjacentNode<K>> queues = new ArrayList<AdjacentNode<K>>();
			for(int i = 0; i < length; i++) {
				if(indegree.get(i).getConnection() == 0) {
					AdjacentNode<K> node = graph.get(i);
					sameLayerElements.add(node);
					queues.add(node);
				}
			}
			
			for(int i = 0; i < queues.size(); i++) {
				AdjacentNode<K> node = queues.get(i);
				int index = findIndex(node);
				//delete edge
				indegree.get(index).setConnection(-1);
				
				Collection<AdjacentNode<K>> edgeVetex =  node.getEdgeVetex();
				if(edgeVetex == null || edgeVetex.size() == 0) {
					continue;
				}
				modifyConnectionNum(indegree, edgeVetex, -1);
			}
		
			
			if(sameLayerElements.size() != 0) {
				results.add(sameLayerElements);
			} else {
				break;
			}
		}
		
		
		return results;
	}

	private void modifyConnectionNum(
			List<IndegreeElement> indegree,
			Collection<AdjacentNode<K>> edgeVetex, int addValue) {
		Iterator<AdjacentNode<K>> edgeVetexIterator = edgeVetex.iterator();
		/*
		 * A -> B
		 * A -> C
		 * initialize B,C connection = 1
		 */
		while(edgeVetexIterator.hasNext()) {
			AdjacentNode<K> adjacentNode = edgeVetexIterator.next();
			int index = findIndex(adjacentNode);
			IndegreeElement currentInElement = indegree.get(index);
			currentInElement.setConnection(currentInElement.getConnection() + addValue);
		}
	}
	
	/**
	 * @param source
	 * @return
	 */
	private int findIndex(AdjacentNode<K> source) {
		for(int i = 0; i < graph.size(); i++) {
			if(graph.get(i) == source) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * @author faire_000
	 *
	 */
	private class IndegreeElement {
		private Integer connection = 0;

		/**
		 * @return
		 */
		public Integer getConnection() {
			return connection;
		}

		/**
		 * @param connection
		 */
		public void setConnection(Integer connection) {
			this.connection = connection;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return connection.toString();
		}
		
		
		
	};
	
	//public sort
}

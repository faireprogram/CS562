package org.stevens.cs562.utils.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.stevens.cs562.utils.graph.AdjacentList;
import org.stevens.cs562.utils.graph.AdjacentNode;
import org.stevens.cs562.utils.graph.AdjacentNodeImpl;
import org.stevens.cs562.utils.graph.TopologicalGraph;

public class Test_Graph {

	public Test_Graph() {
		// TODO Auto-generated constructor stub
	}

	/*
	 *  CYCLE DEPENDENCY IS NOT SUPPORT
	 *   A -> B -> C
	 *   B -> C
	 *   C -> D
	 *   D
	 */
	private static void test_topological_sort() {
		AdjacentList<String, AdjacentNode<String>> list = new AdjacentList<String, AdjacentNode<String>>();
		AdjacentNode<String> A = new AdjacentNodeImpl<String>(null,"A");
		AdjacentNode<String> B = new AdjacentNodeImpl<String>(null,"B");
		AdjacentNode<String> C = new AdjacentNodeImpl<String>(null,"C");
		AdjacentNode<String> D = new AdjacentNodeImpl<String>(null,"D");
		Collection<AdjacentNode<String>> ARelation = new ArrayList<AdjacentNode<String>>();
		ARelation.add(B);
		ARelation.add(C);
		A.setEdgeVetex(ARelation);
		B.getEdgeVetex().add(C);
		C.getEdgeVetex().add(D);
		list.add(A);
		list.add(B);
		list.add(C);
		list.add(D);
		
		/*        C
		 *   A -> B
		 *   B -> C
		 *   C -> D
		 *   D
		 */
//		list.get(index)
		System.out.println(list.get(0).getValue());
		System.out.println(list.get(1).getValue());
		System.out.println(list.get(2).getValue());
		System.out.println(list.get(3).getValue());
		
		
		TopologicalGraph<String> graph = new TopologicalGraph<String>(list);
		List<Collection<AdjacentNode<String>>> layers = graph.sort();
		for(int i = 0; i < layers.size(); i++) {
			System.out.print("The " + i + " round : " );
			Iterator<AdjacentNode<String>> iter = layers.get(i).iterator();
			while(iter.hasNext()) {
				System.out.print(iter.next().getValue() + "  ");
			}
			System.out.println("");
 		}
		
		A.getEdgeVetex().clear();
		B.getEdgeVetex().clear();
		C.getEdgeVetex().clear();
		D.getEdgeVetex().clear();
		
		/*
		 *   A
		 *   B -> C -> D
		 *   C -> D
		 *   D
		 */
		B.getEdgeVetex().add(C);
		C.getEdgeVetex().add(D);
		
		layers = graph.sort();
		for(int i = 0; i < layers.size(); i++) {
			System.out.print("The " + i + " round : " );
			Iterator<AdjacentNode<String>> iter = layers.get(i).iterator();
			while(iter.hasNext()) {
				System.out.print(iter.next().getValue() + "  ");
			}
			System.out.println("");
 		}
		
		A.getEdgeVetex().clear();
		B.getEdgeVetex().clear();
		C.getEdgeVetex().clear();
		D.getEdgeVetex().clear();
		/*
		 *   A -> C
		 *   B -> C
		 *   C -> D
		 *   D
		 */
		A.getEdgeVetex().add(C);
		B.getEdgeVetex().add(C);
		C.getEdgeVetex().add(D);
		layers = graph.sort();
		for(int i = 0; i < layers.size(); i++) {
			System.out.print("The " + i + " round : " );
			Iterator<AdjacentNode<String>> iter = layers.get(i).iterator();
			while(iter.hasNext()) {
				System.out.print(iter.next().getValue() + "  ");
			}
			System.out.println("");
 		}
		
	}
	
	public static void main(String[] args) {
		test_topological_sort();
	}
}

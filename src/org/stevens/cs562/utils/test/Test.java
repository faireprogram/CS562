package org.stevens.cs562.utils.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.stevens.cs562.sql.sqlimpl.SqlSentence;
import org.stevens.cs562.utils.SQLStringParsers;
import org.stevens.cs562.utils.graph.AdjacentList;
import org.stevens.cs562.utils.graph.AdjacentNode;
import org.stevens.cs562.utils.graph.AdjacentNodeImpl;
import org.stevens.cs562.utils.graph.TopologicalGraph;

public class Test {
	
	
	public static String s = " SELECT ty ,zy,  xz,th "
			+ "from sales C, sales D "
			+ "where C.X='1' "
			+ "group by C.X, D.Y : X, Y "
			+ "suchthat X.start_date < '192/1687/200  and Y.start_data > '899222' "
			+ "having count(X.startdate) > 5 ;";
	
	private static String readLine(String format) throws IOException {
	    
	    System.out.print(String.format(format, null));
	    BufferedReader reader = new BufferedReader(new InputStreamReader(
	            System.in));
	    return reader.readLine();
	}
	
	public static void main(String [ ] args) throws IOException {
//		RelationalAnalysis rs = new RelationalAnalysis();
//		SqlSentence ss = new SqlSentence(Test.s);
//		System.out.println("Hello World211d") ; 
		
		AdjacentList<Integer, AdjacentNode<Integer>> list = new AdjacentList<Integer, AdjacentNode<Integer>>();
		AdjacentNode<Integer> A = new AdjacentNodeImpl<Integer>(null,1);
		AdjacentNode<Integer> B = new AdjacentNodeImpl<Integer>(null,2);
		AdjacentNode<Integer> C = new AdjacentNodeImpl<Integer>(null,3);
		AdjacentNode<Integer> D = new AdjacentNodeImpl<Integer>(null,4);
		Collection<AdjacentNode<Integer>> ARelation = new ArrayList<AdjacentNode<Integer>>();
		ARelation.add(B);
		ARelation.add(C);
		A.setEdgeVetex(ARelation);
		B.getEdgeVetex().add(C);
		C.getEdgeVetex().add(B);
		list.add(A);
		list.add(B);
		list.add(C);
		list.add(D);
		
//		list.get(index)
		System.out.println(list.get(0).getValue());
		System.out.println(list.get(1).getValue());
		System.out.println(list.get(2).getValue());
		System.out.println(list.get(3).getValue());
		
		
		TopologicalGraph<Integer> graph = new TopologicalGraph<Integer>(list);
		List<Collection<AdjacentNode<Integer>>> layers = graph.sort();
		System.out.println(layers.size());

	}
}

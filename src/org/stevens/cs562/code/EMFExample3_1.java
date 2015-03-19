package org.stevens.cs562.code;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
		select product, month, year,  sum(X.quantity)/sum(Y.quantity)
		from Sales
		group by product, month, year : X, Y
		such that X.product = product and X.month = month and X.year = year and X.quant > 500, 
			      Y.product = product and Y.year = year
*/

public class EMFExample3_1 {

	public EMFExample3_1() {
	}

	/**
	 * 
	 */
	public void mf_query() {
		DataEmultation[] datas = new DataEmultation[500];
		
		//grouping attributes: from_ac, from_tel
		//calculate sum(1_length)  , sum(0_length), max(1_length)
		List<EMFEntry3> list = new ArrayList<EMFEntry3>();

		//----------------------------------------------------------------------------------------------------------------------------
		//     SCAN 1 - n -> building the MF_TABLE
		//----------------------------------------------------------------------------------------------------------------------------
		//SCAN 1 
		int i = 0;
		while(i < datas.length) {
			
			/*
			 * WHERE CONDITION
			 */
//			if(false) {
//				continue;
//			}
			
			//----------------------------------------------------------------------------------------------------------------------------
			//     Compute the X, Y
			//----------------------------------------------------------------------------------------------------------------------------
			/*
			 * find the grouping attributes for GROUPING VARIABELS 0
			 * 
			 */
			EMFEntry3 emf_entry = new EMFEntry3();
			
			boolean update0 = true;
			boolean success0 = false;
			
			for(int j = 0; j < list.size(); j++) {
				// judge whether it has the same grouping attributes
				if(list.get(j).product.equals(datas[i].getString("product")) && list.get(j).year == datas[i].getInteger("year") && list.get(j).month == datas[i].getInteger("month") && datas[i].getInteger("quant") > 500) {
					if(update0) {
						//update avg
						success0 = true;
					}
				}
			}
			
			boolean update1 = true;
			boolean success1 = false;
			
			for(int j = 0; j < list.size(); j++) {
				if(list.get(j).product.equals(datas[i].getString("product")) && list.get(j).year == datas[i].getInteger("year")) {
					if(update1) {
						//update avg
						success1 = true;
					}
				}
			}
			if(update1 && !success1) {
				emf_entry._2_sum_quantity = datas[i].getInteger("quant");
			}
			
			if((update0 || update1) && (!success0 || !success1)) {
				emf_entry.product = datas[i].getString("prod");
				emf_entry.year = datas[i].getInteger("year");
				emf_entry.month = datas[i].getInteger("month");
				list.add(emf_entry);
			}
			
			//---------------------------------- this part can be omit -------------------------------------------------------------------
			
		}
		
		//----------------------------------------------------------------------------------------------------------------------------
		//     SCAN THE EMF_TABLE TO COMPUTE THE RESULT
		//----------------------------------------------------------------------------------------------------------------------------
		
		//
		// Merge EMF_TABLES : list0, list1, list2 into one base on the product, month, year
		// 
		// Start Merge
//		List<EMFEntry3> final_output = merge(list0, list1, list2);
		while(i < list.size()) {
			
			for(int j = 0; j < list.size(); j++) {
				System.out.println(list.get(j).product);
				System.out.println(list.get(j).year);
				System.out.println(list.get(j).month);
				System.out.println(list.get(j)._1_sum_quantity);
				System.out.println(list.get(j)._2_sum_quantity);
			}
			
		}
		
		/*
		 * IMPROVEMENT:
		 * #1 Filter out redundant EMF_TABLE 1 // BECAUSE it's same to the GROUP VARIABLE 0
		 * #2 implement the MERGE function;
		 * #2 The Entry can be stored in HASH/B+ Tree Structure which will have the O(logN)
		 */
	}
	
	private List<EMFEntry3> merge(Collection<EMFEntry3> ... emftables ) {
		return new ArrayList<EMFEntry3>();
		
	}
	
	private class DataEmultation {
		
		public String getString(String s) {
			return null;
		}
		
		
		public Integer getInteger(String s) {
			return 0;
		}
		
	}
	
}

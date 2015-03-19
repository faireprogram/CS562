package org.stevens.cs562.code;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
		select product, month, year,  sum(X.quantity)/sum(Y.quantity)
		from Sales
		group by product, month, year : X, Y
		such that X.product = product and X.month = month and X.year = year, 
			      Y.product = product, Y.year = year
*/

public class EMFExample3 {

	public EMFExample3() {
	}

	/**
	 * 
	 */
	public void mf_query() {
		DataEmultation[] datas = new DataEmultation[500];
		
		//grouping attributes: from_ac, from_tel
		//calculate sum(1_length)  , sum(0_length), max(1_length)
		List<EMFEntry3> list0 = new ArrayList<EMFEntry3>();
		List<EMFEntry3> list1 = new ArrayList<EMFEntry3>();
		List<EMFEntry3> list2 = new ArrayList<EMFEntry3>();

		//----------------------------------------------------------------------------------------------------------------------------
		//     SCAN 1 - n -> building the MF_TABLE
		//----------------------------------------------------------------------------------------------------------------------------
		//SCAN 1 
		int i = 0;
		while(i < datas.length) {
			
			boolean is_find = false;
			int position = 0;
			EMFEntry3 mf_entry = null;
			
			/*
			 * WHERE CONDITION
			 */
//			if(false) {
//				continue;
//			}
			
			//----------------------------------------------------------------------------------------------------------------------------
			//     Compute the GROUP VARIABLE 0
			//----------------------------------------------------------------------------------------------------------------------------
			/*
			 * find the grouping attributes for GROUPING VARIABELS 0
			 */
			for(int j = 0; j < list0.size(); j++) {
				// judge whether it has the same grouping attributes
				if(list0.get(j).product.equals(datas[i].getString("product")) && 
				  list0.get(j).year == datas[i].getInteger("year") &&
				  list0.get(j).month == datas[i].getInteger("month")) {
					position = j;
					is_find = true;
					break;
				}
			}
			
			if(is_find) { // update the aggregation for 0
				// mf_entry = list.get(position);
			} else { // if not find in MF_TABLE but it can't appear
				mf_entry = new EMFEntry3();
				mf_entry.product = datas[i].getString("product");
				mf_entry.month = datas[i].getInteger("month");
				mf_entry.year = datas[i].getInteger("year");
				list0.add(mf_entry);
			}
			
			//---------------------------------- this part can be omit -------------------------------------------------------------------
			//----------------------------------------------------------------------------------------------------------------------------
			//     Compute the GROUP VARIABLE 1
			//----------------------------------------------------------------------------------------------------------------------------
			/*
			 * find the grouping attributes for GROUPING VARIABELS 1
			 */
			for(int j = 0; j < list0.size(); j++) {
				//CASE 1: If 1.product = product and 1.month = month and 1.year = year
				if(list1.get(j).product.equals(datas[i].getString("product")) && 
				  list1.get(j).year == datas[i].getInteger("year") &&
				  list1.get(j).month == datas[i].getInteger("month")) {
					position = j;
					is_find = true;
					break;
				}
			}
			
			if(is_find) { // update the aggregation for 0
				 mf_entry = list1.get(position);
				 mf_entry._1_sum_quantity += datas[i].getInteger("quantity");
			} else { // if not find in MF_TABLE but it can't appear
				mf_entry = new EMFEntry3();
				mf_entry.product = datas[i].getString("product");
				mf_entry.month = datas[i].getInteger("month");
				mf_entry.year = datas[i].getInteger("year");
				list1.add(mf_entry);
			}
			//---------------------------------- End -------------------------------------------------------------------------------------
			
			//----------------------------------------------------------------------------------------------------------------------------
			//     Compute the GROUP VARIABLE 2
			//----------------------------------------------------------------------------------------------------------------------------
			/*
			 * find the grouping attributes for GROUPING VARIABELS 2
			 */
			for(int j = 0; j < list2.size(); j++) {
				// CASE 2: If 2.product = product and 2.year = year
				if(list2.get(j).product.equals(datas[i].getString("product")) && 
				  list2.get(j).year == datas[i].getInteger("year")) {
					position = j;
					is_find = true;
					break;
				}
			}
			
			if(is_find) { // update the aggregation for 2
				 mf_entry = list2.get(position);
				 mf_entry._2_sum_quantity += datas[i].getInteger("quantity");
			} else { // if not find in MF_TABLE but it can't appear
				mf_entry = new EMFEntry3();
				mf_entry.product = datas[i].getString("product");
				mf_entry.month = datas[i].getInteger("month");
				mf_entry.year = datas[i].getInteger("year");
				list2.add(mf_entry);
			}
			i++;
		}
		
		//----------------------------------------------------------------------------------------------------------------------------
		//     SCAN THE EMF_TABLE TO COMPUTE THE RESULT
		//----------------------------------------------------------------------------------------------------------------------------
		
		//
		// Merge EMF_TABLES : list0, list1, list2 into one base on the product, month, year
		// 
		// Start Merge
		@SuppressWarnings("unchecked")
		List<EMFEntry3> final_output = merge(list0, list1, list2);
		i = 0;
		while(i < final_output.size()) {
			
			for(int j = 0; j < final_output.size(); j++) {
				System.out.println(final_output.get(j).product);
				System.out.println(final_output.get(j).year);
				System.out.println(final_output.get(j).month);
				System.out.println(final_output.get(j)._1_sum_quantity);
				System.out.println(final_output.get(j)._2_sum_quantity);
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

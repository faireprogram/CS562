package org.stevens.cs562.code;

import java.util.ArrayList;
import java.util.List;

/*
		select product, sum(X.quantity), sum(Y.quantity), sum(Z.quantity)
		from Sales
		where year=¡®¡®1997¡¯¡¯
		group by product : X, Y, Z
		such that X.month = 1, Y.month = 2, Z.month = 3
*/

public class EMFExample2 {

	public EMFExample2() {
	}

	/**
	 * 
	 */
	private void mf_query() {
		DataEmultation[] datas = new DataEmultation[500];
		
		//grouping attributes: from_ac, from_tel
		//calculate sum(1_length)  , sum(0_length), max(1_length)
		List<EMFEntry2> list = new ArrayList<EMFEntry2>();
		
		

		//----------------------------------------------------------------------------------------------------------------------------
		//     SCAN 1 - n -> building the MF_TABLE
		//----------------------------------------------------------------------------------------------------------------------------
		//SCAN 1 
		int i = 0;
		while(i < datas.length) {
			
			boolean is_find = false;
			int position = 0;
			EMFEntry2 mf_entry = null;
			
			
			/*
			 * WHERE CONDITION
			 */
			if(!(datas[i].getString("year").equals("1997"))) {
				continue;
			}
			
			//----------------------------------------------------------------------------------------------------------------------------
			//     Compute the GROUP VARIABLE 0
			//----------------------------------------------------------------------------------------------------------------------------
			/*
			 * this part is for 0
			 * e.g _0_max_length
			 */
			for(int j = 0; j < list.size(); j++) {
				// judge whether it has the same grouping attributes
				if(list.get(j).product.equals(datas[i].getString("product"))) {
					position = j;
					is_find = true;
					break;
				}
			}
			if(is_find) { // update the aggregation for 0
				 mf_entry = list.get(position);
			} else { // if not find in MF_TABLE but it can't appear
				mf_entry = new EMFEntry2();
				mf_entry.product = datas[i].getString("product");
			}
			
			//----------------------------------------------------------------------------------------------------------------------------
			//     Compute the GROUP VARIABLE 1
			//----------------------------------------------------------------------------------------------------------------------------
			// CASE 1: If 1.month = 1
			if(datas[i].getInteger("month") == 1) {
				mf_entry._1_sum_quantity += datas[i].getInteger("quantity");
			}
			 
			// CASE 1: If 2.month = 2
			if(datas[i].getInteger("month") == 2) {
				mf_entry._2_sum_quantity += datas[i].getInteger("quantity");
			}
			
			// CASE 1: If 3.month = 3
			if(datas[i].getInteger("month") == 3) {
				mf_entry._3_sum_quantity += datas[i].getInteger("quantity");
			}
			i++;
		}
		
		//----------------------------------------------------------------------------------------------------------------------------
		//     SCAN THE EMF_TABLE TO COMPUTE THE RESULT
		//----------------------------------------------------------------------------------------------------------------------------
		i = 0;
		while(i < list.size()) {
			
			for(int j = 0; j < list.size(); j++) {
				System.out.println(list.get(j).product);
				System.out.println(list.get(j)._1_sum_quantity);
				System.out.println(list.get(j)._2_sum_quantity);
				System.out.println(list.get(j)._3_sum_quantity);
			}
			
		}
		
		/*
		 * IMPROVEMENT:
		 * #1 The Last PROJECTION Scan can concentrate in the relative set of R ( NOT scan the whole DATA again)
		 * #2 final result should be the join within the R0,R1,R2 and MF_TABLE  -> scan the whole data, compute the both selection, use or
		 * #2 The Entry can be stored in HASH/B+ Tree Structure which will have the O(logN)
		 */
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

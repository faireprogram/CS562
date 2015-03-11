package org.stevens.cs562.code;

import java.util.ArrayList;
import java.util.List;

/*
	select product, month, avg(X.quantity),
	avg(Y.quantity)
	from Sales
	where year=¡®¡®1997¡¯¡¯
	group by product, month; X , Y
	such that X.product=product and X.month>month, Y.product=product and Y.month<month
*/

public class EMFExample1 {

	public EMFExample1() {
	}

	/**
	 * 
	 */
	private void mf_query() {
		DataEmultation[] datas = new DataEmultation[500];
		
		//grouping attributes: from_ac, from_tel
		//calculate sum(1_length)  , sum(0_length), max(1_length)
		List<EMFEntry1> list = new ArrayList<EMFEntry1>();
		
		

		//----------------------------------------------------------------------------------------------------------------------------
		//     SCAN 1 - n -> building the MF_TABLE
		//----------------------------------------------------------------------------------------------------------------------------
		//SCAN 1 
		int i = 0;
		while(i < datas.length) {
			
			boolean is_find = false;
			int position = 0;
			EMFEntry1 mf_entry = null;
			
			
			/*
			 * WHERE CONDITION
			 */
			if(!(datas[i].getString("year").equals("1997"))) {
				continue;
			}
			
			//----------------------------------------------------------------------------------------------------------------------------
			//     Compute the GROUP 0
			//----------------------------------------------------------------------------------------------------------------------------
			/*
			 * this part is for 0
			 * e.g _0_max_length
			 */
			for(int j = 0; j < list.size(); j++) {
				// judge whether it has the same grouping attributes
				if(list.get(j).product.equals(datas[i].getString("product")) && list.get(j).month.equals(datas[i].getString("month"))) {
					position = j;
					is_find = true;
					break;
				}
			}
			if(is_find) { // update the aggregation for 0
//				list.get(position)._0_sum_length = list.get(position)._0_sum_length + datas[i].getInteger("count");
//				list.get(position)._0_count += 1;
				 mf_entry = list.get(position);
			} else { // if not find in MF_TABLE but it can't appear
				mf_entry = new EMFEntry1();
				mf_entry.product = datas[i].getString("product");
				mf_entry.month = datas[i].getInteger("month");
			}
			
			i++;
		}
		
		
		//----------------------------------------------------------------------------------------------------------------------------
		//     SCAN 2 - n -> continue building MF_TABLE
		//----------------------------------------------------------------------------------------------------------------------------
		//SCAN 2 
		i = 0;
		while(i < datas.length) {
			
			int position = 0;
			
			
			//----------------------------------------------------------------------------------------------------------------------------
			//     In this case, We needn't Compute the GROUP 0. THE GROUP Number should start from 1 - N
			//----------------------------------------------------------------------------------------------------------------------------
			/*
			 * this part is for 0
			 * e.g _0_max_length
			 */
			for(int j = 0; j < list.size(); j++) {
				// judge whether it has the same grouping attributes
				if(list.get(j).product.equals(datas[i].getString("product")) && list.get(j).month.equals(datas[i].getString("month"))) {
					position = j;
					break;
				}
			}
			
			EMFEntry1 mf_entry = list.get(position);
			
			//----------------------------------------------------------------------------------------------------------------------------
			//     Compute the GROUP 1 - /  AGGREGATION  combine with selection
			//----------------------------------------------------------------------------------------------------------------------------
			// #1
			// in this case : update the if 1.product=product and 1.month>month
			// update the Records in MF_TABLE
			for(int j =0; j < list.size(); j++) {
				if(datas[i].getString("product").equals(list.get(j).product) 
						&& datas[i].getInteger("month") > list.get(j).month) {
					//update the EMF_table
					/*
					 * this part is for 1
					 * e.g _1_sum_quantity
					 * e.g _1_count
					 */
					list.get(j)._1_count += 1;
					list.get(j)._1_sum_quantity += 1;
				}
			}
			
			
			//----------------------------------------------------------------------------------------------------------------------------
			//     Compute the GROUP 2 - /  AGGREGATION  combine with selection
			//----------------------------------------------------------------------------------------------------------------------------
			// #2
			// in this case : update the if 2.product=product and 2.month<month
			// update the Records in MF_TABLE
			for(int j =0; j < list.size(); j++) {
				if(datas[i].getString("product").equals(list.get(j).product) 
						&& datas[i].getInteger("month") < list.get(j).month) {
					//update the EMF_table
					/*
					 * this part is for 2
					 * e.g _2_sum_quantity
					 * e.g _2_count
					 */
					list.get(j)._2_count += 1;
					list.get(j)._2_sum_quantity += datas[i].getInteger("quantity");
				}
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
				System.out.println(list.get(j).month);
				System.out.println(list.get(j)._1_sum_quantity/list.get(j)._1_count);
				System.out.println(list.get(j)._2_sum_quantity/list.get(j)._2_count);
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

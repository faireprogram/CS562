package org.stevens.cs562.code;

import java.util.ArrayList;
import java.util.List;

/*

select FromAC, FromTel,	X.names as names, count(X.*), count(Y.*), 
from CALLS
group by FromAC, FromTel : X, Y
suchthat 
(X.Date<"96/07/01" AND  X.Length>avg(Length)) AND (Y.Date>"96/06/30"  AND  Y.Length>avg(Length))

 */
// THIS IS PSUDO CODE !!! NOT REALLY IMPLMENTATION
public class MFExample2 {

	public MFExample2() {
	}
	
	/**
	 * 
	 */
	private void mf_query() {
		DataEmultation[] datas = new DataEmultation[500];
		
		//grouping attributes: from_ac, from_tel
		//calculate sum(1_length)  , sum(0_length), max(1_length)
		List<MFEntry2> list = new ArrayList<MFEntry2>();
		
		

		//----------------------------------------------------------------------------------------------------------------------------
		//     SCAN 1 - n -> building the MF_TABLE
		//----------------------------------------------------------------------------------------------------------------------------
		//SCAN 1 
		int i = 0;
		while(i < datas.length) {
			
			boolean is_find = false;
			int position = 0;
			
			
			//----------------------------------------------------------------------------------------------------------------------------
			//     Compute the GROUP 0
			//----------------------------------------------------------------------------------------------------------------------------
			/*
			 * this part is for 0
			 * e.g _0_max_length
			 */
			for(int j = 0; j < list.size(); j++) {
				// judge whether it has the same grouping attributes
				if(list.get(j).from_ac.equals(datas[i].getString("from_ac")) && list.get(j).from_tel.equals(datas[i].getString("from_tel"))) {
					position = j;
					is_find = true;
					break;
				}
			}
			if(is_find) { // update the aggregation for 0
				list.get(position)._0_sum_length = list.get(position)._0_sum_length + datas[i].getInteger("count");
				list.get(position)._0_count += 1;
			} else { // if not find in MF_TABLE but it can't appear
				MFEntry2 mf_entry = new MFEntry2();
				mf_entry.from_ac = datas[i].getString("from_ac");
				mf_entry.from_tel = datas[i].getString("from_tel");
				mf_entry._0_sum_length = datas[i].getInteger("count");
				mf_entry._0_count = 1;
			}
			
			//----------------------------------------------------------------------------------------------------------------------------
			//     Compute the GROUP 1 - /  AGGREGATION
			//----------------------------------------------------------------------------------------------------------------------------
			// #1
			// in this case : update the if 1.date > 96/05/31 || 1.date < 96/09/01
			// update the Records in MF_TABLE
//			if(datas[i].getString("Date").equals("96/05/31") || datas[i].getString("Date").equals("96/09/01")) {
//				//update the aggregation table
//				// #1
//				// inner Loop for MFEntry2 -> Hash Table & B+ plus tree
//				
//				/*
//				 * this part is for 1
//				 * e.g _1_sum_length
//				 * e.g _1_max_length
//				 */
//				list.get(position)._1_sum_length = list.get(position)._1_sum_length + datas[i].getInteger("count");
//				list.get(position)._1_max_length = list.get(position)._1_max_length + datas[i].getInteger("count");
//				
//			}
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
				if(list.get(j).from_ac.equals(datas[i].getString("from_ac")) && list.get(j).from_tel.equals(datas[i].getString("from_tel"))) {
					position = j;
					break;
				}
			}
			
			MFEntry2 mf_entry = list.get(position);
			
			//----------------------------------------------------------------------------------------------------------------------------
			//     Compute the GROUP 1 - /  AGGREGATION  combine with selection
			//----------------------------------------------------------------------------------------------------------------------------
			// #1
			// in this case : update the if 1.Date<"96/07/01" AND  1.Length>avg(Length)
			// update the Records in MF_TABLE
			if(datas[i].getString("Date").equals("96/05/31") && datas[i].getInteger("Length") > (mf_entry._0_sum_length/mf_entry._0_count)) {
				//update the aggregation table
				/*
				 * this part is for 1
				 * e.g _1_sum_length
				 * e.g _1_max_length
				 */
				list.get(position)._1_count += 1;
			}
			//----------------------------------------------------------------------------------------------------------------------------
			//     Compute the GROUP 2 - /  AGGREGATION  combine with selection
			//----------------------------------------------------------------------------------------------------------------------------
			// #2
			// in this case : update the if 2.Date>"96/06/30"  AND  Y.Length>avg(Length)
			// update the Records in MF_TABLE
			if(datas[i].getString("Date").equals("96/05/31") && datas[i].getInteger("Length") > (mf_entry._0_sum_length/mf_entry._0_count)) {
				//update the aggregation table
				/*
				 * this part is for 1
				 * e.g _1_sum_length
				 * e.g _1_max_length
				 */
				list.get(position)._2_count += 1;
			}
			i++;
		}
		
		
		//----------------------------------------------------------------------------------------------------------------------------
		//     LAST SCAN n + 1 -> Compute Having -> Projection the result
		//----------------------------------------------------------------------------------------------------------------------------
		//SCAN 2 
		i = 0;
		while(i < datas.length) {
			
			int position = 0;
			
			//--------
			// HAVING|
			//--------
			//find the attribute in the MF_TABLE
			for(int j = 0; j < list.size(); j++) {
				// judge whether it has the same grouping attributes
				if(list.get(j).from_ac.equals(datas[i].getString("from_ac")) && list.get(j).from_tel.equals(datas[i].getString("from_tel"))) {
					position = j;
					break;
				}
			}
			
			/*
			 * DO WHERE / SUCH THAT /  HAVING CONDITION
			 */
			MFEntry2 mf_entry = list.get(position);
			// WHERE CONDITION
			if(true) {
				// SUCHTHAT CONDITION
				if(datas[i].getString("Date").equals("96/05/31") && datas[i].getInteger("Length") > (mf_entry._0_sum_length/mf_entry._0_count) ||   // FOR 1_X
				   datas[i].getString("Date").equals("96/05/31") && datas[i].getInteger("Length") > (mf_entry._0_sum_length/mf_entry._0_count)) {   // FOR 2_Y	
	
					// HAVING CONDITION
					if(true) {
						// projection code;
						System.out.println(datas[i].getString("project_XXX"));
						System.out.println(datas[i].getString("project_XXX"));
					}
				}
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

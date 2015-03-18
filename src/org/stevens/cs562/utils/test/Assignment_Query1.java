package org.stevens.cs562.utils.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Author: Zejie Ding
 * How to test my code on postgres server
 * 1. Upload all my .java files to server
 * 
 * 2. Before you run my file, please remember to export the jdbc driver
 * Like this: export CLASSPATH=/home/zding6/java/postgresql-9.2-1003.jdbc4.jar:.
 *  
 * and change the username, pwd and url to yours in my code.
 * 
 * 3. There is a class file named sale_rec_ind.java that will be used in both
 * queries. Make sure you compile this class file along with two queries files
 * Like this: 
* zding6@postgres:~/java$ javac -d . Assignment1_Query1.java sale_rec_ind.java
* zding6@postgres:~/java$ javac -d . Assignment1_Query2_Query3.java sale_rec_ind.java
 * 
 * 4. Please notice that my java files are under the Basic_Query package, please
 * remember to type Basic_Query.Assignment1_Query1 before you run it
 * Like this:
 * zding6@postgres:~/java$ java Basic_Query.Assignment_Query1
 * zding6@postgres:~/java$ java Basic_Query.Assignment_Query2
*/

/*
 * select avg(X.m), avg(Y.m), avg(Z.m)
 * 
 * 
 * 
 */

public class Assignment_Query1 {
	public static void main(String[] args) 
	{
		int count=0;   //count number
		int flag;      //indicator for the(cname & pname) combination
		sale_rec_ind [] sri= new sale_rec_ind[500];  //sale record indicator
		for(int i=0; i<500; i++)
		{
			sri[i] = new sale_rec_ind();
		}
		//this is for database connection
		String usr ="postgres";
		String pwd ="zw198787";
		String url ="jdbc:postgresql://localhost:5432/test";
		
		//try to capture the exception if you are not connecting to database
		try 
		{
			Class.forName("org.postgresql.Driver");
		} 

		catch(Exception e) 
		{
			System.out.println("Fail loading Driver!");
			e.printStackTrace();
		}
		
		//----------------------------------------------------------------------
		// PRINT TITLE OF THE RESULT
		//----------------------------------------------------------------------
		System.out.printf("CUST  AVG_NY  AVG_NJ  AVG_CT\n");
		System.out.printf("====  ======  ======  ======\n");
		
		try 
		{
			//build the connection  and execute the sql statement
			Connection conn = DriverManager.getConnection(url, usr, pwd); 

			Statement stmt = conn.createStatement(); 
			ResultSet rs = stmt.executeQuery("SELECT * FROM Sales");
			
			while (rs.next()) //you need to read every line of the database
			{
				if(rs.getRow() ==0) // you are reaching the first row
				{
					if(rs.getString("state").equals("NY") && rs.getShort("year")==1997)//if state is NY, store the relevant variables
					{
						sri[0].cname = rs.getString("cust");           //store customer name by string copy            
						sri[0].ny_sum = rs.getDouble("quant");         //sum = quantity for the first row, store the quantity    
						sri[0].ny_amount = 1;                           //amount = row number =1
						count++;					               //continue to the next row;
					}
					else if(rs.getString("state").equals("NJ")&& rs.getShort("year")==1997)
					{
						sri[0].cname = rs.getString("cust");                     
						sri[0].nj_sum = rs.getDouble("quant");          
						sri[0].nj_amount = 1;
						count++;
					}
					else if(rs.getString("state").equals("CT")&& rs.getShort("year")==1997)
					{
						sri[0].cname = rs.getString("cust");                    
						sri[0].ct_sum = rs.getDouble("quant");          
						sri[0].ct_amount = 1;
						count++;
					}
				}
				else if(rs.getRow() >0)					//if not first row
		          {
		             flag=0;						//initialize the indicator of (customer name&product name)combination, if mark =1, the combination exist
		             int n= 0;
		             for(n=0; n<count; n++)			//testing the (customer name&product name)combination with the former rows
		             {
		              	 String cname_temp; 
						 cname_temp = rs.getString("cust");      //Temporary value for customer name
						 if(sri[n].cname.equals(cname_temp))   //find the same (customer name&product name)combination
		                 {
							 flag = 1;                                                        //if exist, flag=1
		                     if(rs.getString("state").equals("NY")&& rs.getShort("year")==1997)                           //add the quantity to relevant sum, and amount+1
		                     {
		                         sri[n].ny_sum += rs.getDouble("quant");
		                         sri[n].ny_amount ++;
		                     }
							 else if(rs.getString("state").equals("NJ")&& rs.getShort("year")==1997)     
		                     {
								  sri[n].nj_sum += rs.getDouble("quant");
			                      sri[n].nj_amount ++;
		                     }
		                     else if(rs.getString("state").equals("CT")&& rs.getShort("year")==1997)     
		                     {
		                    	 sri[n].ct_sum += rs.getDouble("quant");
		                         sri[n].ct_amount ++;
		                     }
		                 }
		               }
		             if(flag == 0)                                                 //if (customer name & product name)combination not exist 
	           		 {     														   //we need to add a new row of customer name & product name 
	                    if(rs.getString("state").equals("NY")&& rs.getShort("year")==1997)                       
	                    {
							sri[count].cname = rs.getString("cust");                     
							sri[count].ny_sum = rs.getDouble("quant");
							sri[count].ny_amount = 1;
							count++;
						}

						else if(rs.getString("state").equals("NJ")&& rs.getShort("year")==1997)
						{
							sri[count].cname = rs.getString("cust");                      
							sri[count].nj_sum = rs.getDouble("quant");
							sri[count].nj_amount = 1;
							count++;
						}

						else if(rs.getString("state").equals("CT")&& rs.getShort("year")==1997)
						{
							sri[count].cname = rs.getString("cust");                    
							sri[count].ct_sum = rs.getDouble("quant");
							sri[count].ct_amount = 1;
							count++;
						}                 
	          		  }
		            }
			} // this is the end of while
			int t;
			for(t = 0; t < count; t++)
			{
				sri[t].ny_avg = sri[t].ny_sum / sri[t].ny_amount;     //avg = sum/amount
				sri[t].nj_avg = sri[t].nj_sum / sri[t].nj_amount;
				sri[t].ct_avg = sri[t].ct_sum/ sri[t].ct_amount;
				if(sri[t].ny_avg >= sri[t].nj_avg && sri[t].ny_avg >= sri[t].ct_avg)
				{
					System.out.printf("%-5s    ",sri[t].cname);   // Customer
					System.out.printf("    %5.0f  ",sri[t].ny_avg);   // NY avg
					System.out.printf(" %5.0f ",sri[t].nj_avg);   // NJ avg
					System.out.printf("  %5.0f\n",sri[t].ct_avg); // CT avg
				}
			}
		} 
		catch(SQLException e) 
		{
			System.out.println("Connection URL or username or password errors!");
			e.printStackTrace();
		}
	}
}

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class print_DS{
	
	public void print_ds( ) {
		
		String usr ="postgres";
		String pwd ="19930920";
		String url ="jdbc:postgresql://localhost:5432/postgres";
		
		try 
		{
			Class.forName("org.postgresql.Driver");
		} 

		catch(Exception e) 
		{
			System.out.println("Fail loading Driver!");
			e.printStackTrace();
		}
		
		
		int flag_sum=0, flag_cnt = 0; //indicate whether attribute is already there or not
		String cust_dt="",prod_dt="",quant_dt="", date_dt="";
		
		try {
			File file_input  = new File("sampleQuery.txt");
			Scanner input = new Scanner(file_input);
			
			String [] selectattr = input.nextLine().split(" ");
			int num_of_var = Integer.parseInt(input.nextLine());
			String [] groupattr = input.nextLine().split(" ");
			String [] aggrfunc = input.nextLine().split(" ");		
			String [] selectcon = input.nextLine().split(" ");
			String [] havingcon = input.nextLine().split(" ");
			
			//build the connection  and execute the sql statement
			Connection conn = DriverManager.getConnection(url, usr, pwd); 

			Statement stmt = conn.createStatement(); 
			ResultSet cn_dt = stmt.executeQuery("select * from information_schema.columns");
			while(cn_dt.next())
			{
				if(cn_dt.getString("column_name").equals("cust") 
						&& cn_dt.getString("data_type").equals("character varying")
						&& cn_dt.getString("table_name").equals("sales"))
				{
					cust_dt = "String";
				}
				else if(cn_dt.getString("column_name").equals("prod") 
						&& cn_dt.getString("data_type").equals("character varying")
						&& cn_dt.getString("table_name").equals("sales"))
				{
					prod_dt = "String";
				}
				else if(cn_dt.getString("column_name").equals("quant") 
						&& cn_dt.getString("data_type").equals("integer")
						&& cn_dt.getString("table_name").equals("sales"))
				{
					quant_dt = "int";
				}
			}
			
			System.out.println("public class MF_Struct {");
			for(int i = 0; i < groupattr.length; i++)
			{
				if(groupattr[i].equals("cust"))
				{
					System.out.println("\t" + cust_dt + "\t" + "cust");
				}
				else if (groupattr[i].equals("prod"))
				{
					System.out.println("\t" + prod_dt + "\t" + "prod");
				}
			}
			
			for(int j = 0; j < aggrfunc.length; j++)
			{
				for (int k=1; k<=num_of_var; k++)
				{
					if(aggrfunc[j].contains("sum_"+k))
					{
						System.out.println("\t" + quant_dt + "\t" + aggrfunc[j]+";");
						flag_sum = 1;
					}
					else if (aggrfunc[j].contains("cnt_"+k))
					{
						System.out.println("\t" + quant_dt + "\t" + aggrfunc[j]+";");
						flag_cnt = 1;
					}
					else if (aggrfunc[j].contains("min_"+k))
					{
						System.out.println("\t" + quant_dt + "\t" + aggrfunc[j]+";");
					}
					else if (aggrfunc[j].contains("max_"+k))
					{
						System.out.println("\t" + quant_dt + "\t" + aggrfunc[j]+";");
					}
					else if (aggrfunc[j].contains("avg_"+k))
					{
						if(flag_sum == 0 && flag_cnt == 0)
						{
							System.out.println("\t" + quant_dt + "\t" + "sum_"+k+"_quant"+";");
							System.out.println("\t" + quant_dt + "\t" + "cnt_"+k+"_quant"+";");
						}
						else if(flag_sum == 0)
						{
							System.out.println("\t" + quant_dt + "\t" + "sum_"+k+"_quant"+";");
						}
						else if (flag_cnt == 0)
						{
							System.out.println("\t" + quant_dt + "\t" + "cnt_"+k+"_quant"+";");
						}
					}
				}
			}
			
			System.out.println("}");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			System.out.println("File is not found!");
			e1.printStackTrace();
		}
		catch(SQLException e) 
		{
			System.out.println("Connection URL or username or password errors!");
			e.printStackTrace();
		}
	}

}

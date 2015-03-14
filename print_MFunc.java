import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;


public class print_MFunc {
	
	String usr ="postgres";
	String pwd ="19930920";
	String url ="jdbc:postgresql://localhost:5432/postgres"; 
		
	public void printMainFunction(){
		
		int flag_sum = 0, flag_cnt=0;
		File file_input  = new File("sampleQuery.txt");
		Scanner input;
		try {
			input = new Scanner(file_input);
			String a = "";
			String b = "";
			String [] selectattr = input.nextLine().split(" ");
			int num_of_var = Integer.parseInt(input.nextLine());
			String [] groupattr = input.nextLine().split(" ");
			String [] aggrfunc = input.nextLine().split(" ");		
			String [] selectcon = input.nextLine().split(" ");
			String [] havingcon = input.nextLine().split(" ");
			
			//List<String> heads_prints = getPrintedHeader();
			String str = "";
			str += "import java.sql.Connection;\n";
			str += "import java.sql.DriverManager;\n";
			str += "import java.sql.ResultSet;\n";
			str += "import java.sql.SQLException;\n";
			str += "import java.sql.Statement;\n\n";
			str += "public class "+ "MF_QUERY" +" {\n";
			str +=  	"\tpublic static void main(String[] args) {\n";
			str +=          "\t\t" + "int count = 0;\n";
			str +=          "\t\t" + "int flag;\n";
			str += 			"\t\t" + "MF_Struct" +"[] mf= new " + "MF_Struct" + "[500];\n";
			str += 			"\t\tfor(int i=0; i<500; i++) {\n";
			str +=			"\t\t\tsri[i] = new "+ "MF_Struct" +"();\n";
			str += 			"\t\t}\n";
			str += 			"\t\tString usr =\"" + usr +"\";\n";
			str += 			"\t\tString pwd =\"" + pwd +"\";\n";
			str += 			"\t\tString url =\"" + url +"\";\n";
			str += 			"\t\ttry {\n";
			str += 			"\t\t\tClass.forName(\"org.postgresql.Driver\");\n";
			str += 			"\t\t} catch(Exception e) {\n";
			str += 			 "\t\t\te.printStackTrace();\n";
			str +=			"\t\t}\n";
			str +=			"\t\t//----------------------------------------------------------------------\n";
			str +=			"\t\t//PRINT TITLE OF THE RESULT\n";
			str +=			"\t\t//----------------------------------------------------------------------\n";
			for(int i=0; i<selectattr.length; i++)
			{
				a = a + " " + selectattr[i];
			}
			for(int j=0; j<a.length(); j++)
			{
				b = b + "=";
			}
			str +=			"\t\tSystem.out.println(\"" + a + "\");\n";
			str +=			"\t\tSystem.out.println(\"" + b + "\");\n\n";
			str +=          "\t\ttry\n";
			str +=          "\t\t{\n";
			str +=          "\t\t//build the connection  and execute the sql statement\n";
			str +=          "\t\tConnection conn = DriverManager.getConnection(url, usr, pwd);\n\n";
			str +=          "\t\tStatement stmt = conn.createStatement();\n";
			str +=          "\t\tResultSet rs = stmt.executeQuery(\""+"SELECT * FROM Sales"+"\");\n";
			str +=          "\t\twhile(rs.next())\n";
			str +=          "\t\t{\n";
			str +=          "\t\t\tif(rs.getrow() ==0)\n"; //first row of the table
			str +=          "\t\t\t{\n";
			
			for(int i=0; i < selectcon.length; i++)
			{
				if(selectcon[i].contains(".state"))
				{
					if(i == 0)
					{
						str +=          "\t\t\t\tif (rs.getString(\""+"state"+"\").";
					}
					else{
						str +=          "\t\t\t\telse if (rs.getString(\""+"state"+"\").";
					}
					if(selectcon[i].contains("="))
					{
						String s = selectcon[i].substring(".state".length()+2);
						str +=          "equals(\""+s+"\"))\n";
						str +=          "\t\t\t\t{\n";
						for (int j = 0; j < groupattr.length; j++)
						{
							str +=          "\t\t\t\tsri[0]."+groupattr[j]+"=rs.getString(\""+groupattr[j]+"\");\n";
						}
					}
				}
				for(int k = 1; k <= num_of_var; k++)
				{
					if(selectcon[i].contains(""+k))
					{
						for(int j=0; j<aggrfunc.length; j++)
						{
							if(aggrfunc[j].contains(""+k))
							{
								if(aggrfunc[j].contains("sum_"))
								{
									str +=          "\t\t\t\tsri[0]."+"sum_"+k+"_quant"+"=rs.getDouble(\""+"quant"+"\");\n";
									flag_sum = 1;
								}
								else if(aggrfunc[j].contains("avg_"))
								{
									if(flag_sum !=1 && flag_cnt !=1)
									{
										str +=          "\t\t\t\tsri[0]."+"sum_"+k+"_quant"+"=rs.getDouble(\""+"quant"+"\");\n";
										str +=          "\t\t\t\tsri[0]."+"cnt_"+k+"_quant"+"=1;\n";
									}
									else if (flag_sum !=1 && flag_cnt ==1)
									{
										str +=          "\t\t\t\tsri[0]."+"sum_"+k+"_quant"+"=rs.getDouble(\""+"quant"+"\");\n";
									}
									else if (flag_sum ==1 && flag_cnt !=1)
									{
										str +=          "\t\t\t\tsri[0]."+"cnt_"+k+"_quant"+"=1;\n";
									}
								}
							}
						}
						
					}
				}
				str +=          "\t\t\t\tcount++;\n";
				str +=          "\t\t\t\t}\n";
			}
			
			
			str +=          "\t\t\t}\n";
			str +=          "\t\t\telse if(rs.getRow()>0)\n";
			str +=          "\t\t\t{\n";
			str +=          "\t\t\t\tflag = 0;\n";
			str +=          "\t\t\t\tfor (int n=0; n<count; n++)\n";
			str +=          "\t\t\t\t{\n";
			
			String s = "";
			if(groupattr.length > 1)
			{
				for (int m=0; m<groupattr.length-1; m++)
				{
					
						str +=          "\t\t\t\t\tString " +groupattr[m]+"_temp, "+groupattr[m+1]+"_temp;\n";
						s +=  "sri[n]."+groupattr[m]+".equals"+"("+groupattr[m] + "_temp"+")" 
					+ " "+ "&&" + " "+ "sri[n]."+groupattr[m+1]+".equals"+"("+groupattr[m+1] + "_temp"+") ";  
						
				}
			}
			else 
			{
				for (int n=0; n<groupattr.length; n++ )
				{
					str +=          "\t\t\t\t\tString " +groupattr[n]+"_temp;\n";
					s +=          "sri[n]."+groupattr[n]+".equals"+"("+groupattr[n] + "_temp"+")";
				}
			}
			
			str +=          "\t\t\t\t\tif("+s;
			str +=          ")\n";
			str +=          "\t\t\t\t\t{\n";
			str +=          "\t\t\t\t\t\tflag = 1;\n";
			for(int i=0; i < selectcon.length; i++)
			{
				if(selectcon[i].contains(".state"))
				{
					if(i == 0)
					{
						str +=          "\t\t\t\t\t\tif (rs.getString(\""+"state"+"\").";
					}
					else{
						str +=          "\t\t\t\t\t\telse if (rs.getString(\""+"state"+"\").";
					}
					if(selectcon[i].contains("="))
					{
						String st = selectcon[i].substring(".state".length()+2);
						str +=          "equals(\""+st+"\"))\n";
						str +=          "\t\t\t\t\t\t{\n";
					}
				}
				for(int k = 1; k <= num_of_var; k++)
				{
					if(selectcon[i].contains(""+k))
					{
						for(int j=0; j<aggrfunc.length; j++)
						{
							if(aggrfunc[j].contains(""+k))
							{
								if(aggrfunc[j].contains("sum_"))
								{
									str +=          "\t\t\t\t\t\tsri[n]."+"sum_"+k+"_quant"+"+=rs.getDouble(\""+"quant"+"\");\n";
									flag_sum = 1;
								}
								else if(aggrfunc[j].contains("avg_"))
								{
									if(flag_sum !=1 && flag_cnt !=1)
									{
										str +=          "\t\t\t\t\t\tsri[n]."+"sum_"+k+"_quant"+"+=rs.getDouble(\""+"quant"+"\");\n";
										str +=          "\t\t\t\t\t\tsri[n]."+"cnt_"+k+"_quant"+"++;\n";
									}
									else if (flag_sum !=1 && flag_cnt ==1)
									{
										str +=          "\t\t\t\t\t\tsri[n]."+"sum_"+k+"_quant"+"+=rs.getDouble(\""+"quant"+"\");\n";
									}
									else if (flag_sum ==1 && flag_cnt !=1)
									{
										str +=          "\t\t\t\t\t\tsri[n]."+"cnt_"+k+"_quant"+"++;\n";
									}
								}
							}
						}
						
					}
				}
				
				str +=          "\t\t\t\t\t\t}\n";
			}
			
			
			str +=          "\t\t\t\t\t}\n";
			str +=          "\t\t\t\t}\n";
			
			str +=          "\t\t\t\tif(flag == 0)\n";
			str +=          "\t\t\t\t{\n";
			for(int i=0; i < selectcon.length; i++)
			{
				if(selectcon[i].contains(".state"))
				{
					if(i == 0)
					{
						str +=          "\t\t\t\t\tif (rs.getString(\""+"state"+"\").";
					}
					else{
						str +=          "\t\t\t\t\telse if (rs.getString(\""+"state"+"\").";
					}
					if(selectcon[i].contains("="))
					{
						String sw = selectcon[i].substring(".state".length()+2);
						str +=          "equals(\""+sw+"\"))\n";
						str +=          "\t\t\t\t\t{\n";
						for (int j = 0; j < groupattr.length; j++)
						{
							str +=          "\t\t\t\t\tsri[0]."+groupattr[j]+"=rs.getString(\""+groupattr[j]+"\");\n";
						}
					}
				}
				for(int k = 1; k <= num_of_var; k++)
				{
					if(selectcon[i].contains(""+k))
					{
						for(int j=0; j<aggrfunc.length; j++)
						{
							if(aggrfunc[j].contains(""+k))
							{
								if(aggrfunc[j].contains("sum_"))
								{
									str +=          "\t\t\t\t\tsri[0]."+"sum_"+k+"_quant"+"=rs.getDouble(\""+"quant"+"\");\n";
									flag_sum = 1;
								}
								else if(aggrfunc[j].contains("avg_"))
								{
									if(flag_sum !=1 && flag_cnt !=1)
									{
										str +=          "\t\t\t\t\tsri[0]."+"sum_"+k+"_quant"+"=rs.getDouble(\""+"quant"+"\");\n";
										str +=          "\t\t\t\t\tsri[0]."+"cnt_"+k+"_quant"+"=1;\n";
									}
									else if (flag_sum !=1 && flag_cnt ==1)
									{
										str +=          "\t\t\t\t\tsri[0]."+"sum_"+k+"_quant"+"=rs.getDouble(\""+"quant"+"\");\n";
									}
									else if (flag_sum ==1 && flag_cnt !=1)
									{
										str +=          "\t\t\t\t\tsri[0]."+"cnt_"+k+"_quant"+"=1;\n";
									}
								}
							}
						}
						
					}
				}
				str +=          "\t\t\t\t\tcount++;\n";
				str +=          "\t\t\t\t\t}\n";
			}
			
			str +=          "\t\t\t\t}\n";
			str +=          "\t\t\t}\n";
			str +=          "\t\t}\n";
			
			str +=          "\t\t\n";
			str +=          "\t\t\tfor(int t = 0; t < count; t++)\n";
			str +=          "\t\t\t{\n";
			for(int i=0; i< aggrfunc.length; i++ )
			{
				if(aggrfunc[i].contains("avg_"))
				{
					for(int k=1; k <= num_of_var; k++)
					{
						if(aggrfunc[i].contains(""+k))
						{
							str += "\t\t\t\t"+aggrfunc[i]+"="+"sum_"+k+"_quant"+"/"+"cnt_"+k+"_quant;\n";
						}
					}
				}
			}
			str +=          "\t\t\t\tif(";
			String hc = "";
			for(int j=0; j < havingcon.length; j++)
			{
				if(havingcon[j].contains("and"))
				{
					havingcon[j] = "&&";
				}
				else if(havingcon[j].contains("or"))
				{
					havingcon[j] = "||";
				}	
				hc += havingcon[j] + " ";
			}
			str +=          hc+")\n";
			str +=          "\t\t\t\t{\n";
			for(int m = 0; m < selectattr.length; m++)
			{
				if(selectattr[m].contains("cust")||
						selectattr[m].contains("prod")||
						selectattr[m].contains("state"))
				{
					str +=          "\t\t\t\tSystem.out.printf(\""+"%s "+"\""+"sri[t]."+","+selectattr[m]+");\n";
				}
				else 
				{
					str +=          "\t\t\t\tSystem.out.printf(\""+"%d "+"\""+"sri[t]."+","+selectattr[m]+");\n";
				}
				
			}
			str +=          "\t\t\t\t}\n";
			str +=          "\t\t\t}\n";
			str +=          "\t\t}\n";
			
			str +=          "\t\tcatch(Exception e)\n";
			str +=          "\t\t{\n";
			str +=          "\t\tSystem.out.println(\""+"Connection URL or username or password errors!"+"\");\n";
			str +=          "\t\te.printStackTrace();\n";
			str +=          "\t\t}\n";
			str += "\t}\n";
			str += "}\n";
			
			System.out.println(str);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

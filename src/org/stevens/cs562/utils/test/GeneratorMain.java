package org.stevens.cs562.utils.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.stevens.cs562.code.CodeGeneratorComposite;
import org.stevens.cs562.utils.Constants;
import org.stevens.cs562.utils.DynamicCompiler;
import org.stevens.cs562.utils.StringBuilder;

public class GeneratorMain {
	
	public static void main(String[] args) throws IOException {
		printString("[INFO] ############################################################################### ");
		printString("[INFO] ###############          MF/EMF Code Generator 1.0            ################# ");
		printString("[INFO] ############################################################################### ");
		printString("[INFO] ## This is the EMF/MF Generator Program!");
		printString("[INFO] ## input emf or mf will let you enter in emf mode or mf mode");
		printString("[INFO] ## eg. input emf, you will enter in emf mode");
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		String line;
		boolean emf = false;
		boolean mf = false;
		while(true) {
			line = stdin.readLine();
			if(line.toLowerCase().trim().equals("emf") || line.toLowerCase().trim().equals("mf") || line.toLowerCase().trim().equals("exit")) {
				break;
			} else {
				printString("[ERROR] you input should be \"emf\" or \"mf\" or \"exit\" ");
			}
		}
		
		if(line.toLowerCase().trim().equals("exit")) {
			return;
		}
		if(line.toLowerCase().trim().equals("emf")) {
			printString("");
			printString("[INFO] you are now in EMF MODE");
			printString("[INFO] Please give the EMF SQL String or empty string calls default configuration");
			emf = true;
			mf = false;
		}
		if(line.toLowerCase().trim().equals("mf")) {
			printString("");
			printString("[INFO] you are now in MF MODE");
			printString("[INFO] Please give the MF SQL String or empty string calls default configuration ");
			mf = true;
			emf = false;
		}
		
		String str = "";
 		while(!StringBuilder.isEmpty(line)) {
 			line = stdin.readLine();
 			str += line;
		}
		
		printString("");
		printString("[INFO] ############################################################################### ");
		printString("[INFO] ###############                generating code                ################# ");
		printString("[INFO] ############################################################################### ");
		if(emf) {
			generateCode("EMF", str);
		}
		if(mf) {
			generateCode("MF", str);
		}
	}
	
	private static void generateCode(String type, String line) throws IOException {
		
		if(type.equals("EMF")) {
			CodeGeneratorComposite composite = new CodeGeneratorComposite(Constants.GENERATE_EMF, line);
			composite.generate();
			
			/*
			 * dynamic MF
			 */
			DynamicCompiler compiler = new DynamicCompiler(Constants.GENERATE_CODE_EMF_MAIN);
			compiler.compileAndRun();
		} 
		if(type.equals("MF")) {
			CodeGeneratorComposite composite = new CodeGeneratorComposite(Constants.GENERATE_MF, line);
			composite.generate();
			
			/*
			 * dynamic MF
			 */
			DynamicCompiler compiler = new DynamicCompiler(Constants.GENERATE_CODE_MF_MAIN);
			compiler.compileAndRun();
		} 
	}
	
	private static void printString(String str) {
		System.out.println(str);
	}
}

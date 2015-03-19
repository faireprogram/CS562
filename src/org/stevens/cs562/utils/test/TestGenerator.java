package org.stevens.cs562.utils.test;

import java.io.IOException;
import java.sql.SQLException;

import org.stevens.cs562.code.CodeGeneratorComposite;
import org.stevens.cs562.utils.Constants;
import org.stevens.cs562.utils.DynamicCompiler;

public class TestGenerator {
	
	public static void main(String[] strings) throws SQLException, IOException {
		
		CodeGeneratorComposite composite = new CodeGeneratorComposite(Constants.GENERATE_MF);
		composite.generate();
		
//		/*
//		 * dynamic 
//		 */
		DynamicCompiler compiler = new DynamicCompiler(Constants.GENERATE_CODE_MF_MAIN);
		compiler.compileAndRun();
	}
	


}

package org.stevens.cs562.utils.test;

import java.io.IOException;
import java.sql.SQLException;

import org.stevens.cs562.code.CodeGeneratorComposite;
import org.stevens.cs562.utils.DynamicCompiler;

public class TestGenerator {
	
	public static void main(String[] strings) throws SQLException, IOException {
		
		CodeGeneratorComposite composite = new CodeGeneratorComposite();
		composite.generate();
		
//		/*
//		 * dynamic 
//		 */
		DynamicCompiler compiler = new DynamicCompiler();
		compiler.compileAndRun();
	}
	


}

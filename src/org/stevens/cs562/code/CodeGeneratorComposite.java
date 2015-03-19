package org.stevens.cs562.code;

import org.stevens.cs562.utils.Constants;
import org.stevens.cs562.utils.ResourceHelper;

public class CodeGeneratorComposite {

	private Generator generator;
	
	public CodeGeneratorComposite(String type, String sql) {
		initialize(type, sql);
	}
	
	public CodeGeneratorComposite(String type) {
		initialize(type, ResourceHelper.readFromFile());
	}
	
	public CodeGeneratorComposite() {
		initialize(Constants.GENERATE_MF, ResourceHelper.readFromFile());
	}
	
	private void initialize(String type, String sql) {
		if(type == null) {
			generator = new MFGenerator(sql);
		} else if(type.equals(Constants.GENERATE_MF)) {
			generator = new MFGenerator(sql);
		} else if(type.equals(Constants.GENERATE_EMF)) {
			generator = new EMFGenerator(sql);
		}
	}

	public void generate() {
		generator.generateTable();
		generator.generateMain();
	}
}

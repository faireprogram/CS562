import java.io.FileNotFoundException;


public class generateCode {

	public static void main(String[] args) throws FileNotFoundException{
		print_DS printDS = new print_DS();
		printDS.print_ds();
		print_MFunc printMFunc = new print_MFunc();
		printMFunc.printMainFunction();
	}

}

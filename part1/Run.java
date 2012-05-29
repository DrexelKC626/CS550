
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java_cup.runtime.Symbol;


public class Run {
	public static void main(String[] args) {
		try {
			String input = "input";
			if(args.length > 1){
				System.out.println("Invalid input. The parameter should be: java -jar package-name.jar <input-file>.");
				System.exit(1);
			}
			if(args.length == 1) {
				input = args[0];
			}
			 
			// read it with BufferedReader
			BufferedReader br = new BufferedReader(new FileReader(input));
			parser test = new parser(new Yylex(br));
			Symbol out = test.parse();
			System.out.println(out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getInputRule() {
		StringBuffer info = new StringBuffer();
		info.append("The arguments should be:\r\n");
		info.append("\t-f\t The next argument is the input file.\r\n");
		return info.toString();
	}
}

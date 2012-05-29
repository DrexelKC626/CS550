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
			int size = 8192;

			if(args.length <= 4 && args.length % 2 == 0) {
				for(int i = 0; i < args.length; i += 2) {
					if(args[i].equals("-f")) {
						input = args[i + 1];
					}
					else if(args[i].equals("-s")) {
						size = Integer.parseInt(args[i + 1]);
					}
					else {
						System.out.println("Invalid input" + args[i] + "." + "The parameter should be: java -jar package-name.jar <-f input-file> <-s memory-size>.");
						System.out.println("Default input file is \"inout\".");
						System.out.println("Default memory size is 8192.");
						System.exit(1);
					}
				}
			}
			else {
				System.out.println("Invalid input. The parameter should be: java -jar package-name.jar <-f input-file> <-s memory-size>.");
				System.out.println("Default input file is \"inout\"");
				System.out.println("Default memory size is 8192");
				System.exit(1);
			}
			 
			Block.initList(size);
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
}
